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
import de.gematik.ti.epa.vzd.client.model.DirectoryEntry;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.GemStringUtils;
import de.gematik.ti.epa.vzd.gem.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
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
 * Specific execution for Command "ReadDirectoryEntry"
 */
public class ReadDirEntryExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(ReadDirEntryExecution.class);

    public ReadDirEntryExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.READ_DIR_ENTRY);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        boolean check = certificateCheck(command.getUserCertificate());
        if (!check) {
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
                    check = true;
                }
            }
        }
        if (!check) {
            LOG.error("Missing argument -> The given command have no argument to search for or given different TelematikIds delivered " + command
                .getName()
                + "\n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
        return check;
    }

    private boolean certificateCheck(List<UserCertificateType> userCertificate) {
        if (!userCertificate.isEmpty()) {
            String telematikId = userCertificate.get(FIRST_INDEX).getTelematikID();
            for (UserCertificateType cert : userCertificate) {
                if (!cert.getTelematikID().equals(telematikId) || StringUtils.isBlank(cert.getTelematikID())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected Callable<Boolean> createCallable(CommandType command) {
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                StringBuffer sb = new StringBuffer();
                sb.append("\n--- Command  " + command.getCommandId() + " ---\n");
                try (GemApiClient apiClient = connectionPool.getConnection()) {
                    apiClient.validateToken();
                    executeCommand(command, apiClient).getData().forEach(directoryEntry -> sb.append("Entry found: " + directoryEntry));
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.debug(sb.toString());
                    return true;
                } catch (ApiException ex) {
                    sb.append("\nRead directory entry execution failed\n"
                        + Transformer.getBaseDirectoryEntryFromCommandType(command) + "\n" + ex.getMessage());
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.error(sb.toString());
                    return false;
                }
            }
        };
    }

    public ApiResponse<List<DirectoryEntry>> executeCommand(CommandType command, GemApiClient apiClient) throws ApiException {

        String uid = getUid(command, apiClient);
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

        if (!command.getUserCertificate().isEmpty()) {
            if (StringUtils.isBlank(uid) && StringUtils.isNotBlank(command.getUserCertificate().get(0).getTelematikID())) {
                throw new ApiException("No entry present for telematikID: " + command.getUserCertificate().get(0).getTelematikID());
            }
        }
        ApiResponse<List<DirectoryEntry>> response =
            new GemDirectoryEntryAdministrationApi(apiClient).readDirectoryEntryWithHttpInfo(uid, givenName, sn, cn,
                displayName, streetAddress, postalCode, localityName, stateOrProvinceName, title,
                organization, otherName, specialization, domainID, personalEntry,
                dataFromAuthority);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            return response;
        } else {
            throw new CommandException(
                "Read directory entry execution failed. Response status was: "
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

