package eu.psycheer.psyCommands.events;

import eu.psycheer.psyCommands.PsyCommands;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlayerMessage implements Listener{
    public Chat messageConstructor;
    public Audience viewers;
    public final PsyCommands plugin;

    public PlayerMessage(PsyCommands plugin, Chat chat){
        this.plugin = plugin;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerMessage event is listening...");
    }

    @EventHandler
    private void PlayerSentMessage(AsyncChatEvent e){
        Player player = e.getPlayer();
        String playerName = e.getPlayer().getName();
        String wip = messageConstructor.getPlayerPrefix(player) + playerName;
        player.setDisplayName(wip);


    }
}
