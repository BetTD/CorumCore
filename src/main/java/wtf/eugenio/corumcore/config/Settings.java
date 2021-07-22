package wtf.eugenio.corumcore.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import wtf.eugenio.corumcore.CorumCore;

import java.util.List;

public class Settings {
    public String joinMessage;
    public int motdLines;
    public String joinMotd;
    // *------*
    public String leaveMessage;
    // *------*
    public List<String> challenge;
    // *------*
    public String webhookURL;

    public void initializeMessages() {
        joinMessage = ChatColor.translateAlternateColorCodes('&', CorumCore.getInstance().getConfig().getString("joinleave.join-message"));
        motdLines = CorumCore.getInstance().getConfig().getInt("joinleave.join-motd-newlines");
        joinMotd = ChatColor.translateAlternateColorCodes('&', CorumCore.getInstance().getConfig().getString("joinleave.join-motd-message"));
        // *------*
        leaveMessage = ChatColor.translateAlternateColorCodes('&', CorumCore.getInstance().getConfig().getString("joinleave.leave-message"));
        // *------*
        challenge = CorumCore.getInstance().getConfig().getStringList("challenge");
        // *------*
        webhookURL = CorumCore.getInstance().getConfig().getString("webhook-url");
    }
}
