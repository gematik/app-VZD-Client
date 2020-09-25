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
import de.gematik.ti.epa.vzd.client.model.UserCertificate;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.GemStringUtils;
import de.gematik.ti.epa.vzd.gem.api.GemCertificateAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import generated.UserCertificateType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specific execution for Command "ReadDirectoryEntryCertificate"
 */
public class ReadDirEntryCertExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(ReadDirEntryCertExecution.class);

    public ReadDirEntryCertExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.READ_DIR_CERT);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getUserCertificate().isEmpty()) {
            LOG.error("No certificate delivered for command " + Transformer.getCreateDirectoryEntry(command));
            return false;
        }
        for (UserCertificateType cert : command.getUserCertificate()) {
            if (checkSingleCertificate(cert) == false) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSingleCertificate(UserCertificateType cert) {
        List<String> params = new ArrayList<>();
        if (cert.getDn() != null) {
            params.add(cert.getDn().getUid());
        }
        params.add(cert.getEntryType());
        params.add(cert.getTelematikID());
        params.add(cert.getProfessionOID());
        if (!cert.getUsage().isEmpty()) {
            params.add("usage Vorhanden");
        }
        for (String param : params) {
            if (!StringUtils.isBlank(param)) {
                return true;
            }
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
                    sb.append("--- Command  " + command.getCommandId() + " end ---");
                    LOG.debug(sb.toString());
                } catch (ApiException ex) {
                    sb.append("Read directory entry cert execution failed\n"
                        + Transformer.getBaseDirectoryEntryFromCommandType(command) + "\n" + ex.getMessage());
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.error(sb.toString());
                    return false;
                }
                return true;
            }
        };
    }

    protected ApiResponse<List<UserCertificate>> executeCommand(CommandType command, GemApiClient apiClient)
        throws ApiException {

        ApiResponse<List<UserCertificate>> result = null;
        for (UserCertificateType cert : command.getUserCertificate()) {

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
            if (cert.getUsage().isEmpty()) {
                usage = GemStringUtils.listToString(cert.getUsage());
            }
            ApiResponse<List<UserCertificate>> tempResult = new GemCertificateAdministrationApi(apiClient)
                .readDirectoryCertificatesWithHttpInfo(uid, cn, entryType,
                    telematikID, professionOID, usage);
            if (result == null) {
                result = tempResult;
            } else {
                result.getData().addAll(tempResult.getData());
            }
        }
        return result;
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
