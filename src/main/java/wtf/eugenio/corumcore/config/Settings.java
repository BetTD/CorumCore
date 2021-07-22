package wtf.eugenio.corumcore.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import wtf.eugenio.corumcore.CorumCore;

public class Settings {
    private final FileConfiguration config = CorumCore.getInstance().getConfig();

    public String joinMessage;
    public int motdLines;
    public String joinMotd;
    // *------*
    public String leaveMessage;
    // *------*
    public String challenge;
    // *------*
    public String webhookURL;

    public void initializeMessages() {
        joinMessage = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.join-message"));
        motdLines = CorumCore.getInstance().getConfig().getInt("joinleave.join-motd-newlines");
        joinMotd = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.join-motd-message"));
        // *------*
        leaveMessage = ChatColor.translateAlternateColorCodes('&', config.getString("joinleave.leave-message"));
        // *------*
        challenge = ChatColor.translateAlternateColorCodes('&', config.getString("challenge"));
        // *------*
        webhookURL = config.getString("webhook-url");
    }
}
