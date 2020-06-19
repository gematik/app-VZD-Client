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
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.GemStringUtils;
import de.gematik.ti.epa.vzd.gemClient.api.GemCertificateAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.UserCertificateType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ReadDirectoryEntryCertificate"
 */
public class ReadDirEntryCertExecution extends ExecutionBase {

  private CertificateAdministrationApi certificateAdministrationApi;
  private Logger LOG = LoggerFactory.getLogger(AddDirEntryExecution.class);

  public ReadDirEntryCertExecution(GemApiClient api) {
    super(api, CommandNamesEnum.READ_DIR_CERT);
    certificateAdministrationApi = new GemCertificateAdministrationApi(apiClient);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    UserCertificateType cert = command.getUserCertificate();
    if (cert != null) {
      List<String> params = new ArrayList<>();
      if (command.getDn() != null) {
        params.add(command.getDn().getUid());
      }
      params.add(cert.getEntryType());
      params.add(cert.getTelematikID());
      params.add(cert.getProfessionOID());
      if (cert.getUsage().size() != 0) {
        params.add("usage Vorhanden");
      }
      for (String param : params) {
        if (!StringUtils.isBlank(param)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean executeCommands() {
    boolean runSuccessful = true;
    if (commands.size() == 0) {
      return true;
    }
    for (CommandType command : commands) {
      try {
        for (UserCertificate userCertificate : executeCommand(command).getData()) {
          LOG.debug("Entry found: " + userCertificate);
        }
      } catch (Exception ex) {
        LOG.error("Read directory entry execution failed\n" + Transformer
            .getCreateDirectoryEntry(command));
        runSuccessful = false;
      }
    }
    return runSuccessful;
  }

  protected ApiResponse<List<UserCertificate>> executeCommand(CommandType command)
      throws ApiException {
    UserCertificateType cert = command.getUserCertificate();
    String uid = null;
    String cn = null;
    if (cert.getDn() != null) {
      uid = cert.getDn().getUid();
      cn = cert.getDn().getCn();
    }
    String entryType = cert.getEntryType();
    String telematikID = cert.getTelematikID();
    String professionOID = cert.getProfessionOID();
    String usage = null;
    if (cert.getUsage().size() != 0) {
      usage = GemStringUtils.listToString(cert.getUsage());
    }

    ApiResponse<List<UserCertificate>> response = certificateAdministrationApi
        .readDirectoryCertificatesWithHttpInfo(uid, cn, entryType,
            telematikID, professionOID, usage);

    return response;
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
