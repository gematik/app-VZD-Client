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
import de.gematik.ti.epa.vzd.client.model.DirectoryEntry;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.GemStringUtils;
import de.gematik.ti.epa.vzd.gemClient.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ReadDirectoryEntry"
 */
public class ReadDirEntryExecution extends ExecutionBase {

  private Logger LOG = LoggerFactory.getLogger(ReadDirEntryExecution.class);
  private DirectoryEntryAdministrationApi directoryEntryAdministrationApi;

  public ReadDirEntryExecution(GemApiClient api) {
    super(api, CommandNamesEnum.READ_DIR_ENTRY);
    directoryEntryAdministrationApi = new GemDirectoryEntryAdministrationApi(apiClient);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    List<String> params = new ArrayList<>();
    if (command.getDn() != null) {
      params.add(command.getDn().getUid());
    }
    params.add(command.getGivenName());
    params.add(command.getSn());
    params.add(command.getCn());
    params.add(command.getDisplayName());
    params.add(command.getStreetAddress());
    params.add(command.getPostalCode());
    params.add(command.getLocalityName());
    params.add(command.getStateOrProvinceName());
    params.add(command.getTitle());
    params.add(command.getOrganization());
    params.add(command.getOtherName());
    params.add(GemStringUtils.listToString(command.getSpecialization()));
    params.add(GemStringUtils.listToString(command.getDomainID()));
    params.add(command.getPersonalEntry());
    params.add(command.getDataFromAuthority());
    for (String parameter : params) {
      if (!StringUtils.isBlank(parameter)) {
        return true;
      }
    }
    LOG.error("Missing argument -> The given command have no argument to search for " + command
        .getName()
        + "\n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
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
        for (DirectoryEntry directoryEntry : executeCommand(command).getData()) {
          LOG.debug("Entry found: " + directoryEntry);
        }
      } catch (Exception ex) {
        LOG.error("Read directory entry execution failed\n" + Transformer
            .getBaseDirectoryEntryFromCommandType(command));
        runSuccessful = false;
      }
    }
    return runSuccessful;
  }

  public ApiResponse<List<DirectoryEntry>> executeCommand(CommandType command)
      throws ApiException {
    apiClient.validateToken();
    String uid = command.getDn().getUid();
    String givenName = command.getGivenName();
    String sn = command.getSn();
    String cn = command.getCn();
    String displayName = command.getDisplayName();
    String streetAddress = command.getStreetAddress();
    String postalCode = command.getPostalCode();
    String localityName = command.getLocalityName();
    String stateOrProvinceName = command.getStateOrProvinceName();
    String title = command.getTitle();
    String organization = command.getOrganization();
    String otherName = command.getOtherName();
    String specialization = GemStringUtils.listToString(command.getSpecialization());
    String domainID = GemStringUtils.listToString(command.getDomainID());
    String personalEntry = command.getPersonalEntry();
    String dataFromAuthority = command.getDataFromAuthority();

    ApiResponse<List<DirectoryEntry>> response = directoryEntryAdministrationApi
        .readDirectoryEntryWithHttpInfo(uid, givenName, sn, cn, displayName, streetAddress,
            postalCode, localityName, stateOrProvinceName, title, organization, otherName,
            specialization, domainID, personalEntry, dataFromAuthority);
    if (response.getStatusCode() == 200) {
      return response;
    } else {
      throw new CommandException(
          "Modify directory entry execution failed. Response-Status was: " + response
              .getStatusCode() + "\n" + Transformer
              .getBaseDirectoryEntryFromCommandType(command));
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
