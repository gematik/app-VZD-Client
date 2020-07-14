package de.gematik.ti.epa.vzd.gemClient.command.commandExecutions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.DistinguishedNameType;
import org.junit.Test;

public class DeleteDirEntryExecutionTest {

    private static GemApiClient gemApiClient = mock(GemApiClient.class);
    private static DeleteDirEntryExecution deleteDirEntryExecution = new DeleteDirEntryExecution(gemApiClient);

    @Test
    public void checkValidationMissingArgumentTest() {
        CommandType command = new CommandType();
        assertFalse(deleteDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationHaveUID() {
        CommandType command = new CommandType();
        DistinguishedNameType dn = new DistinguishedNameType();
        dn.setUid("cbca60fe-8ca7-4960-990d-ec526a200582");
        command.setDn(dn);
        assertTrue(deleteDirEntryExecution.checkValidation(command));
    }
}