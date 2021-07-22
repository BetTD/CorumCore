package wtf.eugenio.corumcore.managers;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
// TODO Actualizar a la nueva librería JSON incluída en el pom.xml
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import wtf.eugenio.corumcore.CorumCore;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"unchecked"})
public class VidasManager {
    public static File lifes;

    public static HashMap<String, Integer> health = new HashMap<>();
    public static List<String> undoneChallenges = new ArrayList<>();

    public static void init() {
        lifes = new File("./lifes.json");

        if (!lifes.exists()) {
            try {
                lifes.createNewFile();
            } catch (IOException exception) {
                throw new RuntimeException("Error creando el archivo lifes.json. Excepción: " + exception);
            }

            JSONObject object = new JSONObject();

            JSONArray listParticipants = new JSONArray();
            listParticipants.add("zCarmen");
            listParticipants.add("NotEugenio_");

            object.put("participants", listParticipants);



            JSONArray listUndone = new JSONArray();
            listUndone.add("test");

            object.put("participants-undone-challenges", listUndone);

            JSONArray listOneheart = new JSONArray();
            listOneheart.add("test");

            object.put("participants-oneheart", listOneheart);

            JSONArray listTwoheart = new JSONArray();
            listTwoheart.add("test");

            object.put("participants-twoheart", listTwoheart);

            JSONArray listThreeheart = new JSONArray();
            listThreeheart.add("test");

            object.put("participants-threeheart", listThreeheart);

            for (Object value : listUndone) undoneChallenges.add(value.toString());

            try (FileWriter file = new FileWriter("./lifes.json")) {
                file.write(object.toJSONString());
            } catch (IOException exception) {
                throw new RuntimeException("Error al escribir al archivo lifes.json. Excepción: " + exception);
            }
        }

        putOnCache();
    }

    private static void putOnCache() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("./lifes.json")) {
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;

            JSONArray oneList = (JSONArray) jsonObject.get("participants-oneheart");
            for (Object value : oneList) health.put(value.toString(), 1);

            JSONArray twoList = (JSONArray) jsonObject.get("participants-twoheart");
            for (Object value : twoList) health.put(value.toString(), 2);

            JSONArray threeList = (JSONArray) jsonObject.get("participants-threeheart");
            for (Object value : threeList) health.put(value.toString(), 3);
        } catch (IOException | ParseException exception) {
            throw new RuntimeException("Error al leer o parsear el archivo lifes.json. Excepción: " + exception);
        }
    }

    public static void startFresh() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("./lifes.json")) {
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;

            JSONArray participantsList = (JSONArray) jsonObject.get("participants");
            jsonObject.put("participants-undone-challenges", participantsList);
            jsonObject.put("participants-threeheart", participantsList);

            for (Object value : participantsList) VidasManager.health.put(value.toString(), 3);

            Bukkit.getConsoleSender().sendMessage("starteadonew");
            try (FileWriter file = new FileWriter("./lifes.json")) {
                file.write(jsonObject.toJSONString());
            } catch (IOException exception) {
                throw new RuntimeException("Error al escribir al archivo lifes.json. Excepción: " + exception);
            }
        } catch (IOException | ParseException exception) {
            throw new RuntimeException("Error al leer o parsear el archivo lifes.json. Excepción: " + exception);
        }
    }

    public static void saveLife(String name) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("./lifes.json")) {
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;

            JSONArray undoneList = (JSONArray) jsonObject.get("participants-undone-challenges");
            undoneList.remove(name);

            jsonObject.put("participants-undone-challenges", undoneList);

            undoneChallenges.remove(name);

            Bukkit.getConsoleSender().sendMessage("Salvado a " + name);

            try (FileWriter file = new FileWriter("./lifes.json")) {
                file.write(jsonObject.toJSONString());
            } catch (IOException exception) {
                throw new RuntimeException("Error al escribir al archivo whitelist.json. Excepción: " + exception);
            }
        } catch (IOException | ParseException exception) {
            throw new RuntimeException("Error al leer o parsear el archivo whitelist.json. Excepción: " + exception);
        }
    }

    public static void endDay() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("./lifes.json")) {
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;


            JSONArray undoneList = (JSONArray) jsonObject.get("participants-undone-challenges");

            JSONArray oneList = (JSONArray) jsonObject.get("participants-oneheart");
            JSONArray twoList = (JSONArray) jsonObject.get("participants-twoheart");
            JSONArray threeList = (JSONArray) jsonObject.get("participants-threeheart");

            for (Object value : undoneList) {
                if (oneList.contains(value.toString())) {
                    Bukkit.getBanList(BanList.Type.NAME).addBan(value.toString(), "§cGracias por jugar, pero ya no tienes más vidas.", new Date(), "Consola");
                } else if (twoList.contains(value.toString())) {
                    twoList.remove(value.toString());

                    jsonObject.put("participants-twoheart", twoList);

                    oneList.add(value.toString());

                    jsonObject.put("participants-oneheart", oneList);
                } else if (threeList.contains(value.toString())) {
                    threeList.remove(value.toString());

                    jsonObject.put("participants-threeheart", threeList);

                    twoList.add(value.toString());

                    jsonObject.put("participants-twoheart", twoList);
                }
            }

            Bukkit.getScheduler().runTaskLater(CorumCore.getInstance(), () -> {
                JSONArray participantsList = (JSONArray) jsonObject.get("participants");
                jsonObject.put("participants-undone-challenges", participantsList);
            }, 5L);

            Bukkit.getScheduler().runTaskLater(CorumCore.getInstance(), () -> {
                health.clear();

                for (Object value : oneList) health.put(value.toString(), 1);
                for (Object value : twoList) health.put(value.toString(), 2);
                for (Object value : threeList) health.put(value.toString(), 3);
            }, 100L);

            Bukkit.getConsoleSender().sendMessage("Hecho");

            try (FileWriter file = new FileWriter("./lifes.json")) {
                file.write(jsonObject.toJSONString());
            } catch (IOException exception) {
                throw new RuntimeException("Error al escribir al archivo lifes.json. Excepción: " + exception);
            }
        } catch (IOException | ParseException exception) {
            throw new RuntimeException("Error al leer o parsear el archivo lifes.json. Excepción: " + exception);
        }
    }

    public static int getLifes(Player player) {
        return VidasManager.health.get(player.getName());
    }
}
