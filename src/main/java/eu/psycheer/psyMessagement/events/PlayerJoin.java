package eu.psycheer.psyMessagement.events;

import eu.psycheer.psyMessagement.PsyMessagement;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoin implements Listener {

    public Chat messageConstructor;
    public final PsyMessagement plugin;
    private PlayerMessage pm;
    private String joinMessage;

    public PlayerJoin(PsyMessagement plugin, Chat chat, PlayerMessage pm){
        //Boolean join = (Boolean) plugin.getConfig().getString("Messages.enabled");
        this.plugin = plugin;
        this.pm = pm;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerJoin event is listening...");
        joinMessage = plugin.getConfig().getString("Messages.join-message");
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerJoinE(PlayerJoinEvent e){

        if(joinMessage != null){
            e.setJoinMessage(null);
            TextComponent content = (TextComponent) MiniMessage.miniMessage().deserialize(joinMessage);
            pm.audience.sendMessage(content);
        }
    }
}
