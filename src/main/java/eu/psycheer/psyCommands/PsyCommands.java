package eu.psycheer.psyCommands;

import eu.psycheer.psyCommands.events.PlayerUpdatePlayerList;
import eu.psycheer.psyCommands.events.PlayerMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PsyCommands extends JavaPlugin {

    public Chat chat = null;
    public File dataFile;
    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("LuckPerms") == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no LuckPerms was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }

        if(this.getDataFolder().exists()){
            return;
        }else{
            //this.dataFile = new File(this.getDataFolder(), saveDefaultConfig());
        }

        setupChat();
        if(!setupChat() || chat == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }else{
            this.getLogger().warning("Hooked into Chat " + chat +" API!");
            //new PlayerUpdatePlayerList(this, chat);
            new PlayerMessage(this, chat);
            this.getLogger().warning("PsyCommands successfully enabled!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
