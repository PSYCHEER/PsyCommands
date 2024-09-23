package eu.psycheer.psyMessagement;

import eu.psycheer.psyMessagement.PsyMessagement;
import eu.psycheer.psyMessagement.events.PlayerMessage;

public class ConfigReader {

    private final PsyMessagement plugin;
    private PlayerMessage pm;

    public ConfigReader(PsyMessagement plugin) {
        this.plugin = plugin;
    }

}
