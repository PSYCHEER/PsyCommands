package eu.psycheer.psyMessagement.events;

import eu.psycheer.psyMessagement.ConfigReader;
import eu.psycheer.psyMessagement.PsyMessagement;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerMessage implements Listener {
    public Chat messageConstructor;
    public final PsyMessagement plugin;
    public Audience audience = Audience.audience((Audience) Bukkit.getServer());
    public String permissionsColors;

    public PlayerMessage(PsyMessagement plugin, Chat chat){
        this.plugin = plugin;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerMessage event is listening...");
        permissionsColors = "PsyMessagement." + plugin.cr.permissionsColors;
        new PlayerJoin(plugin, chat, this);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerSentMessage(AsyncChatEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String prefix = messageConstructor.getPlayerPrefix(player);
        String suffix = messageConstructor.getPlayerSuffix(player);
        String content = PlainTextComponentSerializer.plainText().serialize(e.originalMessage());
        String custom = plugin.cr.custom;
        String displayName = prefix + playerName + suffix;
        String format = plugin.cr.format;
        format = format.replace("%DISPLAY_NAME%", displayName);
        format = format.replace("%PREFIX%", prefix);
        format = format.replace("%SUFFIX%", suffix);
        format = format.replace("%PLAYER%", e.getPlayer().getName());
        format = format.replace("%CUSTOM%", custom);
        format = format.replace("%MESSAGE%", content);

        if(e.getPlayer().hasPermission(permissionsColors)){
            TextComponent parsed = (TextComponent) MiniMessage.miniMessage().deserialize(format);
            audience.sendMessage(parsed);
            e.setCancelled(true);
        }
        else{
            content = content.replace("<", "\\<");
            content = content.replace(">", "\\>");
            TextComponent parsed = (TextComponent) MiniMessage.miniMessage().deserialize(format);
            audience.sendMessage(parsed);
            e.setCancelled(true);
        }
    }
}
