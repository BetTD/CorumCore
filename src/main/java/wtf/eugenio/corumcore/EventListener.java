package wtf.eugenio.corumcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) e.setJoinMessage("§8[§a+§8] §7" + p.getName());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) e.setQuitMessage("§8[§c-§8] §7" + p.getName());
    }

    // Code from https://www.spigotmc.org/resources/supervanish-be-invisible.1331/
    // Simple dependency-less method to check if a player is vanished, works with SuperVanish, PremiumVanish, VanishNoPacket, etc...
    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
