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

package de.gematik.ti.epa.vzd.gem.invoker;

import de.gematik.ti.epa.vzd.gem.exceptions.GemClientException;
import generated.CommandType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class reads the user input and store all the locations and configurations the user did
 * <p>
 * Implemented as singelton
 */
public final class ConfigHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigHandler.class);
    private static final String BASE_PATH = "base";
    private static final String RETRY_OAUTH = "retryingOAuth";
    private static final String COMMANDS = "commands";
    private static final String PARA_EXECUTIONS = "maxExecutionsPerOperation";
    private static final String PARA_EXECUTORS = "maxOperations";
    private static final String PROXY_HOST = "proxyHost";
    private static final String PROXY_PORT = "proxyPort";
    private static final int MAX_EXECUTORS = 9;
    private static final int MAX_EXECUTIONS_PER_EXECUTOR = 20;

    private static ConfigHandler configHandler = null;
    private String configPath;
    private String basePath;
    private String credentialPath;
    private String commandsPath;
    private String retryingOAuthPath;
    private String proxyHost;
    private int maxParallelExecutor = 1;
    private int maxParaExecutionsPerExecutor = 1;
    private int connectionCount;
    private int proxyPort = -1;
    private boolean proxySet;

    private ConfigHandler() {
    }

    /**
     * This function returns the instance of the ConfigHandler as long as it is initialized
     *
     * @return
     */
    public static ConfigHandler getInstance() {
        if (configHandler == null) {
            throw new GemClientException("A ConfigHandler have to be initialized first");
        }
        return configHandler;
    }

    /**
     * Create an instance of a ConfigHandler while reading the commandline
     *
     * @param args input parameter from commandline
     */
    public static ConfigHandler init(String[] args) {
        if (configHandler == null) {
            configHandler = new ConfigHandler();
            for (int iIndex = 0; iIndex < args.length; iIndex++) {
                switch (args[iIndex]) {
                    case "-p":
                        configHandler.configPath = new File(args[iIndex + 1]).getAbsolutePath();
                        configHandler.setParams(configHandler.configPath);
                        break;
                    case "-c":
                        configHandler.credentialPath = new File(args[iIndex + 1]).getAbsolutePath();
                        break;
                    case "-b":
                        configHandler.commandsPath = new File(args[iIndex + 1]).getAbsolutePath();
                        break;
                    case "-h":
                        configHandler.proxyHost = args[iIndex + 1];
                        break;
                    case "-d":
                        try {
                            configHandler.proxyPort = Integer.parseInt(args[iIndex + 1]);
                        } catch (NumberFormatException ex) {
                            LOG.error("Your named proxy port is not valid." + args[iIndex + 1]);
                            throw new IllegalArgumentException("Your named proxy port is not valid." + args[iIndex + 1]);
                        }
                        break;
                    default:
                        break;
                }
            }
        } else {
            throw new GemClientException("Configurations are only allowed to set once");
        }
        if (StringUtils.isBlank(configHandler.credentialPath) || StringUtils
            .isBlank(configHandler.configPath)) {
            LOG.error("At least CredentialPath or ConfigPath is missing and have to be set");
            throw new GemClientException(
                "At least CredentialPath or ConfigPath is missing and have to be set");
        }
        LOG.debug("Configurations have been set");
        return configHandler;
    }

    private void setParams(String arg) {
        File file = new File(arg);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                if (StringUtils.isNotBlank(line) && line.contains("=")) {
                    String[] param = line.split("=");
                    String name = param[0];
                    String value = param[1];
                    switch (name) {
                        case BASE_PATH:
                            configHandler.basePath = value;
                            break;
                        case RETRY_OAUTH:
                            configHandler.retryingOAuthPath = value;
                            break;
                        case COMMANDS:
                            if (StringUtils.isBlank(configHandler.commandsPath)) {
                                configHandler.commandsPath = new File(value).getAbsolutePath();
                            }
                            break;
                        case PARA_EXECUTORS:
                            try {
                                maxParallelExecutor = Integer.parseInt(value.trim());
                                if (maxParallelExecutor > MAX_EXECUTORS) {
                                    maxParallelExecutor = MAX_EXECUTORS;
                                } else if (maxParallelExecutor <= 0) {
                                    maxParallelExecutor = 1;
                                }
                            } catch (NumberFormatException ex) {
                                LOG.error("maxExecutors have to be a number");
                            }
                            break;
                        case PARA_EXECUTIONS:
                            try {
                                maxParaExecutionsPerExecutor = Integer.parseInt(value.trim());
                                if (maxParaExecutionsPerExecutor > MAX_EXECUTIONS_PER_EXECUTOR) {
                                    maxParaExecutionsPerExecutor = MAX_EXECUTIONS_PER_EXECUTOR;
                                } else if (maxParaExecutionsPerExecutor <= 0) {
                                    maxParaExecutionsPerExecutor = 1;
                                }
                            } catch (NumberFormatException ex) {
                                LOG.error("maxExecutionsPerExecutor have to be a number");
                            }
                            break;
                        case PROXY_HOST:
                            if (StringUtils.isBlank(configHandler.proxyHost)) {
                                configHandler.proxyHost = value;
                            }
                            break;
                        case PROXY_PORT:
                            if (configHandler.proxyPort == -1) {
                                try {
                                    configHandler.proxyPort = Integer.parseInt(value.trim());
                                } catch (NumberFormatException ex) {
                                    LOG.error("Your named proxy port is not valid." + value);
                                    throw new IllegalArgumentException("Your named proxy port is not valid." + value);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                line = br.readLine();
            }
            connectionCount = maxParaExecutionsPerExecutor * maxParallelExecutor;
        } catch (IOException e) {
            LOG.error("File not found at " + file.getAbsolutePath());
            throw new IllegalArgumentException("No access to given file " + file.getAbsolutePath());
        }

        if (StringUtils.isNotBlank(proxyHost) && (proxyPort != -1)) {
            proxySet = true;
        }

        if (StringUtils.isBlank(configHandler.retryingOAuthPath)) {
            LOG.error("No authorization server named");
            throw new GemClientException("No authorization server named");
        }
        if (StringUtils.isBlank(configHandler.basePath)) {
            LOG.error("No vzd server named");
            throw new GemClientException("No server named");
        }
    }

    public void adjustConnectionCount(List<CommandType> commands) {
        Map<String, List<CommandType>> commandMap = commands.stream().collect(Collectors.groupingBy(CommandType::getName));
        int count = 0;
        for (var entry : commandMap.entrySet()) {
            if (entry.getValue().size() >= MAX_EXECUTIONS_PER_EXECUTOR) {
                count += MAX_EXECUTIONS_PER_EXECUTOR;
            } else {
                count += entry.getValue().size();
            }
        }
        this.connectionCount = count < this.connectionCount ? count : this.connectionCount;
    }

    // <editor-fold desc="Getter & Setter">

    public static void setConfigHandler(ConfigHandler setConfigHandler) {
        configHandler = setConfigHandler;
    }

    public String getRetryingOAuthPath() {
        return retryingOAuthPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public String getCredentialPath() {
        return credentialPath;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getCommandsPath() {
        return commandsPath;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public boolean isProxySet() {
        return proxySet;
    }

    public int getMaxParallelExecutor() {
        return maxParallelExecutor;
    }

    public int getMaxParaExecutionsPerExecutor() {
        return maxParaExecutionsPerExecutor;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    // </editor-fold>
}
