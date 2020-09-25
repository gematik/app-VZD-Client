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
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.api.GemCertificateAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ModifyDirectoryEntryCertificate"
 */
public class ModifyDirEntryCertExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(ModifyDirEntryCertExecution.class);

    public ModifyDirEntryCertExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.MOD_DIR_CERT);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getUserCertificate() != null) {
            for (UserCertificateType cert : command.getUserCertificate()) {
                if (checkSingleUserCertificate(cert)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected Callable<Boolean> createCallable(CommandType command) {
        LOG.info("The execution for ModifyCertificate is exposed");
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        };
    }

    private boolean checkSingleUserCertificate(UserCertificateType cert) {
        if (cert.getDn() != null) {
            DistinguishedNameType dn = cert.getDn();
            if (StringUtils.isBlank(dn.getUid()) || StringUtils.isBlank(dn.getCn())) {
                return false;
            }
        }
        return true;
    }


    protected ApiResponse<UserCertificate> executeCommand(CommandType command, GemApiClient apiClient) throws ApiException {
        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);
        ApiResponse<UserCertificate> response = null;

        for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
            try {
                response = new GemCertificateAdministrationApi(apiClient)
                    .modifyDirectoryEntryCertificateWithHttpInfo(userCertificate.getDn().getUid(),
                        userCertificate.getDn().getCn(), userCertificate);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    LOG.debug(
                        "Modify directory entry execution successful operated\n" + response.getData());
                } else {
                    throw new CommandException(
                        "Modify directory entry execution failed. Response-Status was: " + response
                            .getStatusCode() + "\n" + Transformer
                            .getBaseDirectoryEntryFromCommandType(command));
                }
            } catch (ApiException e) {
                LOG.error("ModifyCertExecution could not be proceed. " + e.getMessage());
            }
        }
        return response;
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
