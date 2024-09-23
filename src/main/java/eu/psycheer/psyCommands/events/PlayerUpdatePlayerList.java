package eu.psycheer.psyCommands.events;

import eu.psycheer.psyCommands.PsyCommands;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerUpdatePlayerList implements Listener {
    public final PsyCommands plugin;
    private Player player = null;
    private String playerName;
    private String decoratedName;
    private Chat messageConstructor;

    public PlayerUpdatePlayerList(PsyCommands plugin, Chat chat){
        this.plugin = plugin;
        messageConstructor = chat;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().warning("PlayerMessage event is listening...");
    }
    @EventHandler
    private void PlayerJoinEvent(PlayerJoinEvent e){
        player = e.getPlayer();
        playerName = e.getPlayer().getName();
        decoratedName = messageConstructor.getPlayerPrefix(player) + playerName + messageConstructor.getPlayerSuffix(player);
        player.setPlayerListName(decoratedName);
    }
}
