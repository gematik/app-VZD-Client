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

package de.gematik.ti.epa.vzd.gem;

import de.gematik.ti.epa.vzd.gem.command.CommandsBuilder;
import de.gematik.ti.epa.vzd.gem.command.ExecutionCollection;
import de.gematik.ti.epa.vzd.gem.command.ExecutionController;
import de.gematik.ti.epa.vzd.gem.invoker.ConfigHandler;
import de.gematik.ti.epa.vzd.gem.invoker.ConnectionPool;
import generated.CommandType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        LOG.info("VZD-Client started");
        LOG.info(GemStringUtils.getPic());
        ConfigHandler configHandler = ConfigHandler.init(args);
        if (configHandler.isGetVersion()) {
            LOG.info("You are currently using version " + configHandler.getClientVersion());
            return;
        }
        start();
    }

    private static void start() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        List<CommandType> commands = new CommandsBuilder().buildCommands();
        printClientInfo(configHandler, commands.size());

        configHandler.adjustConnectionCount(commands);
        ExecutionCollection.init(ConnectionPool.createConnectionPool(configHandler.getConnectionCount()));

        if (!ConfigHandler.getInstance().isChunked()) {
            new ExecutionController().execute(commands);
            return;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SS");
        System.setProperty("l4j.logDir", System.getProperty("l4j.logDir") + "/VZD-Client_" + LocalDateTime.now().format(dateTimeFormatter));
        startChunked(commands);
    }

    private static void printClientInfo(ConfigHandler configHandler, int commands) {
        LOG.debug("============ Execution parameter ============");
        LOG.debug("Server: " + configHandler.getBasePath());
        LOG.debug("OAuth Server: " + configHandler.getRetryingOAuthPath());
        LOG.debug("Command data: " + configHandler.getCommandsPath());
        LOG.debug("Config path: " + configHandler.getConfigPath());
        LOG.debug("Commands in progress: " + commands);
        LOG.debug("VZD-Client version: " + configHandler.getClientVersion());
        LOG.debug("=============================================");
    }

    private static void startChunked(List<CommandType> commands) {
        List<List<CommandType>> chunkedList = chunkList(commands);
        int count = 0;
        for (List<CommandType> chunk : chunkedList) {
            System.setProperty("l4j.logFileName", count + "-" + (count + chunk.size()));
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            new ExecutionController().execute(chunk);
            count += chunk.size();
        }
    }

    private static List<List<CommandType>> chunkList(List<CommandType> commands) {
        int chunkSize = ConfigHandler.getInstance().getChunkSize();
        int count = 0;
        List<List<CommandType>> chunkedList = new ArrayList<>();
        while (count < commands.size()) {
            int end = count + chunkSize > commands.size() ? commands.size() : count + chunkSize;
            chunkedList.add(commands.subList(count, end));
            count += chunkSize;
        }
        return chunkedList;
    }

    // <editor-fold desc="Private Constructor">
    private Main() {
        super();
    }
    // </editor-fold>
}
