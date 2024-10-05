package eu.psycheer.psyMessagement.events;

import eu.psycheer.psyMessagement.PsyMessagement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerJoin implements Listener {

    public Chat messageConstructor;
    public final PsyMessagement plugin;
    private final PlayerMessage pm;
    private String joinMessage;
    private String leftMessage;

    public PlayerJoin(PsyMessagement plugin, Chat chat, PlayerMessage pm){
        //Boolean join = (Boolean) plugin.getConfig().getString("Messages.enabled");
        this.plugin = plugin;
        this.pm = pm;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerJoinAndLeave event is listening...");
        joinMessage = plugin.cr.joinMessage;
        leftMessage = plugin.cr.leaveMessage;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerJoinE(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String prefix = messageConstructor.getPlayerPrefix(player);
        String suffix = messageConstructor.getPlayerSuffix(player);
        String displayName = prefix + playerName + suffix;

        if(joinMessage != null){
            e.setJoinMessage(null);
            joinMessage = joinMessage.replace("%DISPLAY_NAME%", displayName);
            joinMessage = joinMessage.replace("%PREFIX%", prefix);
            joinMessage = joinMessage.replace("%SUFFIX%", suffix);
            joinMessage = joinMessage.replace("%PLAYER%", e.getPlayer().getName());
            TextComponent content = (TextComponent) MiniMessage.miniMessage().deserialize(joinMessage);
            send(content);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerLeft(PlayerQuitEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String prefix = messageConstructor.getPlayerPrefix(player);
        String suffix = messageConstructor.getPlayerSuffix(player);
        String displayName = prefix + playerName + suffix;

        if(leftMessage != null){
            e.setQuitMessage(null);
            leftMessage = leftMessage.replace("%DISPLAY_NAME%", displayName);
            leftMessage = leftMessage.replace("%PREFIX%", prefix);
            leftMessage = leftMessage.replace("%SUFFIX%", suffix);
            leftMessage = leftMessage.replace("%PLAYER%", e.getPlayer().getName());
            TextComponent content = (TextComponent) MiniMessage.miniMessage().deserialize(leftMessage);
            send(content);
        }
    }

    private void send(Component content){
        pm.audience.sendMessage(content);
    }

}
