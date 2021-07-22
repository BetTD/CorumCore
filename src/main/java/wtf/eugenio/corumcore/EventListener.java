package wtf.eugenio.corumcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

public class EventListener implements Listener {
    // Sí, he usado ChatColor, lo siento Eugenio, pero me daba pereza reemplazar todos los & por §
    FileConfiguration config = CorumCore.getInstance().getConfig();
    String joinMsg = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.join-message"));
    String leaveMsg = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.leave-message"));
    int motdNewlines = config.getInt("joinleave.join-motd-newlines");
    String motdMsg = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.join-motd-message"));

    // No debería dejar así el código, debería poner un caso else para que no envíe el mensaje si está en vanish, pero
    // de eso ya se encarga SuperVanish, así que... supongo que así nos sirve.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) {
            joinMsg = joinMsg.replace("{PLAYER}", p.getName());
            e.setJoinMessage(joinMsg);
        }

        StringBuilder motd = new StringBuilder();
        for (int i = 0; i < motdNewlines; i++) {
            motd.append("\n");
        }
        motd.append(motdMsg);

        Bukkit.getScheduler().runTaskLater(CorumCore.getInstance(), () -> {
            p.sendMessage(motd.toString());
        }, 20L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) {
            leaveMsg = leaveMsg.replace("{PLAYER}", p.getName());
            e.setQuitMessage(leaveMsg);
        }
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
