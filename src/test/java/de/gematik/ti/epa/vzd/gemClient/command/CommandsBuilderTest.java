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

package de.gematik.ti.epa.vzd.gemClient.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import de.gematik.ti.epa.vzd.gemClient.exceptions.ReadException;
import de.gematik.ti.epa.vzd.gemClient.invoker.ConfigHandler;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class CommandsBuilderTest {

  private static final String[] ARGS_Wrong_Formatted = new String[]{"-p",
      "src" + File.separator + "test" + File.separator + "resources" + File.separator
          + "config" + File.separator + "ConfigWrongFormattedCommands.txt", "-c",
      "src" + File.separator + "test" + File.separator + "resources" + File.separator
          + "config" + File.separator + "bspCredentials.txt"};
  private static final String[] ARGS_MISSING_COMMANDS = new String[]{"-p",
      "src" + File.separator + "test" + File.separator + "resources" + File.separator
          + "config" + File.separator + "ConfigMissingCommands.txt", "-c",
      "src" + File.separator + "test" + File.separator + "resources" + File.separator
          + "config" + File.separator + "bspCredentials.txt"};

  @Before
  public void createConfigHandler() {
    ConfigHandler.setConfigHandler(null);
  }

  @Test
  public void checkParsingError() {
    ConfigHandler.init(ARGS_Wrong_Formatted);
    ReadException exception = assertThrows(ReadException.class,
        () -> new CommandsBuilder().buildCommands());
    assertEquals(
        "An error have been occurred while reading your command file. Please check if this file is a valid .xml file",
        exception.getMessage());
  }

  @Test
  public void checkFileNotFoundError() {
    ConfigHandler.init(ARGS_MISSING_COMMANDS);
    ReadException exception = assertThrows(ReadException.class,
        () -> new CommandsBuilder().buildCommands());
    assertEquals(
        "A problem with your named file have occurred. Please if check "+ new File("doesNotExist.xml").getAbsolutePath() + " exist",
        exception.getMessage());
  }
}
