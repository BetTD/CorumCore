package wtf.eugenio.corumcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.managers.VidasManager;

public class CCCommand implements CommandExecutor {
    private final CorumCore plugin;
    public CCCommand(CorumCore plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("corum.admin")) {
            sender.sendMessage("§cNo puedes ejecutar este comando.");
            return true;
        }

        if (args.length!=0) {
            switch (args[0]) {
                case "empezardesde0":
                    VidasManager.startFresh();
                    break;
                case "salvarvida":
                    Bukkit.getScheduler().runTaskAsynchronously(CorumCore.getInstance(), () -> VidasManager.saveLife(args[1]));
                    break;
                case "endeardia":
                    VidasManager.endDay();
                    break;
                case "stopcountdown":
                    VidasManager.stopCosmeticCountdown(false);
                    sender.sendMessage("§eCuenta atrás desactivada");
                    break;
                case "recargar":
                    plugin.reloadConfig();
                    sender.sendMessage("§aLa configuración ha sido recargada.");
                    if (Bukkit.getScheduler().isCurrentlyRunning(VidasManager.taskID)) {
                        VidasManager.stopCosmeticCountdown(true);
                        VidasManager.startCosmeticCountdown(plugin.getConfig().getString("countdown-limit"));
                        sender.sendMessage("§aLa cuenta atrás también ha sido recargada para reflejar posibles cambios.");
                    }
            }
        } else {
            sender.sendMessage("§cUso: /cc <empezardesde0|salvarvida|endeardia|stopcountdown|recargar>");
        }


        return true;
    }
}
