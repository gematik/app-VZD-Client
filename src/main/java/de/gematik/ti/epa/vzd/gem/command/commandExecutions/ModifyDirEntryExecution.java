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

package de.gematik.ti.epa.vzd.gem.command.commandExecutions;

import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.ExecutionCollection;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto.ExecutionResult;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import generated.UserCertificateType;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ModifyDirectoryEntry"
 */
public class ModifyDirEntryExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(ModifyDirEntryExecution.class);

    public ModifyDirEntryExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.MOD_DIR_ENTRY);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getDn() != null) {
            if (StringUtils.isBlank(command.getDn().getUid())) {
                LOG.error(
                    "Missing argument -> uid for command " + command.getName() + "\n" + Transformer
                        .getBaseDirectoryEntryFromCommandType(command));
                return false;
            }
            return true;
        }
        if (!command.getUserCertificate().isEmpty()) {
            String telematikId = command.getUserCertificate().get(0).getTelematikID();
            for (UserCertificateType userCertificateType : command.getUserCertificate()) {
                if (!telematikId.equals(userCertificateType.getTelematikID())) {
                    return false;
                }
            }
            return true;
        }
        LOG.error("Missing element \"dn\" " + command.getName() + "\n" + Transformer
            .getBaseDirectoryEntryFromCommandType(command));
        return false;
    }

    @Override
    protected Callable<Boolean> createCallable(CommandType command) {
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                StringBuffer sb = new StringBuffer();
                sb.append("\n--- Command  " + command.getCommandId() + " ---\n");

                try (GemApiClient apiClient = connectionPool.getConnection()) {
                    apiClient.validateToken();
                    if (isEntryPresent(command, apiClient)) {
                        ExecutionResult modifyExecutionResult = executeCommand(command, apiClient);
                        sb.append(modifyExecutionResult.getMessage());
                        sb.append("--- Command  " + command.getCommandId() + " end ---");
                        LOG.debug(sb.toString());
                        return modifyExecutionResult.getResult();
                    } else {
                        sb.append("\nEntry is not present in VZD. Will Proceed with add directory entry command\n");
                        ExecutionResult addExecutionResult = doAdd(command, apiClient);
                        sb.append(addExecutionResult.getMessage());
                        sb.append("\n--- Command  " + command.getCommandId() + " end (proceeded as add) ---");
                        return addExecutionResult.getResult();
                    }
                } catch (ApiException ex) {
                    sb.append("Modify directory entry execution failed\n"
                        + Transformer.getBaseDirectoryEntryFromCommandType(command)+ "\n" + ex.getMessage());
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.error(sb.toString());
                    return false;
                }
            }
        };
    }

    /**
     * Function that execute one command and logs the result
     *
     * @param command
     * @return
     * @throws ApiException
     */
    protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient)
        throws ApiException {

        BaseDirectoryEntry baseDirectoryEntry = Transformer
            .getBaseDirectoryEntryFromCommandType(command);
        if (baseDirectoryEntry.getDn() == null) {
            baseDirectoryEntry.setDn(new DistinguishedName());
        }
        if (StringUtils.isBlank(baseDirectoryEntry.getDn().getUid())) {
            baseDirectoryEntry.getDn().setUid(getUid(command, apiClient));
        }
        ApiResponse<DistinguishedName> response = new GemDirectoryEntryAdministrationApi(apiClient)
            .modifyDirectoryEntryWithHttpInfo(baseDirectoryEntry.getDn().getUid(), baseDirectoryEntry);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            return (new ExecutionResult(
                "Modify directory entry execution successful operated\n" + response.getData(), true));
        } else {
            throw new CommandException(
                "Modify directory entry execution failed. Response status was: "
                    + response.getStatusCode() + "\n"
                    + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
    }

    private ExecutionResult doAdd(CommandType command, GemApiClient apiClient) throws ApiException {
        LOG.debug("Entry not present at VZD. Will proceed with add directory entry command");
        return ExecutionCollection.getInstance().getAddDirEntryExecution().executeCommand(command, apiClient);
    }
}
