package eu.psycheer.psyMessagement;

import eu.psycheer.psyMessagement.events.PlayerMessage;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigReader {

    private final PsyMessagement plugin;
    public String permissionsColors = null;
    public String custom = null;
    public String format = null;
    public String joinMessage = null;
    public FileConfiguration config;

    public ConfigReader(PsyMessagement plugin) {
        this.plugin = plugin;
    }

    public void load(FileConfiguration config){

        this.config = config;

        joinMessage = config.getString("Messages.join-message");
        custom = config.getString("Messages.custom");
        format = config.getString("Messages.format");
        //plugin.getLogger().warning(joinMessage);
        //plugin.getLogger().warning(permissionsColors);
        //plugin.getLogger().info(custom);
        //plugin.getLogger().warning(format);
    }

    public void loadPerms(FileConfiguration config){
        permissionsColors = config.getString("Colors.chatcolor-permission-suffix");
    }

}
