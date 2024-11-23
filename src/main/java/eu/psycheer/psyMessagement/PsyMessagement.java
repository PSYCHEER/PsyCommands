package eu.psycheer.psyMessagement;

import eu.psycheer.psyMessagement.events.PlayerJoinLeave;
import org.bstats.bukkit.Metrics;

import eu.psycheer.psyMessagement.commands.commands;
import eu.psycheer.psyMessagement.events.PlayerMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;


public final class PsyMessagement extends JavaPlugin {

    public Chat chat = null;
    public ConfigReader cr;
    int bstatsID = 23879;
    PlayerMessage pm = null;
    boolean debug;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, bstatsID);
        update();
        setupChat();
        setupCommands();
        loadConfig(false);
        debug = getConfig().getBoolean("debug");

        if(!setupChat() || chat == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        else{
            this.getLogger().warning("Hooked into Chat " + chat.getName());
            this.getLogger().warning("Successfully enabled!");
        }
    }

    @Override
    public void onDisable() {
        cr = null;
        chat = null;
    }

    public void loadConfig(boolean reload){
        if(!reload)
        {
            if(this.getDataFolder().exists()){
                this.getLogger().info("Found existing folder!");
                getConfig();
                File config = new File(getDataFolder(), "config.yml");
                if(!config.exists())
                {
                    this.getLogger().warning("No config found!");
                    saveDefaultConfig();
                    getConfig();
                }
            }
            else{
                this.getLogger().info("Creating plugin folder!");
                saveDefaultConfig();
                getConfig();
            }

            cr = new ConfigReader(this);
            cr.load(getConfig(), debug);
            cr.loadPerms(getConfig());
            pm = new PlayerMessage(this, chat);
            if(debug)
                getLogger().warning("Loaded");
        }
        else{
            reloadConfig();
            cr.load(getConfig(), debug);
            new PlayerJoinLeave(this, chat, pm);
            if(debug)
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

    private void update(){
        if(!Objects.equals(getConfig().getString("Version"), this.getPluginMeta().getVersion())){
            getLogger().warning("Config version is not equal to plugin! Updating config...");
            getConfig().set("debug", false);
            getConfig().set("Version", this.getPluginMeta().getVersion());
            saveConfig();
        }
        else{
            return;
        }
    }
}
