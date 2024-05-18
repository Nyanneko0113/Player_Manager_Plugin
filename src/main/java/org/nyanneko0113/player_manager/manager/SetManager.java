package org.nyanneko0113.player_manager.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class SetManager {

    private static final Map<Player, Boolean> cmd_log_map = new HashMap<>();

    public static void setCommandLog(boolean value, Player player) {
        if (player.isOp()) {
            cmd_log_map.put(player, value);
        }
        else {
            throw new IllegalArgumentException("そのプレイヤーはopを持っていないため、有効にできません。");
        }
    }

    public static Set<Player> getEnableLogPlayer() {
        return cmd_log_map.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static boolean isLogEnable(Player player) {
        if (cmd_log_map.containsKey(player)) {
            return cmd_log_map.get(player);
        }

        return false;
    }
}
