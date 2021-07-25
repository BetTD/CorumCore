package wtf.eugenio.corumcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.managers.CountdownManager;
import wtf.eugenio.corumcore.managers.ScoreboardManager;
import wtf.eugenio.corumcore.managers.VidasManager;

import java.util.Date;

public class CCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("corum.admin")) {
            sender.sendMessage("§cNo puedes ejecutar este comando.");
            return true;
        }

        String helpmsg = "§8§m         §r §f§lCORUMCORE§r §8§m         §r\n" +
                "§7§oCorumCore versión "+ CorumCore.getInstance().getDescription().getVersion() + "\n" +
                "§f• /cc empezardesde0\n" +
                "§f• /cc salvarvida\n" +
                "§f• /cc endeardia\n" +
                "§f• /cc stopcountdown\n" +
                "§f• /cc startcountdown\n" +
                "§f• /cc ajustarcountdown\n" +
                "§f• /cc recargar|reload\n" +
                "§8§m         §r §f§lCORUMCORE§r §8§m         §r";

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
                    if (CountdownManager.isCountdownRunning()) {
                        CountdownManager.stopCosmeticCountdown(false);
                        sender.sendMessage("§eCuenta atrás desactivada.");
                    } else {
                        sender.sendMessage("§cLa cuenta atrás ya está desactivada.");
                    }
                    break;
                case "startcountdown":
                    if (CountdownManager.isCountdownRunning()) {
                        sender.sendMessage("§cLa cuenta atrás ya está activada.");
                    } else {
                        CountdownManager.startCosmeticCountdown();

                        sender.sendMessage("§aCuenta atrás activada.");
                    }
                    break;
                case "ajustarcountdown":
                    if (args.length == 3) {
                        Object parsedDate = CountdownManager.parseDate(args[1] + " " + args[2]);
                        if (parsedDate.equals(false)) {
                            sender.sendMessage("§cEl formato de la fecha es incorrecto.");
                        } else {
                            CorumCore.getInstance().getConfig().set("countdown-limit", args[1] + " " + args[2]);
                            CorumCore.getInstance().saveConfig();
                            CountdownManager.countdownlimit = (Date) parsedDate;
                            if (CountdownManager.isCountdownRunning()) CountdownManager.stopCosmeticCountdown(true);
                            CountdownManager.startCosmeticCountdown();
                        }
                    } else {
                        sender.sendMessage("§e§lUSO:§f /cc ajustarcountdown <fecha>" + "\n" + "§7§oFormato: dd/MM/yyyy HH:mm");
                    }
                    break;
                case "setscore":
                    ScoreboardManager.setScoreboard((Player) sender);
                    break;
                case "reload":
                case "recargar":
                    CorumCore.getInstance().reloadConfig();
                    CorumCore.getInstance().getSettings().initializeMessages();
                    sender.sendMessage("§aLa configuración ha sido recargada.");
                    if (CountdownManager.isCountdownRunning()) {
                        CountdownManager.stopCosmeticCountdown(true);
                        CountdownManager.startCosmeticCountdown();
                        sender.sendMessage("§aLa cuenta atrás también ha sido recargada para reflejar posibles cambios.");
                    }
                    break;
                default:
                    sender.sendMessage("§cEste subcomando no existe, usa §l/cc§c para ver todos los subcomandos.");
                    break;
            }
        } else {
            sender.sendMessage(helpmsg);
        }

        return true;
    }
}
