package eu.psycheer.psyMessagement.events;

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
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerMessage implements Listener {
    public Chat messageConstructor;
    public final PsyMessagement plugin;
    public Audience audience = Audience.audience((Audience) Bukkit.getServer());
    public String permissionsColors;

    private final String colorPerm;

    public PlayerMessage(PsyMessagement plugin, Chat chat){
        this.plugin = plugin;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerMessage event is listening...");
        permissionsColors = plugin.getConfig().getString("Colors.chatcolor-permission-suffix");
        colorPerm = "PsyMessagement." + permissionsColors;
        new PlayerJoin(plugin, chat, this);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void PlayerSentMessage(AsyncChatEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String prefix = messageConstructor.getPlayerPrefix(player);
        String suffix = messageConstructor.getPlayerSuffix(player);
        String custom = " >> ";
        String content = PlainTextComponentSerializer.plainText().serialize(e.originalMessage());
        String displayName = prefix + playerName + suffix;

        if(e.getPlayer().hasPermission(colorPerm)){
            String format = displayName + custom + content;
            TextComponent parsed = (TextComponent) MiniMessage.miniMessage().deserialize(format);
            audience.sendMessage(parsed);
            e.setCancelled(true);
        }
        else{
            content = content.replace("<", "");
            content = content.replace(">", "");
            String format = displayName + custom + content;
            TextComponent parsed = (TextComponent) MiniMessage.miniMessage().deserialize(format);
            audience.sendMessage(parsed);
            e.setCancelled(true);
        }
    }
}
