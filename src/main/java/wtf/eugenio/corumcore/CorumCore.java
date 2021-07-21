package wtf.eugenio.corumcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.eugenio.corumcore.commands.CCCommand;
import wtf.eugenio.corumcore.commands.VidasCommand;
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
        registerComamnds();
        Object parsedDate = VidasManager.parseDate(getConfig().getString("countdown-limit"));
        if (parsedDate.equals(false)) {
            Bukkit.getLogger().log(Level.WARNING, "No fue posible parsear la fecha, así que el countdown no ha sido activado.");
        } else {
            VidasManager.countdownlimit = (Date) parsedDate;
            VidasManager.startCosmeticCountdown();
        }
    }

    private void registerComamnds() {
        getCommand("cc").setExecutor(new CCCommand(this));
        getCommand("vidas").setExecutor(new VidasCommand());
    }
}
