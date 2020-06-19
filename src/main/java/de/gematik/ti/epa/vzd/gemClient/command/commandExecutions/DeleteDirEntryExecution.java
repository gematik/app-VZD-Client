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
import de.gematik.ti.epa.vzd.gemClient.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gemClient.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gemClient.command.Transformer;
import de.gematik.ti.epa.vzd.gemClient.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "DeleteDirectoryEntry"
 */
public class DeleteDirEntryExecution extends ExecutionBase {

  private Logger LOG = LoggerFactory.getLogger(DeleteDirEntryExecution.class);

  private final DirectoryEntryAdministrationApi directoryEntryAdministrationApi;

  public DeleteDirEntryExecution(GemApiClient api) {
    super(api, CommandNamesEnum.DEL_DIR_ENTRY);
    directoryEntryAdministrationApi = new GemDirectoryEntryAdministrationApi(apiClient);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    boolean check = true;
    if (command.getDn() != null) {
      if (command.getDn().getUid() == null || command.getDn().getUid().equals("")) {
        LOG.error(
            "Missing argument -> uid for command " + command.getName() + "\n" + Transformer
                .getBaseDirectoryEntryFromCommandType(command));
        check = false;
      }
    } else {
      LOG.error("Missing element \"dn\" " + command.getName() + "\n" + Transformer
          .getBaseDirectoryEntryFromCommandType(command));
      check = false;
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
      if (isEntryPresent(command)) {
        try {
          executeCommand(command);
        } catch (Exception ex) {
          LOG.error("An error have occured: " + ex.getMessage());
          runSuccessful = false;
        }
      } else {
        LOG.debug(command.getDn().getUid() + " could not be found");
        runSuccessful = false;
      }
    }
    return runSuccessful;
  }

  private void executeCommand(CommandType command) throws ApiException {
    apiClient.validateToken();
    ApiResponse<Void> response = directoryEntryAdministrationApi
        .deleteDirectoryEntryWithHttpInfo(command.getDn().getUid());
    if (response.getStatusCode() == 200) {
      LOG.debug("Delete directory entry execution successful operated for " + command.getDn()
          .getUid());
    } else if (response.getStatusCode() == 404) {
      LOG.debug(command.getDn().getUid() + " could not be found");
      throw new CommandException(command.getDn().getUid() + " could not be found");
    } else {
      throw new CommandException(
          "Delete directory entry execution failed. Response-Status was: " + response
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
