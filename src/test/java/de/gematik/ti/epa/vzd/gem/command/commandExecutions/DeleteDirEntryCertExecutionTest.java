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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.gematik.ti.epa.vzd.gem.invoker.ConnectionPool;
import de.gematik.ti.epa.vzd.gem.invoker.IConnectionPool;
import generated.CommandType;
import generated.DistinguishedNameType;
import generated.UserCertificateType;
import org.junit.Test;

public class DeleteDirEntryCertExecutionTest {

    private static IConnectionPool connectionPool = mock(ConnectionPool.class);


    @Test
    public void checkValidationDifferentUidsTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
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
        command.setCn("SomeCn");
        assertFalse(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void checkValidationMissingCnTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        UserCertificateType certificate1 = new UserCertificateType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        dn1.setUid("SomeUid");
        certificate1.setDn(dn1);
        command.getUserCertificate().add(certificate1);
        assertFalse(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void checkValidationMissingUidTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        command.setCn("SomeCn");
        assertFalse(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void checkValidationSameUidsTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        UserCertificateType certificate1 = new UserCertificateType();
        UserCertificateType certificate2 = new UserCertificateType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        DistinguishedNameType dn2 = new DistinguishedNameType();
        DistinguishedNameType dn3 = new DistinguishedNameType();
        dn1.setUid("SomeUid");
        dn1.setCn("SomeCn");
        dn2.setUid("SomeUid");
        dn2.setCn("SomeOtherCn");
        dn3.setUid("SomeUid");
        certificate1.setDn(dn1);
        certificate2.setDn(dn2);
        command.setDn(dn3);
        command.getUserCertificate().add(certificate1);
        command.getUserCertificate().add(certificate2);
        assertTrue(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void doubledCnTest(){
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        UserCertificateType certificate1 = new UserCertificateType();
        UserCertificateType certificate2 = new UserCertificateType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        DistinguishedNameType dn2 = new DistinguishedNameType();
        DistinguishedNameType dn3 = new DistinguishedNameType();
        dn1.setUid("SomeUid");
        dn1.setCn("SameCn");
        dn2.setUid("SomeUid");
        dn2.setCn("SameCn");
        dn3.setUid("SomeUid");
        certificate1.setDn(dn1);
        certificate2.setDn(dn2);
        command.setDn(dn3);
        command.getUserCertificate().add(certificate1);
        command.getUserCertificate().add(certificate2);
        assertFalse(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void checkValidationUidInCertTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        UserCertificateType certificate1 = new UserCertificateType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        dn1.setUid("SomeUid");
        dn1.setCn("SomeCn");
        certificate1.setDn(dn1);
        command.setCn("SomeUid");
        dn1.setCn("SomeOtherCn");
        command.getUserCertificate().add(certificate1);
        assertTrue(deleteDirEntryCertExecution.checkValidation(command));
    }

    @Test
    public void checkValidationUidInDnTest() {
        DeleteDirEntryCertExecution deleteDirEntryCertExecution = new DeleteDirEntryCertExecution(connectionPool);
        CommandType command = new CommandType();
        DistinguishedNameType dn1 = new DistinguishedNameType();
        dn1.setUid("SomeUid");
        command.setDn(dn1);
        command.setCn("SomeUid");
        assertTrue(deleteDirEntryCertExecution.checkValidation(command));
    }
}
