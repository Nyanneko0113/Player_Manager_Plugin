package org.nyanneko0113.player_manager.manager;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public class BanManager {

    private static final BanList ban_list_name = Bukkit.getBanList(BanList.Type.NAME);

    public static void normalBan(OfflinePlayer player, String reason) {
        BanEntry entry = ban_list_name.addBan(player.getName(), reason, null, null);
        entry.save();
        if (player.isOnline()) player.getPlayer().kickPlayer("あなたはBanされました。");
    }

    public static void tempBan(OfflinePlayer player, String reason, Date date) {
        BanEntry entry = ban_list_name.addBan(player.getName(), reason, date, null);
        entry.save();
        if (player.isOnline()) player.getPlayer().kickPlayer("あなたはBanされました。");
    }

    public static BanEntry getBan(OfflinePlayer player) {
        return ban_list_name.getBanEntry(player.getName());
    }

    public static boolean isBan(OfflinePlayer player) {
        return ban_list_name.isBanned(player.getName());
    }
}
