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
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.api.GemCertificateAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto.ExecutionResult;
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
 * Specific execution for Command "AddDirectoryEntryCertificate"
 */
public class AddDirEntryCertExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(AddDirEntryCertExecution.class);

    public AddDirEntryCertExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.ADD_DIR_CERT);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getUserCertificate().isEmpty()) {
            LOG.error("No certificate element found");
            return false;
        }
        String uid = null;
        if (command.getDn() != null) {
            uid = command.getDn().getUid();
        }
        for (UserCertificateType cert : command.getUserCertificate()) {
            if (StringUtils.isBlank(cert.getUserCertificate())) {
                LOG.error("No user certificate for element found");
                return false;
            }
            if (cert.getDn() != null) {
                DistinguishedNameType certDn = cert.getDn();
                if (uid == null) {
                    uid = certDn.getUid();
                }
                if (StringUtils.isNotBlank(certDn.getUid())) {
                    if (!uid.equals(certDn.getUid())) {
                        LOG.error("Mismatching uid delivered");
                        return false;
                    }
                }
            }
        }
        if (StringUtils.isBlank(uid)) {
            LOG.error("No or mismatching uid delivered");
            return false;
        }
        return true;
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
                    ExecutionResult result = executeCommand(command, apiClient);
                    sb.append(result.getMessage());
                    sb.append("--- Command  " + command.getCommandId() + " end ---");
                    LOG.debug(sb.toString());
                    return result.getResult();
                } catch (ApiException ex) {
                    sb.append(ex.getMessage());
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.error(sb.toString());
                    return false;
                }
            }
        };
    }

    protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient) throws ApiException {
        StringBuffer sb = new StringBuffer();
        boolean runSuccessful = true;
        int errorCode = 0;

        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);

        ApiResponse<DistinguishedName> response;
        for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
            try {
                String uid = getUid(createDirectoryEntry.getDirectoryEntryBase(), userCertificate);
                response = addSingleCertificate(uid, userCertificate, apiClient);
                if (response.getStatusCode() == HttpStatus.SC_CREATED) {
                    sb.append("\nCertificate successful added: \n" + response.getData());
                }
            } catch (ApiException ex) {
                runSuccessful = false;
                errorCode = ex.getCode();
                sb.append("\nSomething went wrong will adding certificate. Responsecode: " + ex.getCode()
                    + " certificate: " + userCertificate.getUserCertificate());
            }
        }
        if (!runSuccessful) {
            throw new ApiException(errorCode, sb.toString() + "\n" +
                "At least one certificate could not be added in:" + "\n" + Transformer
                .getCreateDirectoryEntry(command));
        }
        return new ExecutionResult(sb.toString(), true);
    }

    private String getUid(BaseDirectoryEntry directoryEntryBase, UserCertificate userCertificate) {
        String uidCert = null;
        String uidEntry = null;

        if (userCertificate.getDn() != null) {
            uidCert = userCertificate.getDn().getUid();
        }

        if (directoryEntryBase != null) {
            DistinguishedName dn = directoryEntryBase.getDn();
            if (dn != null) {
                uidEntry = dn.getUid();
            }
        }
        return uidCert == null ? uidEntry : uidCert;
    }

    private ApiResponse<DistinguishedName> addSingleCertificate(String uid, UserCertificate userCertificate, GemApiClient apiClient)
        throws ApiException {
        return new GemCertificateAdministrationApi(apiClient)
            .addDirectoryEntryCertificateWithHttpInfo(uid, userCertificate);
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
