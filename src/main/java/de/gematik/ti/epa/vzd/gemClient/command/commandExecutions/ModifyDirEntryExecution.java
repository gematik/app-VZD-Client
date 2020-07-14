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

import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ModifyDirectoryEntry"
 */
public class ModifyDirEntryExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(ModifyDirEntryExecution.class);
    private final DirectoryEntryAdministrationApi directoryEntryAdministrationApi;

    public ModifyDirEntryExecution(GemApiClient api) {
        super(api, CommandNamesEnum.MOD_DIR_ENTRY);
        directoryEntryAdministrationApi = new GemDirectoryEntryAdministrationApi(apiClient);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        boolean check = true;
        if (command.getDn() != null) {
            if (StringUtils.isBlank(command.getDn().getUid())) {
                LOG.error(
                    "Missing argument -> uid for command " + command.getName() + "\n" + Transformer
                        .getBaseDirectoryEntryFromCommandType(command));
                check = false;
            }
        } else {
            LOG.error("Missing element \"dn\" " + command.getName() + "\n" + Transformer
                .getBaseDirectoryEntryFromCommandType(command));
            check = false;
        }
        return check;
    }

    @Override
    public boolean executeCommands() {
        boolean runSuccessful = true;
        for (CommandType command : commands) {
            LOG.debug("--- Command  " + command.getCommandId() + " ---");
            if (isEntryPresent(command)) {
                try {
                    executeCommand(command);
                } catch (Exception ex) {
                    LOG.error("An error have occured: " + ex.getMessage());
                    runSuccessful = false;
                }
            } else {
                runSuccessful = doAdd(command);
            }
            LOG.debug("--- Command  " + command.getCommandId() + " end ---");
        }
        return runSuccessful;
    }

    /**
     * Function that execute one command and logs the result
     *
     * @param command
     * @return
     * @throws ApiException
     */
    protected ApiResponse<DistinguishedName> executeCommand(CommandType command)
        throws ApiException {
        apiClient.validateToken();

        BaseDirectoryEntry baseDirectoryEntry = Transformer
            .getBaseDirectoryEntryFromCommandType(command);
        ApiResponse<DistinguishedName> response = directoryEntryAdministrationApi
            .modifyDirectoryEntryWithHttpInfo(command.getDn().getUid(), baseDirectoryEntry);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            LOG.debug(
                "Modify directory entry execution successful operated\n" + response.getData());
        } else {
            throw new CommandException(
                "Modify directory entry execution failed. Response status was: "
                    + response.getStatusCode() + "\n"
                    + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
        return response;
    }

    private boolean doAdd(CommandType command) {
        LOG.debug("Entry not present at VZD. Will proceed with add directory entry command");
        try {
            ExecutionCollection.getInstance().getAddDirEntryExecution().executeCommand(command);
            return true;
        } catch (ApiException ex) {
            LOG.error("Add directory entry execution failed\n" + Transformer
                .getCreateDirectoryEntry(command));
            return false;
        }

    }

    @Override
    public boolean postCheck() {
        try {
            super.postCheck();
            return true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        return false;
    }
}
