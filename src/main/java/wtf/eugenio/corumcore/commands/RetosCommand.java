package wtf.eugenio.corumcore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.managers.CountdownManager;
import wtf.eugenio.corumcore.managers.VidasManager;

public class RetosCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No eres un jugador, por lo que no participas en los desafíos. Qué suerte, ¿eh?");
            return false;
        }

        StringBuilder msg = new StringBuilder();
        StringBuilder desafio = new StringBuilder();
        for (String s : CorumCore.getInstance().getSettings().challenge) {
            desafio.append(s).append("\n");
        }
        msg.append("&8&m       &r &f&lDESAFÍO DIARIO DE HOY&r &8&m       &r").append("\n");
        Object remainingTime = CountdownManager.getRemainingTime(true);
        if (!remainingTime.equals(false)) {

            msg.append(desafio).append("\n");
            if (VidasManager.undoneChallenges.contains(((Player) sender).getPlayer().getName())) {
                msg.append("&eTe quedan ").append((String) remainingTime).append(" para completar el desafío.");
            }
        } else {
            if (args.length > 0 && args[0].equals("antiguo")) {
                msg.append("&7Este es el desafío que ha habido hasta ahora:")
                        .append("\n")
                        .append(desafio);

            } else {
                msg.append("&c¡Ya ha acabado el desafío! &7Pronto anunciaremos otro reto. Si quieres ver el desafío que había hasta ahora, haz &f&l/")
                        .append(label)
                        .append(" antiguo&7.");
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.toString()));
        return true;
    }
}
