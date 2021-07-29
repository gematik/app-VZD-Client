/*
 * Copyright (c) 2021 gematik GmbH
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
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto.ExecutionResult;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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

    public ReadDirEntryExecution(IConnectionPool connectionPool, CommandNamesEnum commandNamesEnum) {
        super(connectionPool, commandNamesEnum);
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
        params.add(command.getCountryCode());
        params.add(command.getLocalityName());
        params.add(command.getStateOrProvinceName());
        params.add(command.getTitle());
        params.add(command.getOrganization());
        params.add(command.getOtherName());
        try {
            params.add(getTelematikId(command));
        } catch (CommandException e) {
            LOG.error(e.getMessage() + "\n" + command.getName() + "\n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
            throw e;
        }
        params.add(command.getTelematikIDSubStr());
        params.add(GemStringUtils.listToString(command.getSpecialization()));
        params.add(GemStringUtils.listToString(command.getDomainID()));
        params.add(GemStringUtils.listToString(command.getHolder()));
        params.add(command.getPersonalEntry());
        params.add(command.getDataFromAuthority());
        if (!command.getProfessionOID().isEmpty() || !command.getEntryType().isEmpty()) {
            params.add("Profession or Entrytype delivered");
        }

        boolean check = false;
        for (String parameter : params) {
            if (!StringUtils.isBlank(parameter)) {
                check = true;
            }
        }
        if (!check) {
            LOG.error("Missing argument -> The given command have no argument to search for or given different TelematikIds delivered " + command
                .getName()
                + "\n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
        return check;
    }

    protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient) throws ApiException {

        String uid = null;
        if (command.getDn() != null) {
            uid = command.getDn().getUid();
        }
        String givenName = command.getGivenName();
        String sn = command.getSn();
        String cn = command.getCn();
        String displayName = command.getDisplayName();
        String streetAddress = command.getStreetAddress();
        String postalCode = command.getPostalCode();
        String countryCode = command.getCountryCode();
        String localityName = command.getLocalityName();
        String stateOrProvinceName = command.getStateOrProvinceName();
        String title = command.getTitle();
        String organization = command.getOrganization();
        String otherName = command.getOtherName();
        String telematikID = getTelematikId(command);
        String telematikIDSubStr = command.getTelematikIDSubStr();
        String specialization = GemStringUtils.listToString(command.getSpecialization());
        String domainID = GemStringUtils.listToString(command.getDomainID());
        String holder = GemStringUtils.listToString(command.getHolder());
        String personalEntry = command.getPersonalEntry();
        String dataFromAuthority = command.getDataFromAuthority();
        String professionOID = GemStringUtils.listToString(command.getProfessionOID());
        String entryType = GemStringUtils.listToString(command.getEntryType());
        boolean baseEntryOnly = false;
        if (command.isBaseEntryOnly() != null) {
            baseEntryOnly = command.isBaseEntryOnly();
        }
        ApiResponse<List<DirectoryEntry>> response;
        try {
            response = new GemDirectoryEntryAdministrationApi(apiClient)
                .readDirectoryEntryWithHttpInfo(uid, givenName, sn, cn, displayName, streetAddress, postalCode, countryCode, localityName,
                    stateOrProvinceName, title, organization, otherName, telematikID, telematikIDSubStr, specialization, domainID, holder,
                    personalEntry, dataFromAuthority, professionOID, entryType, baseEntryOnly);
        } catch (ApiException ex) {
            throw new ApiException(ex.getCode(), "Entry could not be found: \n" + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
        StringBuffer sb = new StringBuffer();
        response.getData().forEach(directoryEntry -> sb.append("Entry found: " + directoryEntry));
        return new ExecutionResult(sb.toString(), true, response.getStatusCode());
    }

    protected String getTelematikId(CommandType command) {
        Set<String> telematikIds = command.getUserCertificate().stream().map(e -> e.getTelematikID()).collect(Collectors.toSet());
        telematikIds.add(command.getTelematikID());
        telematikIds.remove(null);

        if (telematikIds.size() > 1) {
            throw new CommandException(String.format("At least two different TelematikIds found in command -> %s", telematikIds));
        }
        return telematikIds.isEmpty() ? null : telematikIds.iterator().next();
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

