package wtf.eugenio.corumcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.eugenio.corumcore.managers.VidasManager;

public class VidasCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) sender.sendMessage("§eAhora mismo tienes §c" + VidasManager.health.get(sender.getName()) + " vidas.");
        return false;
    }
}
