/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gemClient.command.commandExecutions;

import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.model.DirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;

import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.exceptions.GemClientException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the base for every specific execution
 */
public abstract class ExecutionBase {

    protected final int FIRST_INDEX = 0;

    private Logger LOG = LoggerFactory.getLogger(ExecutionBase.class);

    protected GemApiClient apiClient;
    protected CommandNamesEnum execCommand;
    protected List<CommandType> commands;

    public ExecutionBase(GemApiClient api, CommandNamesEnum cmd) {
        this.apiClient = api;
        this.execCommand = cmd;
        this.commands = new ArrayList<>();
    }

    public boolean canHandleCommand(CommandNamesEnum cmd) {
        return this.execCommand.equals(cmd);
    }

    /**
     * Checks the given command for validation and adds it to the queue of commands to execute
     *
     * @param command
     * @return
     */
    public boolean preCheck(CommandType command) {
        try {
            if (!checkValidation(command)) {
                throw new CommandException(
                    "Command invalid. Please check " + command.getName());
            }
            commands.add(command);
            return true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return false;
        }
    }

    /**
     * Every single executor validates their commands and log the missing or wrong values
     *
     * @param command
     * @return
     */
    public abstract boolean checkValidation(CommandType command);

    /**
     * Executes all commands in the queue
     *
     * @return
     */
    public abstract boolean executeCommands();

    /**
     * Checks if the execution was successful and logs the result
     *
     * @return
     */
    public boolean postCheck() {
        try {
            return true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        return false;
    }

    /**
     * This function proceed a read command, to check if a entry without cert is already present
     *
     * @param command
     * @return
     */
    protected boolean isEntryPresent(CommandType command) {
        if (command.getDn() != null) {
            CommandType searchCommand = new CommandType();
            DistinguishedNameType dn = new DistinguishedNameType();
            dn.setUid(command.getDn().getUid());
            searchCommand.setDn(dn);
            try {
                ApiResponse<List<DirectoryEntry>> response = ExecutionCollection
                    .getInstance().getReadDirEntryExecution().executeCommand(searchCommand);
                return response.getStatusCode() == HttpStatus.SC_OK ? true : false;
            } catch (ApiException ex) {
                if (ex.getCode() == 0) {
                    throw new GemClientException(
                        "The server you address is probably not reachable at the moment");
                }
                LOG.error(ex.getMessage());
                return false;
            }
        } else {
            return serachByTelematikId(command);
        }
    }

    /**
     * This function proceed a read command, to check if a entry without cert is already present
     *
     * @param command
     * @return
     */
    private boolean serachByTelematikId(CommandType command) {
        UserCertificateType userCertificate = command.getUserCertificate().get(FIRST_INDEX);
        if (userCertificate != null) {
            try {
                CommandType searchCommand = new CommandType();
                searchCommand.getUserCertificate().add(new UserCertificateType());
                searchCommand.getUserCertificate().get(FIRST_INDEX).setTelematikID(userCertificate.getTelematikID());
                ApiResponse<List<UserCertificate>> response = ExecutionCollection
                    .getInstance().getReadDirEntryCertExecution().executeCommand(searchCommand);
                return response.getStatusCode() == HttpStatus.SC_OK ? true : false;
            } catch (ApiException ex) {
                if (ex.getCode() == 0) {
                    throw new GemClientException(
                        "The server you address is probably not reachable at the moment");
                }
                return false;
            }
        }
        throw new GemClientException("No valid parameter found for present check" + Transformer
            .getBaseDirectoryEntryFromCommandType(command));
    }

    public boolean searchByUserCertificate(CommandType command) {
        ApiResponse<List<UserCertificate>> response;
        try {
            response = ExecutionCollection.getInstance().getReadDirEntryCertExecution()
                .executeCommand(command);
        } catch (ApiException ex) {
            if (ex.getCode() == 0) {
                throw new GemClientException(
                    "The server you address is probably not reachable at the moment");
            }
            LOG.error(ex.getMessage());
            return false;
        }
        if (response.getData().size() == command.getUserCertificate().size()) {
            return true;
        }
        return false;
    }

    public String getUidByTelematikId(String telematikId) {
        ApiResponse<List<UserCertificate>> response;
        CommandType command = new CommandType();
        command.getUserCertificate().add(new UserCertificateType());
        command.getUserCertificate().get(FIRST_INDEX).setTelematikID(telematikId);
        try {
            response = ExecutionCollection.getInstance().getReadDirEntryCertExecution()
                .executeCommand(command);
        } catch (ApiException ex) {
            if (ex.getCode() == 0) {
                throw new GemClientException(
                    "The server you address is probably not reachable at the moment");
            }
            LOG.error(ex.getMessage());
            return null;
        }
        if (!response.getData().isEmpty()) {
            return response.getData().get(FIRST_INDEX).getDn().getUid();
        }
        return null;
    }
}
