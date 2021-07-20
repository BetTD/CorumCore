package wtf.eugenio.corumcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.managers.VidasManager;

public class CCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("corum.admin")) {
            sender.sendMessage("§cNo puedes ejecutar este comando.");
            return true;
        }

        if (args.length != 0) {
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
                    if (VidasManager.isCountdownRunning()) {
                        VidasManager.stopCosmeticCountdown(false);
                        sender.sendMessage("§eCuenta atrás desactivada.");
                    } else {
                        sender.sendMessage("§cLa cuenta atrás ya está desactivada.");
                    }
                    break;
                case "startcountdown":
                    if (VidasManager.isCountdownRunning()) {
                        sender.sendMessage("§cLa cuenta atrás ya está activada.");
                    } else {
                        VidasManager.startCosmeticCountdown(CorumCore.getInstance().getConfig().getString("countdown-limit"));
                        sender.sendMessage("§aCuenta atrás activada.");
                    }
                    break;
                case "recargar":
                    CorumCore.getInstance().reloadConfig();
                    sender.sendMessage("§aLa configuración ha sido recargada.");
                    if (VidasManager.isCountdownRunning()) {
                        VidasManager.stopCosmeticCountdown(true);
                        VidasManager.startCosmeticCountdown(CorumCore.getInstance().getConfig().getString("countdown-limit"));
                        sender.sendMessage("§aLa cuenta atrás también ha sido recargada para reflejar posibles cambios.");
                    }
                    break;
            }
        } else {
            sender.sendMessage("§cUso: /cc <empezardesde0|salvarvida|endeardia|stopcountdown|startcountdown|recargar>");
        }

        return true;
    }
}
