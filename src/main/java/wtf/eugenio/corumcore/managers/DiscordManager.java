package wtf.eugenio.corumcore.managers;

import org.json.JSONObject;
import wtf.eugenio.corumcore.CorumCore;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordManager {
    public static void sendStartWebhook() {
        sendWebhook("**El servidor se ha iniciado.**", "#15ed56");
    }

    public static void sendStoppingWebhook() {
        sendWebhook("**El servidor se está apagando.**", "#ed281a");
    }

    private static void sendWebhook(String msg, String color) {
        URL webhooklink = null;
        try {
            webhooklink = new URL(CorumCore.getInstance().getConfig().getString("webhook-url"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert webhooklink != null;
            HttpURLConnection http = (HttpURLConnection) webhooklink.openConnection();
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("User-Agent", "CorumCore/" + CorumCore.getInstance().getDescription().getVersion());
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            JSONObject obj = new JSONObject();
            obj.put("username", "Corum");
            obj.put("avatar", "https://i.de.5sm.online/e7bbc0b5592b59e744db25547e731537.webp");

            JSONObject objEmbed = new JSONObject();
            objEmbed.put("title", msg);
            objEmbed.put("description", "");
            objEmbed.put("color", color);
            objEmbed.put("footer", new JSONObject().put("text", "corum.lobosarcraft.com"));

            obj.put("embeds", objEmbed);

            OutputStream out = http.getOutputStream();
            out.write(obj.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            http.getInputStream().close();
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
