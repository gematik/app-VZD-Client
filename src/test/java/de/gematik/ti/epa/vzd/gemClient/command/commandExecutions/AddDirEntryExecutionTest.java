package de.gematik.ti.epa.vzd.gemClient.command.commandExecutions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.UserCertificateType;
import org.junit.Test;

public class AddDirEntryExecutionTest {

    private static GemApiClient gemApiClient = mock(GemApiClient.class);


    @Test
    public void checkValidationMissingCertificateObjectTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        assertFalse(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationMissingTelematikIdAndCertificateTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        UserCertificateType certificate = new UserCertificateType();
        command.getUserCertificate().add(certificate);
        assertFalse(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationTelematikIdTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        command.setCn("Test");
        UserCertificateType certificate = new UserCertificateType();
        certificate.setUserCertificate("Certificate");
        command.getUserCertificate().add(certificate);
        assertTrue(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationCertificateTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        command.setCn("Test");
        UserCertificateType certificate = new UserCertificateType();
        certificate.setTelematikID("TelematikId");
        command.getUserCertificate().add(certificate);
        assertTrue(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationMissingCnTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        UserCertificateType certificate = new UserCertificateType();
        command.getUserCertificate().add(certificate);
        certificate.setTelematikID("TelematikId");
        certificate.setUserCertificate("Certificate");
        assertFalse(addDirEntryExecution.checkValidation(command));
    }

}
