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

package de.gematik.ti.epa.vzd.gem.command;

import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.exceptions.ReadException;
import de.gematik.ti.epa.vzd.gem.invoker.ConfigHandler;
import generated.CommandListType;
import generated.CommandType;
import generated.ObjectFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * This class is responsible for reading the commands out of the .xml File. Before the commands can be build the ConfigHandler have to be initialized
 */
public class CommandsBuilder {

    private JAXBContext jaxbContext;
    private Logger LOG = LoggerFactory.getLogger(CommandsBuilder.class);

    private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    /**
     * Standard constructor initialize an jaxbContext
     */
    public CommandsBuilder() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            throw new ReadException("Error occurred by creating JAXBContext");
        }
    }

    /**
     * Reads the given command file and creates a list of commands to execute
     *
     * @return CommandListType - List of all commands to execute
     */
    public List<CommandType> buildCommands() {
        CommandListType commandList;
        ConfigHandler configHandler = ConfigHandler.getInstance();
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Document doc = builderFactory.newDocumentBuilder()
                .parse(new File(configHandler.getCommandsPath()));
            Object obj = unmarshaller.unmarshal(doc);
            Object commands = ((JAXBElement) obj).getValue();
            if (commands instanceof CommandListType) {
                commandList = (CommandListType) commands;
                if (addId(commandList)) {
                    writeCommandDataWithIds(commandList);
                }
                LOG.debug("Commands have been build");
                return commandList.getCommand();
            }
        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            throw new ReadException(
                "An error have been occurred while reading your command file. Please check if this file is a valid .xml file");
        } catch (IOException e) {
            throw new ReadException(
                "A problem with your named file have occurred. Please if check " + configHandler.getCommandsPath() + " exist");
        }
        return null;
    }

    private boolean addId(CommandListType commandList) {
        int counter = 1;
        boolean idsSet = false;
        Set<String> givenIds = new HashSet<>();
        for (CommandType command : commandList.getCommand()) {
            if (!StringUtils.isBlank(command.getCommandId()) && !givenIds.add(command.getCommandId())) {
                LOG.error("The predefined ID \"" + command.getCommandId() + "\" occurs twice");
                throw new CommandException("The predefined ID \"" + command.getCommandId() + "\" occurs twice");
            }
        }
        for (CommandType command : commandList.getCommand()) {
            while (givenIds.contains(String.valueOf(counter))) {
                counter++;
            }
            if (StringUtils.isBlank(command.getCommandId())) {
                command.setCommandId(String.valueOf(counter));
                counter++;
                idsSet = true;
            }
        }
        if (idsSet) {
            LOG.debug("IDs have been set automatically");
        }
        return idsSet;
    }

    private void writeCommandDataWithIds(CommandListType commandList) throws JAXBException {
        JAXBElement<CommandListType> element = new ObjectFactory().createCommandList(commandList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(element, new File(ConfigHandler.getInstance().getCommandsPath()));
    }

}
