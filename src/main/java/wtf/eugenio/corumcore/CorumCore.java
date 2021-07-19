package wtf.eugenio.corumcore;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.eugenio.corumcore.commands.CCCommand;
import wtf.eugenio.corumcore.commands.VidasCommand;
import wtf.eugenio.corumcore.managers.VidasManager;

public class CorumCore extends JavaPlugin {
    @Getter
    private static CorumCore instance;

    public void onEnable() {
        instance = this;

        VidasManager.init();

        registerComamnds();

        VidasManager.cHours = 24;
        VidasManager.cMinutes = 60;
        VidasManager.cSeconds = 60;

        VidasManager.startCosmeticCountdown();
    }

    private void registerComamnds() {
        getCommand("cc").setExecutor(new CCCommand());
        getCommand("vidas").setExecutor(new VidasCommand());
    }
}
