package wtf.eugenio.corumcore.managers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wtf.eugenio.corumcore.CorumCore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CountdownManager {
    public static int taskID;

    public static Date countdownlimit;

    public static void startCosmeticCountdown() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorumCore.getInstance(), () -> {
            Object remainingTime = getRemainingTime(false);
            if (!remainingTime.equals(false)) sendActionCountdown((String) remainingTime);
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

    public static Object getRemainingTime(boolean readable) {
        long diff = countdownlimit.getTime() - System.currentTimeMillis();
        int s = (int) TimeUnit.MILLISECONDS.toSeconds(diff);
        int m = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
        int h = (int) TimeUnit.MILLISECONDS.toHours(diff);
        int d = (int) TimeUnit.MILLISECONDS.toDays(diff);
        if (diff > 0) {
            StringBuilder remainingTime = new StringBuilder();
            if (d != 0) remainingTime.append(d).append("día(s), ");
            String format = "%02d:%02d:%02d";
            if (readable) format = "%02d horas, %02d minutos y %02d segundos";
            remainingTime.append(String.format(format, h, m, s));
            return remainingTime.toString();
        } else {
            return false;
        }
    }
}
