package de.gematik.ti.epa.vzd.gem.command.parser;

import de.gematik.ti.epa.vzd.gem.exceptions.CommandException;
import de.gematik.ti.epa.vzd.gem.invoker.ConfigHandler;
import generated.CommandType;
import generated.DistinguishedNameType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;

public class ReadUidParser implements CommandParser {

    private static final String name = "readDirectoryEntries";

    @Override
    public List<CommandType> buildCommands() {
        List<CommandType> commands = new ArrayList<>();
        List<String> uids = getUids();
        AtomicReference<Integer> count = new AtomicReference<>(0);
        uids.forEach(id -> {
            CommandType command = new CommandType();
            command.setName(name);
            count.set(count.get() + 1);
            command.setCommandId(String.valueOf(count.get()));
            DistinguishedNameType dn = new DistinguishedNameType();
            dn.setUid(id);
            command.setDn(dn);
            commands.add(command);
        });
        return commands;
    }

    private List<String> getUids() {
        List<String> uids = new ArrayList<>();
        String path = ConfigHandler.getInstance().getCommandsPath();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (StringUtils.isNotBlank(line)) {
                uids.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new CommandException("The command file you have provided is not valid: " + path);
        }
        return uids;
    }
}
