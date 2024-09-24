package eu.psycheer.psyMessagement;

import eu.psycheer.psyMessagement.commands.commands;
import eu.psycheer.psyMessagement.events.PlayerMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class PsyMessagement extends JavaPlugin {

    public Chat chat = null;
    public ConfigReader cr;

    @Override
    public void onEnable() {
        setupCommands();
        setupChat();
        loadConfig(false);

        if(!setupChat() || chat == null){
            this.getLogger().severe(String.format("§4[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        else{
            this.getLogger().warning("§aHooked into Chat " + chat.getName() +" API!");
            new PlayerMessage(this, chat);
            this.getLogger().warning("§aSuccessfully enabled!");
        }
    }

    @Override
    public void onDisable() {
        cr = null;
        chat = null;
    }

    public void loadConfig(boolean reload){
        if(this.getDataFolder().exists()){
            this.getLogger().warning("§aPlugin folder exists!");
            getConfig();
        }else{
            this.getLogger().severe("§aCreating plugin folder");
            saveDefaultConfig();
            getConfig();
        }

        if(!reload)
        {
            cr = new ConfigReader(this);
            cr.load(getConfig());
            cr.loadPerms(getConfig());
            getLogger().warning("Loaded");
        }
        else{
            reloadConfig();
            cr.load(getConfig());
            getLogger().warning("Reloaded");
        }
    }

    private void setupCommands(){
        getCommand("psy").setExecutor(new commands(this));
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
