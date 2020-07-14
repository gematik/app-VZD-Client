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
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import java.security.cert.CertificateRevokedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "AddDirectoryEntryCertificate"
 */
public class AddDirEntryCertExecution extends ExecutionBase {

    private CertificateAdministrationApi certificateAdministrationApi;
    private Logger LOG = LoggerFactory.getLogger(CertificateAdministrationApi.class);

    public AddDirEntryCertExecution(GemApiClient api) {
        super(api, CommandNamesEnum.ADD_DIR_CERT);
        certificateAdministrationApi = new GemCertificateAdministrationApi(apiClient);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getUserCertificate() == null) {
            LOG.error("No certificate element found");
            return false;
        }
        if (command.getUserCertificate().isEmpty()) {
            LOG.error("No certificate delivered");
            return false;
        }
        String uid= null;
        for (UserCertificateType cert : command.getUserCertificate()) {
            String uidDn = null;
            if (command.getDn() != null) {
                uidDn = command.getDn().getUid();
            }
            if (StringUtils.isBlank(uidDn)) {
                DistinguishedNameType certDn = cert.getDn();
                if (certDn != null) {
                    uid = certDn.getUid();
                }
            }
            if(StringUtils.isBlank(uid)){
                uid = uidDn;
            }

            if (StringUtils.isBlank(uid)) {
                LOG.error("No uid delivered");
                return false;
            }
             if(!StringUtils.isNotBlank(uid) && !StringUtils.isNotBlank(uidDn) && !uid.equals(uidDn)) {
                 LOG.error("Mismatching uid delivered");
                 return false;
                }
            }
        return true;
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
                LOG.error("Entry for this Certificate could not be found: "
                    + Transformer.getCreateDirectoryEntry(command));
                runSuccessful = false;
            }
            LOG.debug("--- Command  " + command.getCommandId() + " end ---");
        }
        return runSuccessful;
    }

    private ApiResponse<DistinguishedName> executeCommand(CommandType command) {
        apiClient.validateToken();

        boolean runSuccessful = true;
        CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);

        ApiResponse<DistinguishedName> response = null;
        for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
            try {
                String uid = getUid(createDirectoryEntry.getDirectoryEntryBase(), userCertificate);
                response = addSingleCertificate(uid, userCertificate);
                if (response.getStatusCode() == HttpStatus.SC_CREATED) {
                    LOG.debug(
                        "Certificate successful added: \n" + userCertificate);
                }
            } catch (ApiException e) {
                runSuccessful = false;
                LOG.error(
                    "Something went wrong will adding certificate. Responsecode: " + e.getCode()
                        + " certificate: " + userCertificate.getUserCertificate());
            }
        }
        if (!runSuccessful) {
            throw new CommandException(
                "At least one certificate could not be added in:" + "\n" + Transformer
                    .getCreateDirectoryEntry(command));
        }

        return response;
    }

    private String getUid(BaseDirectoryEntry directoryEntryBase, UserCertificate userCertificate) {
        // uid present because checked in validation
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

    private ApiResponse<DistinguishedName> addSingleCertificate(String uid,
        UserCertificate userCertificate) throws ApiException {
        return certificateAdministrationApi
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
