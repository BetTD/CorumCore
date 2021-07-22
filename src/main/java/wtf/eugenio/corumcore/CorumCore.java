package wtf.eugenio.corumcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.eugenio.corumcore.commands.CCCommand;
import wtf.eugenio.corumcore.commands.VidasCommand;
import wtf.eugenio.corumcore.config.Settings;
import wtf.eugenio.corumcore.managers.CountdownManager;
import wtf.eugenio.corumcore.managers.VidasManager;
import wtf.eugenio.corumcore.placeholder.CorumPlaceholder;

import java.util.Date;
import java.util.logging.Level;

public class CorumCore extends JavaPlugin {
    @Getter
    private static CorumCore instance;

    @Getter
    private final Settings settings = new Settings();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        VidasManager.init();
        settings.initializeMessages();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) new CorumPlaceholder().register();
        registerStuff();

        Object parsedDate = CountdownManager.parseDate(getConfig().getString("countdown-limit"));
        if (parsedDate.equals(false)) {
            Bukkit.getLogger().log(Level.WARNING, "No fue posible parsear la fecha, así que el countdown no ha sido activado.");
        } else {
            CountdownManager.countdownlimit = (Date) parsedDate;
            CountdownManager.startCosmeticCountdown();
        }
    }

    private void registerStuff() {
        getCommand("cc").setExecutor(new CCCommand());
        getCommand("vidas").setExecutor(new VidasCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
