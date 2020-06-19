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
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.UserCertificateType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "AddDirectoryEntry"
 */
public class AddDirEntryExecution extends ExecutionBase {

  private final DirectoryEntryAdministrationApi directoryEntryAdministrationApi;
  private Logger LOG = LoggerFactory.getLogger(AddDirEntryExecution.class);

  public AddDirEntryExecution(GemApiClient api) {
    super(api, CommandNamesEnum.ADD_DIR_ENTRY);
    directoryEntryAdministrationApi = new GemDirectoryEntryAdministrationApi(apiClient);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    boolean check = true;
    UserCertificateType userCertificateType = command.getUserCertificate();
    if (userCertificateType == null) {
      LOG.error(
          "Missing element \"UserCertificate\" " + command.getName() + "\n" + Transformer
              .getBaseDirectoryEntryFromCommandType(command));
      check = false;
    } else {
      String telematikId = userCertificateType.getTelematikID();
      String userCertificate = userCertificateType.getUserCertificate();
      if ((StringUtils.isBlank(telematikId) && StringUtils
          .isBlank(userCertificate))) {
        check = false;
      }
      if (check == false) {
        LOG.error(
            "Missing argument -> telematikId or userCertificate for command " + command
                .getName()
                + "\n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
      }
    }
    return check;
  }

  @Override
  public boolean executeCommands() {
    boolean runSuccessful = true;
    if (commands.size() == 0) {
      return true;
    }
    for (CommandType command : commands) {
      if (!isEntryPresent(command)) {
        try {
          executeCommand(command);
        } catch (Exception ex) {
          LOG.error("An error have occured: " + ex.getMessage() + "\n" + Transformer
              .getCreateDirectoryEntry(command));
          runSuccessful = false;
        }
      } else {
        runSuccessful = doModify(command);
      }
    }
    return runSuccessful;
  }

  /**
   * Function that execute one command and logs the result
   *
   * @param command
   * @return
   * @throws ApiException
   */
  protected ApiResponse<DistinguishedName> executeCommand(CommandType command) throws
      ApiException {
    apiClient.validateToken();
    CreateDirectoryEntry createDirectoryEntry = Transformer.getCreateDirectoryEntry(command);
    ApiResponse<DistinguishedName> response = directoryEntryAdministrationApi
        .addDirectoryEntryWithHttpInfo(createDirectoryEntry);
    if (response.getStatusCode() == 201) {
      LOG.debug("Add directory entry execution successful operated\n" + response.getData());
    } else {
      throw new CommandException(
          "Add directory entry execution failed. Response-Status was: " + response
              .getStatusCode() + "\n" + Transformer.getCreateDirectoryEntry(command));
    }
    return response;
  }

  private boolean doModify(CommandType command) {
    LOG.debug(
        "Entry is already present in VZD. Will Proceed with modify directory entry command");
    try {
      ExecutionCollection.getInstance().getModifyDirEntry().executeCommand(command);
      return true;
    } catch (Exception ex) {
      LOG.error(
          "Modify directory entry execution failed. " + ex.getMessage() + "\n" + Transformer
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
