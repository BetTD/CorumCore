package wtf.eugenio.corumcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

public class EventListener implements Listener {
    // No debería dejar así el código, debería poner un caso else para que no envíe el mensaje si está en vanish, pero
    // de eso ya se encarga SuperVanish, así que... supongo que así nos sirve.
    // Mensajes movidos a wtf.eugenio.corumcore.config.Settings -- Eugenio
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) e.setJoinMessage(CorumCore.getInstance().getSettings().joinMessage.replace("{PLAYER}", p.getName()));

        StringBuilder motd = new StringBuilder();
        for (int i = 0; i < CorumCore.getInstance().getSettings().motdLines; i++) motd.append("\n");
        motd.append(CorumCore.getInstance().getSettings().joinMotd.replace("{PLAYER}", p.getName()));

        Bukkit.getScheduler().runTaskLater(CorumCore.getInstance(), () -> p.sendMessage(motd.toString()), 20L);
    }

    // Mensajes movidos a wtf.eugenio.corumcore.config.Settings -- Eugenio
    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!isVanished(p)) e.setQuitMessage(CorumCore.getInstance().getSettings().leaveMessage.replace("{PLAYER}", p.getName()));
    }

    // Code from https://www.spigotmc.org/resources/supervanish-be-invisible.1331/
    // Simple dependency-less method to check if a player is vanished, works with SuperVanish, PremiumVanish, VanishNoPacket, etc...
    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) if (meta.asBoolean()) return true;
        return false;
    }
}
