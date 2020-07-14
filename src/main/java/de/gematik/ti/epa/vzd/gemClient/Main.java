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

package de.gematik.ti.epa.vzd.gemClient;

import de.gematik.ti.epa.vzd.gemClient.command.CommandsBuilder;
import de.gematik.ti.epa.vzd.gemClient.command.ExecutionController;
import de.gematik.ti.epa.vzd.gemClient.command.commandExecutions.ExecutionCollection;
import de.gematik.ti.epa.vzd.gemClient.invoker.ConfigHandler;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import generated.CommandType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        LOG.info("VZD-Client started");
        LOG.info(GemStringUtils.getPic());
        ConfigHandler.init(args);
        start();
    }

    private static void start() {
        ExecutionCollection.init(new GemApiClient());
        List<CommandType> commands = new CommandsBuilder().buildCommands();
        ConfigHandler configHandler = ConfigHandler.getInstance();
        LOG.debug("============ Execution parameter ============");
        LOG.debug("Server: " + configHandler.getBasePath());
        LOG.debug("OAuth Server: " + configHandler.getRetryingOAuthPath());
        LOG.debug("Command data: " + configHandler.getCommandsPath());
        LOG.debug("Commands in progress: " + commands.size());
        LOG.debug("=============================================");
        new ExecutionController().execute(commands);
    }

    // <editor-fold desc="Private Constructor">
    private Main() {
        super();
    }
    // </editor-fold>
}
