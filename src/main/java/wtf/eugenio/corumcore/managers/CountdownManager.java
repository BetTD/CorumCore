package wtf.eugenio.corumcore.managers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wtf.eugenio.corumcore.CorumCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class CountdownManager {
    public static int taskID;

    public static Date countdownlimit;

    public static void startCosmeticCountdown() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorumCore.getInstance(), () -> {
            long diff = countdownlimit.getTime() - System.currentTimeMillis();
            int s = (int) (diff / 1000) % 60 ;
            int m = (int) ((diff / (1000*60)) % 60);
            int h = (int) ((diff / (1000*60*60)) % 24);
            String msg = "§e§lSiguiente desafío:§f " + String.format("%02d", h) + ":" + String.format("%02d", m) + ":" + String.format("%02d", s);
            if (diff > 0) sendActionCountdown(msg);
            else sendActionCountdown("§e§l¡Desafío terminado!§6 Pronto se anunciará otro desafío.");
        }, 0L, 20L);
    }

    public static void stopCosmeticCountdown(boolean silent) {
        Bukkit.getScheduler().cancelTask(taskID);
        if (!silent) sendActionCountdown("§c§lLa cuenta atrás ha sido desactivada.");
    }

    public static boolean isCountdownRunning() {
        return Bukkit.getScheduler().isCurrentlyRunning(taskID) || Bukkit.getScheduler().isQueued(taskID);
    }

    private static void sendActionCountdown(String msg) {
        for (final Player player : Bukkit.getOnlinePlayers()) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
    }

    public static Object parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
        } catch (java.text.ParseException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Ha ocurrido un error al procesar la fecha \"" + date + "\"\n"+e);
            return false;
        }
    }
}
