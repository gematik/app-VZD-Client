package de.gematik.ti.epa.vzd.gem.command.commandExecutions;

import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.DistinguishedName;
import de.gematik.ti.epa.vzd.gem.CommandNamesEnum;
import de.gematik.ti.epa.vzd.gem.api.GemDirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.gem.command.ExecutionCollection;
import de.gematik.ti.epa.vzd.gem.command.Transformer;
import de.gematik.ti.epa.vzd.gem.command.commandExecutions.dto.ExecutionResult;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.GemApiClient;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import generated.UserCertificateType;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveModifyDirEntryExecution extends ExecutionBase {

    private Logger LOG = LoggerFactory.getLogger(SaveModifyDirEntryExecution.class);
    private static final int FIRST_INDEX = 0;

    public SaveModifyDirEntryExecution(IConnectionPool connectionPool) {
        super(connectionPool, CommandNamesEnum.SMOD_DIR_ENTRY);
    }

    @Override
    public boolean checkValidation(CommandType command) {
        if (command.getDn() != null) {
            if (StringUtils.isBlank(command.getDn().getUid())) {
                LOG.error(
                    "Missing argument -> uid for command " + command.getName() + "\n" + Transformer
                        .getBaseDirectoryEntryFromCommandType(command));
                return false;
            }
            return true;
        }
        if (!command.getUserCertificate().isEmpty()) {
            String telematikId = command.getUserCertificate().get(FIRST_INDEX).getTelematikID();
            for (UserCertificateType userCertificateType : command.getUserCertificate()) {
                if (!telematikId.equals(userCertificateType.getTelematikID())) {
                    return false;
                }
            }
            return true;
        }
        LOG.error("Missing element \"dn\" " + command.getName() + "\n" + Transformer
            .getBaseDirectoryEntryFromCommandType(command));
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
                    if (isEntryPresent(command, apiClient)) {
                        CommandType safeCommand = getSaveCommand(command, apiClient);
                        ExecutionResult modifyExecutionResult = executeCommand(safeCommand, apiClient);
                        sb.append(modifyExecutionResult.getMessage());
                        sb.append("--- Command  " + safeCommand.getCommandId() + " end ---");
                        LOG.debug(sb.toString());
                        return modifyExecutionResult.getResult();
                    } else {
                        sb.append("\nEntry is not present in VZD. Will Proceed with add directory entry command\n");
                        ExecutionResult addExecutionResult = doAdd(command, apiClient);
                        sb.append(addExecutionResult.getMessage());
                        sb.append("\n--- Command  " + command.getCommandId() + " end (proceeded as add) ---");
                        return addExecutionResult.getResult();
                    }
                } catch (ApiException ex) {
                    sb.append("Save modify directory entry execution failed\n"
                        + Transformer.getBaseDirectoryEntryFromCommandType(command));
                    sb.append("\n--- Command  " + command.getCommandId() + " end ---");
                    LOG.error(sb.toString());
                    return false;
                }
            }
        };
    }

    private CommandType getSaveCommand(CommandType command, GemApiClient apiClient) throws ApiException {
        ApiResponse<List<DirectoryEntry>> response = new GemDirectoryEntryAdministrationApi(apiClient)
            .readDirectoryEntryWithHttpInfo(command.getDn().getUid(), null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null);
        CommandType saveCommand = Transformer.getCommandTypeFromDirectoryEntry(response.getData().get(0));

        saveCommand.setCommandId(command.getCommandId());
        saveCommand.setName(command.getName());

        if (command.getDn() != null) {
            saveCommand.setDn(command.getDn());
        }
        if (StringUtils.isNotBlank(command.getGivenName())) {
            saveCommand.setGivenName(command.getGivenName());
        }
        if (StringUtils.isNotBlank(command.getSn())) {
            saveCommand.setSn(command.getSn());
        }
        if (StringUtils.isNotBlank(command.getStreetAddress())) {
            saveCommand.setStreetAddress(command.getStreetAddress());
        }
        if (StringUtils.isNotBlank(command.getPostalCode())) {
            saveCommand.setPostalCode(command.getPostalCode());
        }
        if (StringUtils.isNotBlank(command.getLocalityName())) {
            saveCommand.setLocalityName(command.getLocalityName());
        }
        if (StringUtils.isNotBlank(command.getStateOrProvinceName())) {
            saveCommand.setStateOrProvinceName(command.getStateOrProvinceName());
        }
        if (StringUtils.isNotBlank(command.getCn())) {
            saveCommand.setCn(command.getCn());
        }
        if (StringUtils.isNotBlank(command.getDisplayName())) {
            saveCommand.setDisplayName(command.getDisplayName());
        }
        if (StringUtils.isNotBlank(command.getTitle())) {
            saveCommand.setTitle(command.getTitle());
        }
        if (StringUtils.isNotBlank(command.getOrganization())) {
            saveCommand.setOrganization(command.getOrganization());
        }
        if (StringUtils.isNotBlank(command.getOtherName())) {
            saveCommand.setOtherName(command.getOtherName());
        }
        if (!command.getSpecialization().isEmpty()) {
            while (!saveCommand.getSpecialization().isEmpty()) {
                saveCommand.getSpecialization().remove(FIRST_INDEX);
            }
            saveCommand.getSpecialization().addAll(command.getSpecialization());
        }
        if (!command.getDomainID().isEmpty()) {
            while (!saveCommand.getDomainID().isEmpty()) {
                saveCommand.getDomainID().remove(FIRST_INDEX);
            }
            saveCommand.getDomainID().addAll(command.getDomainID());
        }

        return saveCommand;
    }

    protected ExecutionResult executeCommand(CommandType command, GemApiClient apiClient)
        throws ApiException {

        BaseDirectoryEntry baseDirectoryEntry = Transformer
            .getBaseDirectoryEntryFromCommandType(command);
        if (baseDirectoryEntry.getDn() == null) {
            baseDirectoryEntry.setDn(new DistinguishedName());
        }
        if (StringUtils.isBlank(baseDirectoryEntry.getDn().getUid())) {
            baseDirectoryEntry.getDn().setUid(getUid(command, apiClient));
        }
        ApiResponse<DistinguishedName> response = new GemDirectoryEntryAdministrationApi(apiClient)
            .modifyDirectoryEntryWithHttpInfo(baseDirectoryEntry.getDn().getUid(), baseDirectoryEntry);
        if (response.getStatusCode() == HttpStatus.SC_OK) {
            return (new ExecutionResult(
                "Modify directory entry execution successful operated\n" + response.getData(), true));
        } else {
            throw new CommandException(
                "Modify directory entry execution failed. Response status was: "
                    + response.getStatusCode() + "\n"
                    + Transformer.getBaseDirectoryEntryFromCommandType(command));
        }
    }

    private ExecutionResult doAdd(CommandType command, GemApiClient apiClient) throws ApiException {
        LOG.debug("Entry not present at VZD. Will proceed with add directory entry command");
        return ExecutionCollection.getInstance().getAddDirEntryExecution().executeCommand(command, apiClient);
    }
}