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
        command.setUserCertificate(certificate);
        assertFalse(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationTelematikIdTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        UserCertificateType certificate = new UserCertificateType();
        certificate.setUserCertificate("Certificate");
        command.setUserCertificate(certificate);
        assertTrue(addDirEntryExecution.checkValidation(command));
    }

    @Test
    public void checkValidationCertificateTest() {
        AddDirEntryExecution addDirEntryExecution = new AddDirEntryExecution(gemApiClient);
        CommandType command = new CommandType();
        UserCertificateType certificate = new UserCertificateType();
        certificate.setTelematikID("TelematikId");
        command.setUserCertificate(certificate);
        assertTrue(addDirEntryExecution.checkValidation(command));
    }

}
