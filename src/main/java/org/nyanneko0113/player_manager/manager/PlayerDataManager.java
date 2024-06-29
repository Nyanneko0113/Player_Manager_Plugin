package org.nyanneko0113.player_manager.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.PlayerManager;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerDataManager {

    public static void addHidePlayer(Player player, Player... hide_player) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new JsonObject();
            json.addProperty("name", player.getName());
            json.addProperty("uuid", player.getUniqueId().toString());
            JsonArray array = new JsonArray();
            for (Player players : hide_player) {
                array.add(players.getName());
            }
            json.add("hide_player", array);

            JsonObject players = new Gson().fromJson(reader, JsonObject.class);
            players.get("players").getAsJsonArray().add(json);

            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                write.write(players.toString());
            }
        }
    }

    public static void addHideWord(Player player, String... word) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new JsonObject();
            json.addProperty("name", player.getName());
            json.addProperty("uuid", player.getUniqueId().toString());
            JsonArray array = new JsonArray();
            for (String words : word) {
                array.add(words);
            }
            json.add("hide_word", array);

            JsonObject players = new Gson().fromJson(reader, JsonObject.class);
            players.get("players").getAsJsonArray().add(json);

            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                write.write(players.toString());
            }
        }
    }

    public static Set<OfflinePlayer> getPlayerData(OfflinePlayer player) throws IOException{
        if (getFile().exists()) {

            JsonObject json = getJson();

            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    JsonArray array = mute.getAsJsonArray("hide_player");
                    Set<OfflinePlayer> list = new HashSet<>();
                    for (int a = 0; a < array.size(); a++) {
                        if (!StringUtils.isBlank(array.get(a).getAsString())) {
                            list.add(Bukkit.getOfflinePlayer(array.get(a).getAsString()));
                        }

                    }
                    return list;
                }

            }

        }
        else {
            return Collections.emptySet();
        }
        return Collections.emptySet();
    }

    public static Set<String> getPlayerHideWord(OfflinePlayer player) throws IOException{
        if (getFile().exists()) {

            JsonObject json = getJson();

            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    JsonArray array = mute.getAsJsonArray("hide_word");
                    Set<String> list = new HashSet<>();
                    for (int a = 0; a < array.size(); a++) {
                        list.add(array.get(a).getAsString());
                    }
                    return list;
                }

            }

        }
        else {
            throw new NullPointerException();
        }
        return new HashSet<>();
    }

    private static JsonObject getJson() {
        if (getFile().exists()) {
            StringBuilder file_read = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    file_read.append(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new JsonParser().parse(file_read.toString()).getAsJsonObject();
        }
        else {
            throw new NullPointerException();
        }
    }

    private static void createJson() throws IOException {
        if (!getFile().exists()) {
            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                JsonObject json = new JsonObject();
                json.add("players", new JsonArray());
                write.write(json.toString());
            }
        }
    }

    private static File getFile() {
        File path = PlayerManager.getInstance().getDataFolder();
        if (!path.exists()) {
            new File(path.getPath()).mkdir();
        }
        return new File(  path.getPath() + "/player_data.json");
    }

}
