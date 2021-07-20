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

        saveDefaultConfig();

        VidasManager.init();

        registerComamnds();

        VidasManager.startCosmeticCountdown(getConfig().getString("countdown-limit"));
    }

    private void registerComamnds() {
        getCommand("cc").setExecutor(new CCCommand(this));
        getCommand("vidas").setExecutor(new VidasCommand());
    }
}
