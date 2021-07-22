package wtf.eugenio.corumcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.eugenio.corumcore.commands.CCCommand;
import wtf.eugenio.corumcore.commands.VidasCommand;
import wtf.eugenio.corumcore.managers.CountdownManager;
import wtf.eugenio.corumcore.managers.DiscordManager;
import wtf.eugenio.corumcore.managers.VidasManager;

import java.util.Date;
import java.util.logging.Level;

public class CorumCore extends JavaPlugin {
    @Getter
    private static CorumCore instance;

    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        VidasManager.init();
        registerStuff();
        Object parsedDate = CountdownManager.parseDate(getConfig().getString("countdown-limit"));
        if (parsedDate.equals(false)) {
            Bukkit.getLogger().log(Level.WARNING, "No fue posible parsear la fecha, así que el countdown no ha sido activado.");
        } else {
            CountdownManager.countdownlimit = (Date) parsedDate;
            CountdownManager.startCosmeticCountdown();
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, DiscordManager::sendStartWebhook);
    }

    public void onDisable() {
        DiscordManager.sendStoppingWebhook();
    }

    private void registerStuff() {
        getCommand("cc").setExecutor(new CCCommand());
        getCommand("vidas").setExecutor(new VidasCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
