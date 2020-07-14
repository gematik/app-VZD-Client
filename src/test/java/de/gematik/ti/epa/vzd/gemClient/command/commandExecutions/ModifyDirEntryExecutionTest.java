package de.gematik.ti.epa.vzd.gemClient.command.commandExecutions;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.DistinguishedNameType;
import org.junit.Test;

public class ModifyDirEntryExecutionTest {

    private static GemApiClient gemApiClient = mock(GemApiClient.class);

    @Test
    public void preCheckFalseWhenUidIsEmptyString() {
        ModifyDirEntryExecution modDirEnt = new ModifyDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        DistinguishedNameType dn = new DistinguishedNameType();
        dn.setUid("");
        command.setDn(dn);

        assertTrue(!modDirEnt.preCheck(command));
    }

    @Test
    public void preCheckIsTrue() {
        ModifyDirEntryExecution modDirEnt = new ModifyDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        DistinguishedNameType dn = new DistinguishedNameType();
        dn.setUid("Test");
        command.setDn(dn);

        assertTrue(modDirEnt.preCheck(command));
    }

    @Test
    public void preCheckFalseWhenUidIsMissing() {
        ModifyDirEntryExecution modDirEnt = new ModifyDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        DistinguishedNameType dn = new DistinguishedNameType();
        dn.setCn("Test");
        command.setDn(dn);

        assertTrue(!modDirEnt.preCheck(command));
    }

}
