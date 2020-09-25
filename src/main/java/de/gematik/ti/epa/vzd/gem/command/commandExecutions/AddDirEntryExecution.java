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
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
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
 * Specific execution for Command "AddDirectoryEntry"
 */
public class AddDirEntryExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(AddDirEntryExecution.class);

    public AddDirEntryExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.ADD_DIR_ENTRY);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        boolean check = true;
        if (command.getUserCertificate().isEmpty()) {
            LOG.error("Missing certificate");
            check = false;
        } else {
            for (UserCertificateType userCertificateType : command.getUserCertificate()) {
                String telematikId = userCertificateType.getTelematikID();
                String userCertificate = userCertificateType.getUserCertificate();
                if ((StringUtils.isBlank(telematikId) && StringUtils
                    .isBlank(userCertificate))) {
                    check = false;
                }
            }
        }
        if (!check) {
            LOG.error("Missing argument -> telematikId or userCertificate in every entry have to be set."
                + command.getName() + "\n"
                + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
        return check;
    }

    @Override
    protected Callable<Boolean> createCallable(CommandType command) {
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                StringBuffer sb = new StringBuffer();
                sb.append("\n--- Command  " + command.getCommandId() + " ---");
                try (GemApiClient apiClient = connectionPool.getConnection()) {
                    apiClient.validateToken();
                    if (isEntryPresent(command, apiClient)) {
                        sb.append("\nEntry is already present in VZD. Will Proceed with modify directory entry command\n");
                        ExecutionResult modifyExecutionResult = doModify(command, apiClient);
                        sb.append(modifyExecutionResult.getMessage());
                        sb.append("\n--- Command  " + command.getCommandId() + " end (proceeded as modify) ---");
                        LOG.debug(sb.toString());
                        return modifyExecutionResult.getResult();
                    } else {
                        sb.append(executeCommand(command, apiClient).getMessage());
                        sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                        LOG.debug(sb.toString());
                        return true;
                    }
                } catch (ApiException ex) {
                    sb.append("\nAdd directory entry execution failed\n"
                        + Transformer.getBaseDirectoryEntryFromCommandType(command) + "\n" + ex.getMessage());
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
    protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient) throws ApiException {

        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);
        ApiResponse<DistinguishedName> response = new GemDirectoryEntryAdministrationApi(apiClient)
            .addDirectoryEntryWithHttpInfo(createDirectoryEntry);
        if (response.getStatusCode() == HttpStatus.SC_CREATED) {
            return new ExecutionResult("\nAdd directory entry execution successful operated\n" + response.getData(), true);
        } else {
            throw new CommandException(
                "Add directory entry execution failed. Response-Status was: " + response
                    .getStatusCode() + "\n" + Transformer.getCreateDirectoryEntry(command));
        }
    }

    private ExecutionResult doModify(CommandType command, GemApiClient apiClient) {
        try {
            return ExecutionCollection.getInstance().getModifyDirEntry().executeCommand(command, apiClient);
        } catch (Exception ex) {
            return new ExecutionResult(ex.getMessage(), false);
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
