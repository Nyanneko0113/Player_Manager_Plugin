package org.nyanneko0113.player_manager.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryManager {

    public static Inventory openMuteList() {

        Inventory inv = Bukkit.createInventory(null, 9 * 6, ChatColor.DARK_AQUA + "ミュートしているプレイヤー");

        List<MuteManager.Mute> mute_list = MuteManager.getMuteList();

        for (int i = 0; i < mute_list.size(); i++) {
            Bukkit.broadcastMessage(i + "\n" + mute_list.size() + "\n" + mute_list);
            MuteManager.Mute mutes = mute_list.get(i);
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta item_meta = (SkullMeta) item.getItemMeta();
            item_meta.setOwner(mutes.getPlayer().getName());
            if (mutes.getType().equals(MuteManager.MuteType.NORMAL)) {
                item_meta.setLore(Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason()));
                item.setItemMeta(item_meta);
            }
            else {
                item_meta.setLore(Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason(), "期間:" + mutes.getDateString()));
                item.setItemMeta(item_meta);
            }
            inv.setItem(i, item);
        }

        ItemStack skull_right = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta right_meta = (SkullMeta) skull_right.getItemMeta();
        right_meta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_ArrowRight"));
        skull_right.setItemMeta(right_meta);
        inv.setItem(53, skull_right);

        return inv;
    }
}
