package wtf.eugenio.corumcore.managers;

import org.json.JSONObject;
import wtf.eugenio.corumcore.CorumCore;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DiscordManager {
    public static void sendStartWebhook() {
        sendWebhook("**El servidor se ha iniciado.**", 1437014);
    }

    public static void sendStoppingWebhook() {
        sendWebhook("**El servidor se está apagando.**", 15542298);
    }

    private static void sendWebhook(String msg, int color) {
        URL webhooklink = null;
        try {
            webhooklink = new URL(CorumCore.getInstance().getSettings().webhookURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String timestamp = df.format(new Date());
            assert webhooklink != null;
            HttpURLConnection http = (HttpURLConnection) webhooklink.openConnection();
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("User-Agent", "CorumCore/" + CorumCore.getInstance().getDescription().getVersion());
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            JSONObject obj = new JSONObject();
            obj.put("username", "Corum");
            obj.put("avatar", "https://i.de.5sm.online/e7bbc0b5592b59e744db25547e731537.webp");

            List<JSONObject> embeds = new ArrayList<>();
            JSONObject objEmbed = new JSONObject();
            objEmbed.put("type", "rich");
            objEmbed.put("title", "Cambio de estado");
            objEmbed.put("description", msg);
            objEmbed.put("color", color);
            objEmbed.put("timestamp", timestamp);
            objEmbed.put("footer", new JSONObject().put("text", "corum.lobosarcraft.com"));
            embeds.add(objEmbed);

            obj.put("embeds", embeds);

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
