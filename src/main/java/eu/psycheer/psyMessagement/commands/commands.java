package eu.psycheer.psyMessagement.commands;

import eu.psycheer.psyMessagement.ConfigReader;
import eu.psycheer.psyMessagement.PsyMessagement;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class commands implements CommandExecutor, TabExecutor {

    private final PsyMessagement plugin;

    public commands(PsyMessagement plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        int arg = args.length;

        if(!(sender instanceof Player|| sender instanceof ConsoleCommandSender)){
            sender.sendMessage("Only players and console can use this command!");
            return false;
        }
        else{
            //      args[0]
            // /psy reload
            switch (arg){
                case 0:
                    sender.sendMessage("§4[§6PsyMessagement§4] §6Version: " + plugin.getDescription().getVersion());
                    break;
                case 1:
                    plugin.loadConfig(true);
                    sender.sendMessage("§4[§6PsyMessagement§4] §6Configuration reloaded");
                    break;
            }
            return true;
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        int arg = args.length;
        switch (arg){
            case 1:
                return Arrays.asList("reload");
        }
        return new ArrayList<>();
    }
}
