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

package de.gematik.ti.epa.vzd.gemClient.invoker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import de.gematik.ti.epa.vzd.gemClient.exceptions.GemClientException;
import de.gematik.ti.epa.vzd.gemClient.invoker.ConfigHandler;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class ConfigHandlerTest {

    private static final String[] TEST_ARGS = new String[]{"-p",
        "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "config" + File.separator + "Config.txt", "-c",
        "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "config" + File.separator + "Credentials.txt", "-b", "rightPath"};
    private static final String[] TEST_ARGS_WITH_COMMANDPATH_IN_FILE = new String[]{"-p",
        "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "config" + File.separator + "Config.txt", "-c",
        "src" + File.separator + "test" + File.separator + "resources" + File.separator
            + "config" + File.separator + "Credentials.txt"};

    @Before
    public void resetConfigHandler() {
        ConfigHandler.setConfigHandler(null);
    }

    @Test
    public void testGetInstanceBeforeInit() {
        GemClientException exception = assertThrows(GemClientException.class,
            ConfigHandler::getInstance);
        assertEquals("A ConfigHandler have to be initialized first", exception.getMessage());
    }

    @Test
    public void doubleInitConfigHandlerTest() {
        ConfigHandler.init(TEST_ARGS);
        GemClientException exception = assertThrows(GemClientException.class, () ->
            ConfigHandler.init(TEST_ARGS));
        assertEquals("Configurations are only allowed to set once", exception.getMessage());
    }

    @Test
    public void checkRightCommandsWithCliPath() {
        ConfigHandler configHandler = ConfigHandler.init(TEST_ARGS);

        assertEquals(new File("rightPath").getAbsolutePath(), configHandler.getCommandsPath());
    }

    @Test
    public void checkRightCommandsWithFilePath() {
        ConfigHandler configHandler = ConfigHandler.init(TEST_ARGS_WITH_COMMANDPATH_IN_FILE);

        assertEquals(new File("src\\test\\resources\\config\\Commands.xml").getAbsolutePath(), configHandler.getCommandsPath());
    }

    @Test
    public void checkRightRetryOAuthPath() {
        ConfigHandler configHandler = ConfigHandler.init(TEST_ARGS);

        assertEquals("https://to.be.defined/oauth/token", configHandler.getRetryingOAuthPath());
    }

    @Test
    public void checkRightBasePath() {
        ConfigHandler configHandler = ConfigHandler.init(TEST_ARGS);

        assertEquals("http://[::1]:8080/OAuth2Token", configHandler.getBasePath());
    }
}
