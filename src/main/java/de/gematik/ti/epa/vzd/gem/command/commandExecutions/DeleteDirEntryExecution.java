/*
 * Copyright (c) 2022 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gem.command.commandExecutions;

import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto.ExecutionResult;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "DeleteDirectoryEntry"
 */
public class DeleteDirEntryExecution extends ExecutionBase {

  private Logger LOG = LoggerFactory.getLogger(DeleteDirEntryExecution.class);

  public DeleteDirEntryExecution(IConnectionPool connectionPool) {
    super(connectionPool, CommandNamesEnum.DEL_DIR_ENTRY);
  }

  @Override
  public boolean checkValidation(CommandType command) {
    boolean check = true;
    if (command.getDn() != null) {
      if (StringUtils.isBlank(command.getDn().getUid())) {
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

  protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient)
      throws ApiException {

    ApiResponse<Object> response = new GemDirectoryEntryAdministrationApi(apiClient)
        .deleteDirectoryEntryWithHttpInfo(command.getDn().getUid());
    if (response.getStatusCode() == HttpStatus.SC_OK) {
      return new ExecutionResult(
          "Delete directory entry execution successful operated for " + command.getDn().getUid(),
          true,
          response.getStatusCode());
    } else if (response.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
      return new ExecutionResult(command.getDn().getUid() + " could not be found", true,
          response.getStatusCode());
    } else {
      throw new ApiException(
          "Delete directory entry execution failed. Response status was: "
              + response.getStatusCode() + "\n"
              + Transformer.getBaseDirectoryEntryFromCommandType(command));
    }
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
