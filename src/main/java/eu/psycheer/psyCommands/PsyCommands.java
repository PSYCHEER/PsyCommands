package eu.psycheer.psyCommands;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class PsyCommands extends JavaPlugin {

    public static Chat chat = null;
    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("LuckPerms") == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
        setupChat();
        if(!setupChat() || chat == null){
            this.getLogger().severe(String.format("[%s] - Disabled because no Vault was found!", getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }else{
            this.getLogger().warning("Hooked into Chat " + chat +" API!");
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
