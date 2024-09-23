package eu.psycheer.psyMessagement;

import eu.psycheer.psyMessagement.events.PlayerJoin;
import eu.psycheer.psyMessagement.events.PlayerMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PsyMessagement extends JavaPlugin {

    public Chat chat = null;
    public File dataFile;
    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("LuckPerms") == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no LuckPerms was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }

        loadConfig();
        setupChat();

        if(!setupChat() || chat == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        else{
            this.getLogger().warning("Hooked into Chat " + chat +" API!");
            //new PlayerUpdatePlayerList(this, chat);
            new PlayerMessage(this, chat);
            this.getLogger().warning("PsyMessagement successfully enabled!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    void loadConfig(){
        if(this.getDataFolder().exists()){
            this.getLogger().warning("Plugin folder exists!");
            new ConfigReader(this);
        }else{
            this.getLogger().severe("Creating plugin folder");
            saveDefaultConfig();
            new ConfigReader(this);
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
