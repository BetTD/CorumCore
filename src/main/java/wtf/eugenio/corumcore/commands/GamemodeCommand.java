package wtf.eugenio.corumcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    // Eug, sé que el código es una mierda, no me mates, no me he esforzado a propósito.
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("corum.gamemode")) {
            sender.sendMessage("§cNo puedes ejecutar este comando.");
            return false;
        }

        GameMode newgm = null;
        Player p;

        switch (label) {
            case "gms":
                newgm = GameMode.SURVIVAL;
                if (args.length > 0) {
                    p = Bukkit.getPlayer(args[0]);
                    setGamemode(p, newgm);
                }
                else if (sender instanceof Player) {
                    p = ((Player) sender).getPlayer();
                    setGamemode(p, newgm);
                }
                else sender.sendMessage("§cDebes especificar un jugador.");
                break;
            case "gmc":
                newgm = GameMode.CREATIVE;
                if (args.length > 0) {
                    p = Bukkit.getPlayer(args[0]);
                    setGamemode(p, newgm);
                }
                else if (sender instanceof Player) {
                    p = ((Player) sender).getPlayer();
                    setGamemode(p, newgm);
                }
                else sender.sendMessage("§cDebes especificar un jugador.");
                break;
            case "gma":
                newgm = GameMode.ADVENTURE;
                if (args.length > 0) {
                    p = Bukkit.getPlayer(args[0]);
                    setGamemode(p, newgm);
                }
                else if (sender instanceof Player) {
                    p = ((Player) sender).getPlayer();
                    setGamemode(p, newgm);
                }
                else sender.sendMessage("§cDebes especificar un jugador.");
                break;
            case "gmsp":
                newgm = GameMode.SPECTATOR;
                if (args.length > 0) {
                    p = Bukkit.getPlayer(args[0]);
                    setGamemode(p, newgm);
                }
                else if (sender instanceof Player) {
                    p = ((Player) sender).getPlayer();
                    setGamemode(p, newgm);
                }
                else sender.sendMessage("§cDebes especificar un jugador.");
                break;
            default:
                if (args.length > 0) {
                    switch (args[0]) {
                        case "0":
                        case "s":
                        case "survival":
                            newgm = GameMode.SURVIVAL;
                            break;
                        case "1":
                        case "c":
                        case "creative":
                            newgm = GameMode.CREATIVE;
                            break;
                        case "2":
                        case "a":
                        case "adventure":
                            newgm = GameMode.ADVENTURE;
                            break;
                        case "3":
                        case "sp":
                        case "spectator":
                            newgm = GameMode.SPECTATOR;
                            break;
                    }
                    if (args.length > 1) {
                        p = Bukkit.getPlayer(args[1]);
                        setGamemode(p, newgm);
                    }
                    else if (sender instanceof Player) {
                        p = ((Player) sender).getPlayer();
                        setGamemode(p, newgm);
                    }
                    else sender.sendMessage("§cDebes especificar un jugador.");
                } else {
                    sender.sendMessage("§cUso: /" + label + " <modo> [jugador]");
                }
                break;
        }

        return true;
    }

    private static void setGamemode(Player p, GameMode gm) {
        p.setGameMode(gm);
    }
}
