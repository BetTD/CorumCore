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
        if (!sender.hasPermission("hg.admin")) {
            sender.sendMessage("§cNo puedes ejecutar este comando.");
            return true;
        }

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
            case "sethourctdwn":
                VidasManager.cHours = Integer.parseInt(args[1]);
            case "setminctdwn":
                VidasManager.cMinutes = Integer.parseInt(args[1]);
            case "setsecctdwn":
                VidasManager.cSeconds = Integer.parseInt(args[1]);
        }

        return true;
    }
}
