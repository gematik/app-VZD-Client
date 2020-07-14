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

import de.gematik.ti.epa.vzd.gemClient.exceptions.GemClientException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    private static ConfigHandler configHandler = null;
    private String configPath;
    private String basePath;
    private String credentialPath;
    private String commandsPath;
    private String retryingOAuthPath;

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
                        configHandler.configPath = new File((args[iIndex + 1])).getAbsolutePath();
                        configHandler.setParams(configHandler.configPath);
                        break;
                    case "-c":
                        configHandler.credentialPath = new File((args[iIndex + 1])).getAbsolutePath();
                        break;
                    case "-b":
                        configHandler.commandsPath = new File((args[iIndex + 1])).getAbsolutePath();
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
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
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
                        default:
                            break;
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            LOG.error("File not found at " + file.getAbsolutePath());
            throw new IllegalArgumentException("No access to given file " + file.getAbsolutePath());
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
    // </editor-fold>
}
