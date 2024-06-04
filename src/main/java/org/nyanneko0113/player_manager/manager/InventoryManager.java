package org.nyanneko0113.player_manager.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.nyanneko0113.player_manager.util.InventoryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryManager {

    public static final InventoryUtil inv_page = new InventoryUtil();

    public static Inventory openMuteList() {
        int max_slot = 8;
        List<MuteManager.Mute> mute_list = MuteManager.getMuteList();

        if (mute_list.size() <= max_slot) {
            Inventory inv = Bukkit.createInventory(null, 9 * 6, ChatColor.DARK_AQUA + "ミュートしているプレイヤー");

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
        else {
            int page_count = (int) Math.ceil((double) mute_list.size() / max_slot);

            for (int x = 1; x <= page_count; x++) {
                Inventory inv = Bukkit.createInventory(null, 9 * 6, ChatColor.DARK_AQUA + "ミュートしているプレイヤー(" + x + "ページ目)");
                inv_page.addInventory(inv);
            }

            int n = 0;
            int page = 1;
            for (int i = 0; i < mute_list.size(); i++) {
                if (n > max_slot) {
                    n=0;
                    page++;
                }

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
                inv_page.getInventory(page).setItem(n, item);

                n++;
            }

            ItemStack skull_right = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta right_meta = (SkullMeta) skull_right.getItemMeta();
            right_meta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_ArrowRight"));
            skull_right.setItemMeta(right_meta);
            inv_page.setAllInventory(53, skull_right);

            return inv_page.getInventory(1);
        }
    }

}
