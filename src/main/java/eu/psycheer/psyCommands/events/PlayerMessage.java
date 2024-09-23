package eu.psycheer.psyCommands.events;

import eu.psycheer.psyCommands.PsyCommands;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.MessageFormat;

public class PlayerMessage implements Listener {
    public Chat messageConstructor;
    public final PsyCommands plugin;
    public Audience audience = Audience.audience((Audience) Bukkit.getServer());

    public PlayerMessage(PsyCommands plugin, Chat chat){
        this.plugin = plugin;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerMessage event is listening...");
    }

    public String Decorated(String message){
        return null;
    }

    @EventHandler
    private void AddAudience(PlayerJoinEvent e){

    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerSentMessage(AsyncChatEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String prefix = messageConstructor.getPlayerPrefix(player);
        String suffix = messageConstructor.getPlayerSuffix(player);
        String displayName = prefix + playerName + suffix;
        String custom = " >> ";
        String content = PlainTextComponentSerializer.plainText().serialize(e.message());
        String format = displayName + custom + content;
        plugin.getLogger().warning(format);
        //plugin.getLogger().warning(e.message().toString());
        TextComponent parsed = (TextComponent) MiniMessage.miniMessage().deserialize(format);
        audience.sendMessage(parsed);
        e.setCancelled(true);
    }
}
