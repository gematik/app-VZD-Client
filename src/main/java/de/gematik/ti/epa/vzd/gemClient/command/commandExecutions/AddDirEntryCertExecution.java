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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "AddDirectoryEntryCertificate"
 */
public class AddDirEntryCertExecution extends ExecutionBase {

  private CertificateAdministrationApi certificateAdministrationApi;
  private Logger LOG = LoggerFactory.getLogger(AddDirEntryExecution.class);

  public AddDirEntryCertExecution(GemApiClient api) {
    super(api, CommandNamesEnum.ADD_DIR_CERT);
    certificateAdministrationApi = new GemCertificateAdministrationApi(apiClient);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    if (command.getUserCertificate() == null) {
      return false;
    }
    if (StringUtils.isBlank(command.getUserCertificate().getUserCertificate())) {
      return false;
    }
    String uid = null;
    if (command.getDn() != null) {
      uid = command.getDn().getUid();
    }
    if (StringUtils.isBlank(uid)) {
      DistinguishedNameType certDn = command.getUserCertificate().getDn();
      if (certDn != null) {
        uid = certDn.getUid();
      }
    }
    if (StringUtils.isBlank(uid)) {
      return false;
    }
    return true;
  }

  @Override
  public boolean executeCommands() {
    boolean runSuccessful = true;
    if (commands.size() == 0) {
      return true;
    }
    for (CommandType command : commands) {
      if (isEntryPresent(command)) {
        try {
          executeCommand(command);
        } catch (Exception ex) {
          LOG.error("An error have occured: " + ex.getMessage());
          runSuccessful = false;
        }
      } else {
        runSuccessful = doModify(command);
      }
    }
    return runSuccessful;
  }

  private ApiResponse<DistinguishedName> executeCommand(CommandType command) {
    apiClient.validateToken();
    boolean runSucsessfull = true;
    CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);

    ApiResponse<DistinguishedName> response = null;
    for (UserCertificate userCertificate : createDirectoryEntry.getUserCertificates()) {
      try {
        String uid = getUid(createDirectoryEntry.getDirectoryEntryBase(), userCertificate);
        response = addSingleCertificate(uid, userCertificate);
        if (response.getStatusCode() == 201) {
          LOG.debug(
              "Certificate successful added: \n" + userCertificate);
        }
      } catch (ApiException e) {
        runSucsessfull = false;
        LOG.error(
            "Something went wrong will adding certificate. Responsecode: " + e.getCode()
                + " certificate: " + userCertificate
                .getUserCertificate());
      }
    }
    if (!runSucsessfull) {
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

  private boolean doModify(CommandType command) {
    LOG.debug(
        "Certificate is already present in VZD. Will Proceed with modify certificate entry command");
    try {
      ExecutionCollection.getInstance().getModifyDirEntryCertExecution()
          .executeCommand(command);
      return true;
    } catch (Exception ex) {
      LOG.error(
          "Modify certificate entry execution failed. " + ex.getMessage() + "\n" + Transformer
              .getBaseDirectoryEntryFromCommandType(command));
      return false;
    }
  }

  @Override
  public boolean postCheck() {
    try {
      super.postCheck();
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return false;
  }
}
