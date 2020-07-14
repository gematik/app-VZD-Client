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

import de.gematik.ti.epa.vzd.client.api.CertificateAdministrationApi;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.api.GemCertificateAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.UserCertificateType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "DeleteDirectoryEntryCertificate"
 */
public class DeleteDirEntryCertExecution extends ExecutionBase {

    private CertificateAdministrationApi certificateAdministrationApi;
    private Logger LOG = LoggerFactory.getLogger(DeleteDirEntryCertExecution.class);


    public DeleteDirEntryCertExecution(GemApiClient api) {
        super(api, CommandNamesEnum.DEL_DIR_CERT);
        certificateAdministrationApi = new GemCertificateAdministrationApi(apiClient);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        String uid = null;
        String cn = null;
        boolean check = true;
        String totalUid = null;
        for (UserCertificateType userCertificateType : command.getUserCertificate()) {
            if (userCertificateType.getDn() != null) {
                if (totalUid == null) {
                    totalUid = userCertificateType.getDn().getUid();
                }
                uid = userCertificateType.getDn().getUid();
                cn = userCertificateType.getDn().getCn();
            }
            if (StringUtils.isBlank(uid)) {
                uid = command.getDn().getUid();
            }
            if (StringUtils.isBlank(uid) || StringUtils.isBlank(cn) || !totalUid.equals(uid)) {
                check = false;
            }
        }
        return check;
    }

    @Override
    public boolean executeCommands() {
        boolean runSuccessful = true;
        if (commands.isEmpty()) {
            return true;
        }
        for (CommandType command : commands) {
            LOG.debug("--- Command  " + command.getCommandId() + " ---");
            if (searchByUserCertificate(command)) {
                try {
                    executeCommand(command);
                } catch (Exception ex) {
                    LOG.error("An error have occured: " + ex.getMessage());
                    runSuccessful = false;
                }
            }
            LOG.debug("--- Command  " + command.getCommandId() + " end ---");
        }
        return runSuccessful;
    }

    private void executeCommand(CommandType command) {
        apiClient.validateToken();

        boolean runSucsessfull = true;
        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);

        ApiResponse<Void> response;
        for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
            try {
                String uid = getUid(createDirectoryEntry.getDirectoryEntryBase(), userCertificate);
                String cn = userCertificate.getDn().getCn();
                response = deleteSingleCertificate(uid, cn);
                if (response.getStatusCode() == HttpStatus.SC_OK) {
                    LOG.debug(
                        "Certificate successful deleted: \n" + userCertificate);
                }
            } catch (ApiException e) {
                runSucsessfull = false;
                LOG.error(
                    "Something went wrong will deleting certificate. Responsecode: " + e.getCode()
                        + " certificate: " + userCertificate
                        .getUserCertificate());
            }
        }
        if (!runSucsessfull) {
            throw new CommandException(
                "At least one certificate could not be added in:" + "\n" + Transformer
                    .getCreateDirectoryEntry(command));
        }
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

    private ApiResponse<Void> deleteSingleCertificate(String uid, String certificateEntryId)
        throws ApiException {
        return certificateAdministrationApi
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
