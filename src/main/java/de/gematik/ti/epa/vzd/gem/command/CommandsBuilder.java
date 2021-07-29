/*
 * Copyright (c) 2021 gematik GmbH
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

package de.gematik.ti.epa.vzd.gem.command;

import de.gematik.ti.epa.vzd.gem.command.parser.CommandParser;
import de.gematik.ti.epa.vzd.gem.command.parser.ReadUidParser;
import de.gematik.ti.epa.vzd.gem.command.parser.XmlExecutionParser;
import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.ConfigHandler;
import generated.CommandType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for reading the commands out of the .xml File. Before the commands can be build the ConfigHandler have to be initialized
 */
public class CommandsBuilder {

    private Logger LOG = LoggerFactory.getLogger(CommandsBuilder.class);
    private CommandParser parser;

    public CommandsBuilder() {
        String path = ConfigHandler.getInstance().getCommandsPath();
        parser = getParser(path);
    }

    /**
     * Reads the given command file and creates a list of commands to execute
     *
     * @return CommandListType - List of all commands to execute
     */
    public List<CommandType> buildCommands() {
        return parser.buildCommands();
    }

    private CommandParser getParser(String path) {
        if (path.endsWith(".xml")) {
            LOG.debug("Get commands as .xml input");
            return new XmlExecutionParser();
        } else if (path.endsWith(".txt")) {
            LOG.debug("Get uid list as .txt input");
            return new ReadUidParser();
        }
        throw new CommandException("The command file you have provided is not valid: " + path);
    }

}
