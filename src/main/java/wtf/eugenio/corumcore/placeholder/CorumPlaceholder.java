package wtf.eugenio.corumcore.placeholder;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;
import wtf.eugenio.corumcore.CorumCore;
import wtf.eugenio.corumcore.managers.VidasManager;

public class CorumPlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return "Corum";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "corum";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        switch (params) {
            case "desafio":
                if (VidasManager.undoneChallenges.contains(player.getPlayer().getName())) {
                    if (CorumCore.getInstance().getSettings().challenge != null) return CorumCore.getInstance().getSettings().challenge;
                } else {
                    return "§a¡Completado!";
                }
                break;
            case "vidas":
                if (VidasManager.health.containsKey(player.getPlayer().getName())) VidasManager.getLifes(player.getPlayer());
                break;
        }

        return null;
    }
}
