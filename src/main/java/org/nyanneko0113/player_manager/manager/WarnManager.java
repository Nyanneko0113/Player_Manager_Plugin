package org.nyanneko0113.player_manager.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nyanneko0113.player_manager.PlayerManager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;

public class WarnManager {

    public static void warnPlayer(OfflinePlayer player, String reason, boolean warn) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new JsonObject();
            JsonObject gson = new Gson().fromJson(reader, JsonObject.class);
            json.addProperty("id",  gson.get("players").getAsJsonArray().size() + 1);
            json.addProperty("name", player.getName());
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("reason", reason);
            json.addProperty("date", System.currentTimeMillis());
            json.addProperty("warning", warn);

            gson.get("players").getAsJsonArray().add(json);

            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                write.write(gson.toString());
            }
        }
    }

    public static boolean isWarn(OfflinePlayer player) {
        if (getFile().exists()) {

            JsonObject json = getJson();
            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    Bukkit.broadcastMessage(name + ":" + mute.get("warning").getAsBoolean());
                    return mute.get("warning").getAsBoolean();
                }
            }
        }
        else {
            throw new NullPointerException();
        }
        return false;
    }

    public static void setWarn(OfflinePlayer player, boolean warn) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);

            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    Bukkit.broadcastMessage(name + ":" + mute.get("warning").getAsBoolean());
                    mute.addProperty("warning", warn);

                    try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                        write.write(json.toString());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getReason(OfflinePlayer player) {
        if (getFile().exists()) {

            JsonObject json = getJson();

            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    return mute.get("reason").getAsString();
                }
            }
        }
        else {
            throw new NullPointerException();
        }
        return null;
    }

    private static JsonObject getJson() {
        if (getFile().exists()) {
            StringBuilder file_read = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
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
        return new File(  path.getPath() + "/warn_players.json");
    }
}
