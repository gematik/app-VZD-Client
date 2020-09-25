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
import generated.UserCertificateType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "DeleteDirectoryEntryCertificate"
 */
public class DeleteDirEntryCertExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(DeleteDirEntryCertExecution.class);


    public DeleteDirEntryCertExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.DEL_DIR_CERT);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        String uid = null;
        String cn;
        if (command.getDn() != null) {
            uid = command.getDn().getUid();
        }
        List<String> listCn = new ArrayList<>();
        for (UserCertificateType userCertificateType : command.getUserCertificate()) {
            if (userCertificateType.getDn() != null) {
                if (uid == null) {
                    uid = userCertificateType.getDn().getUid();
                }
                if (!userCertificateType.getDn().getUid().equals(uid)) {
                    LOG.error("Different Uid delivered (" + userCertificateType.getDn().getUid() + " / " + uid + " at command " + command
                        .getCommandId());
                    return false;
                }
                if (StringUtils.isBlank(userCertificateType.getDn().getCn())) {
                    LOG.error("Missing cn");
                    return false;
                }
                if (listCn.contains(userCertificateType.getDn().getCn())) {
                    LOG.error("Some cn is named twice");
                    return false;
                } else {
                    listCn.add(userCertificateType.getDn().getCn());
                }

            }
        }
        if (StringUtils.isBlank(uid)) {
            LOG.error("Missing UId or Cn");
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
                    sb.append(executeCommand(command, apiClient).getMessage());
                    sb.append("--- Command  " + command.getCommandId() + " end ---");
                    LOG.debug(sb.toString());
                    return true;
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
        boolean runSuccessfull = true;
        int errorCode = 0;

        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);

        for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
            try {
                String uid = getUid(createDirectoryEntry.getDirectoryEntryBase(), userCertificate);
                String cn = userCertificate.getDn().getCn();
                ApiResponse<Void> response = deleteSingleCertificate(uid, cn, apiClient);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    sb.append("\nCertificate successful deleted: \n" + userCertificate);
                }
            } catch (ApiException ex) {
                runSuccessfull = false;
                errorCode = ex.getCode();
                sb.append("\nSomething went wrong will deleting certificate. Responsecode: " + ex.getCode()
                    + " certificate: " + userCertificate.getUserCertificate());
            }
        }
        if (!runSuccessfull) {
            throw new ApiException(errorCode, sb.toString() + "\n" +
                "At least one certificate could not be deleted in:" + "\n" + Transformer
                .getCreateDirectoryEntry(command));
        }
        return new ExecutionResult(sb.toString(), true);
    }

    private String getUid(BaseDirectoryEntry directoryEntryBase, UserCertificate userCertificate) {
        String uidCert = userCertificate.getDn().getUid();
        String uidEntry = null;

        if (directoryEntryBase != null) {
            DistinguishedName dn = directoryEntryBase.getDn();
            if (dn != null) {
                uidEntry = dn.getUid();
            }
        }
        return uidCert == null ? uidEntry : uidCert;
    }

    private ApiResponse<Void> deleteSingleCertificate(String uid, String certificateEntryId, GemApiClient apiClient)
        throws ApiException {
        return new GemCertificateAdministrationApi(apiClient)
            .deleteDirectoryEntryCertificateWithHttpInfo(uid, certificateEntryId);
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
