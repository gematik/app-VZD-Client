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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;
import org.junit.Test;

public class DeleteDirEntryCertExecutionTest {

    private static GemApiClient gemApiClient = mock(GemApiClient.class);


    @Test
    public void checkValidationDifferentUidsTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(gemApiClient);
        CommandType command = new CommandType();
        UserCertificateType certificate1 = new UserCertificateType();
        UserCertificateType certificate2 = new UserCertificateType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        DistinguishedNameType dn2 = new DistinguishedNameType();
        dn1.setUid("something");
        dn2.setUid("somethingelse");
        certificate1.setDn(dn1);
        certificate2.setDn(dn2);
        command.getUserCertificate().add(certificate1);
        command.getUserCertificate().add(certificate2);
        assertFalse(deleteDirEntryCertExecution.checkValidation(command));
    }
}
