package org.nyanneko0113.player_manager.manager;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.PlayerManager;

import java.io.*;
import java.util.Date;

public class MuteManager {

    public static void normalMute(OfflinePlayer player, String reason) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new JsonObject();
            json.addProperty("name", player.getName());
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("reason", reason);
            json.addProperty("type", MuteType.NORMAL.name());

            JsonObject players = new Gson().fromJson(reader, JsonObject.class);
            players.get("players").getAsJsonArray().add(json);

            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                write.write(players.toString());
            }
        }
    }

    public static Mute getMute(Player player) throws IOException{
        if (getFile().exists()) {

            StringBuilder file_read = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    file_read.append(line);
                }
            }

            JsonObject json = new JsonParser().parse(file_read.toString()).getAsJsonObject();

            JsonArray players = json.getAsJsonArray("players");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                if (name.equalsIgnoreCase(player.getName())) {
                    String type = mute.get("type").getAsString();
                    String reason = mute.get("reason").getAsString();

                    if (type.equalsIgnoreCase("NORMAL")) {
                        return new Mute(name, reason);
                    }
                }

            }

        }
        else {
            throw new NullPointerException();
        }
        return null;
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
        return new File(  path.getPath() + "/mute_players.json");
    }

    public static class Mute {
        private final String name;
        private final String reason;
        private final MuteType type;
        private final Long date;

        public Mute(String name, String reason) {
            this.name = name;
            this.reason = reason;
            this.type = MuteType.NORMAL;
            this.date = null;
        }

        public Mute(String name, String reason, long date) {
            this.name = name;
            this.reason = reason;
            this.type = MuteType.NORMAL;
            this.date = date;
        }

        public Player getPlayer() {
            return Bukkit.getPlayer(name);
        }

        public String getReason() {
            return reason;
        }

        public MuteType getType() {
            return type;
        }

        public Date getDate() {
            if (type.equals(MuteType.TEMP)) {
                return new Date(date);
            }
            else {
                throw new UnsupportedOperationException("NormalBanのため取得できません。");
            }

        }

    }

    public enum MuteType {
        NORMAL,
        TEMP
    }

}
