package wtf.eugenio.corumcore.managers;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.EventListener;

import java.util.HashMap;
import java.util.List;

public class ScoreboardManager {
    public static void setScoreboard(Player p) {
        TitleManagerAPI tm = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        tm.removeScoreboard(p);
        tm.giveScoreboard(p);
        tm.setScoreboardTitle(p, ChatColor.translateAlternateColorCodes('&', "&2&lCORUM"));

        for (int i = 1; i < scoreboard(p).size(); i++) {
            tm.setScoreboardValue(p, i, ChatColor.translateAlternateColorCodes('&', scoreboard(p).get(i)));
        }
    }

    public static void updateScoreboard(Player p) {
        TitleManagerAPI tm = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        for (int i = 1; i < scoreboard(p).size(); i++) {
            tm.setScoreboardValue(p, i, ChatColor.translateAlternateColorCodes('&', scoreboard(p).get(i)));
        }
    }

    private static HashMap<Integer, String> scoreboard(Player p) {
        HashMap<Integer, String> scoreboard = new HashMap<>();
        List<String> challenges = CorumCore.getInstance().getSettings().challenge;
        scoreboard.put(1, "&8");
        scoreboard.put(2, "&8» &a" + p.getName());
        scoreboard.put(3, "&8");
        scoreboard.put(4, "&8» &e&lRetos");
        int lines;
        if (VidasManager.undoneChallenges.contains(p.getPlayer().getName())) {
            lines = challenges.size();
            for (int i = 0; i < lines; i++) {
                scoreboard.put(i+5, challenges.get(i));
            }
        } else {
            lines = 1;
            scoreboard.put(5, " &a¡Completado!");
        }
        scoreboard.put(5+lines, "&8");
        scoreboard.put(6+lines, "&8» &e&lEn línea");
        int unvanishedPlayers = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!EventListener.isVanished(player)) {
                unvanishedPlayers++;
            }
        }
        scoreboard.put(7+lines, " &a" + unvanishedPlayers);
        scoreboard.put(8+lines, "&8");
        scoreboard.put(9+lines, "&8» &e&lVidas");
        String vidas;
        if (VidasManager.health.containsKey(p.getPlayer().getName())) {
            vidas = String.valueOf(VidasManager.getLifes(p.getPlayer()));
        } else {
            vidas = "Sin vidas";
        }
        scoreboard.put(10+lines, " &c" + vidas);
        scoreboard.put(11+lines, "&8");
        return scoreboard;
    }

    public static void setScoreboardForEveryone() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            setScoreboard(player);
        }
    }

    public static void updateScoreboardForEveryone() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }
}
