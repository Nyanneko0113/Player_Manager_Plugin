package org.nyanneko0113.player_manager.manager;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nyanneko0113.player_manager.PlayerManager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public static void tempMute(OfflinePlayer player, String reason, Date date) throws IOException {
        createJson();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            JsonObject json = new JsonObject();
            json.addProperty("name", player.getName());
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("reason", reason);
            json.addProperty("type", MuteType.TEMP.name());
            json.addProperty("date", date.getTime());

            JsonObject players = new Gson().fromJson(reader, JsonObject.class);
            players.get("players").getAsJsonArray().add(json);

            try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                write.write(players.toString());
            }
        }
    }

    public static void removeMute(Player player) throws IOException {
        Bukkit.broadcastMessage("removeMute4");
        if (getFile().exists()) {

            JsonObject json = getJson();

            JsonArray players = json.getAsJsonArray("players");
            Bukkit.broadcastMessage("removeMute3");
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                Bukkit.broadcastMessage("removeMute2");
                if (name.equalsIgnoreCase(player.getName())) {
                    players.remove(n);

                    try (BufferedWriter write = new BufferedWriter(new FileWriter(getFile()))) {
                        write.write(json.toString());
                        Bukkit.broadcastMessage("removeMute1" + "\n" +
                                json);
                        break;
                    }
                }

            }
        }
    }

    public static Mute getMute(Player player) throws IOException{
        if (getFile().exists()) {

            JsonObject json = getJson();

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
                    else if (type.equalsIgnoreCase("TEMP")) {
                        long date = mute.get("date").getAsLong();
                        return new Mute(name, reason, date);
                    }
                }

            }

        }
        else {
            throw new NullPointerException();
        }
        return null;
    }

    public static List<Mute> getMuteList() {
        if (getFile().exists()) {
            JsonObject json = getJson();

            JsonArray players = json.getAsJsonArray("players");
            List<Mute> mute_list = new ArrayList<>();
            for (int n = 0; n < players.size(); n++) {
                JsonObject mute = players.get(n).getAsJsonObject();

                String name = mute.get("name").getAsString();
                String type = mute.get("type").getAsString();
                String reason = mute.get("reason").getAsString();
                long date = mute.get("date").getAsLong();

                if (type.equalsIgnoreCase("NORMAL")) {
                    mute_list.add(new Mute(name, reason));
                }
                else if (type.equalsIgnoreCase("TEMP")) {
                    mute_list.add(new Mute(name, reason, date));
                }

            }
            return mute_list;
        }
        else {
            throw new NullPointerException();
        }
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

    public static void taskRun() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    try {
                        Mute mute = getMute(players);

                        if (mute != null) {
                            if (mute.getType().equals(MuteType.TEMP)) {
                                Bukkit.broadcastMessage(System.currentTimeMillis() + ":" + mute.getDate().getTime() + "\n" +
                                        Boolean.parseBoolean(String.valueOf(System.currentTimeMillis() > mute.getDate().getTime())));
                                if (System.currentTimeMillis() > mute.getDate().getTime()) {
                                    mute.remove();
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.runTaskTimer(PlayerManager.getInstance(), 0L, 20L);

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

        public Mute(String name, String reason, Long date) {
            this.name = name;
            this.reason = reason;
            this.type = MuteType.TEMP;
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

        public String getDateString() {
            LocalDateTime datetime = LocalDateTime.ofInstant(getDate().toInstant(), ZoneId.systemDefault());
            return datetime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));
        }

        public void remove() throws IOException {
            MuteManager.removeMute(getPlayer());
        }

        public String toString() {
            if (getType().equals(MuteType.NORMAL)) {
                return "{name:" + name + ",uuid:" + getPlayer().getUniqueId().toString() + ",type:" + getType() + "}";
            }
            else if (getType().equals(MuteType.TEMP)) {
                return "{name:" + name + ",uuid:" + getPlayer().getUniqueId().toString() + ",type:" + getType() + "date: " + getDate() + ",date_string" + getDateString() + "}";
            }

            return null;
        }

    }

    public enum MuteType {
        NORMAL,
        TEMP
    }

}
