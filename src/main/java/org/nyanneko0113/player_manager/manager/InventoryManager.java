package org.nyanneko0113.player_manager.manager;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.nyanneko0113.player_manager.util.InventoryUtil;
import org.nyanneko0113.player_manager.util.ItemUtil;

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

                ItemUtil item;
                String name = mutes.getPlayer().getName();
                if (mutes.getType().equals(MuteManager.MuteType.NORMAL)) {
                    List<String> lore = Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason());
                    item = new ItemUtil(mutes.getPlayer(), name, lore);
                }
                else {
                    List<String> lore = Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason(), "期間:" + mutes.getDateString());
                    item = new ItemUtil(mutes.getPlayer(), name, lore);
                }
                inv.setItem(i, item.toItemStack());
            }

            ItemStack skull_right = new ItemUtil("MHF_ArrowRight", "次のページへ進む", null).toItemStack();
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

                ItemUtil item;
                String name = mutes.getPlayer().getName();
                if (mutes.getType().equals(MuteManager.MuteType.NORMAL)) {
                    List<String> lore = Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason());
                    item = new ItemUtil(mutes.getPlayer(), name, lore);
                }
                else {
                    List<String> lore = Arrays.asList("Banの種類:" + mutes.getType().name(), "理由:" + mutes.getReason(), "期間:" + mutes.getDateString());
                    item = new ItemUtil(mutes.getPlayer(), name, lore);
                }
                inv_page.getInventory(page).setItem(n, item.toItemStack());

                n++;
            }

            ItemStack skull_right = new ItemUtil("MHF_ArrowRight", "次のページへ進む", null).toItemStack();
            inv_page.setAllInventory(53, skull_right);

            return inv_page.getInventory(1);
        }
    }

    public static Inventory openMute(OfflinePlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, ChatColor.DARK_AQUA + player.getName() + "のミュート解除しますか？");

        //緑羊毛
        Wool green = new Wool(DyeColor.GREEN);
        ItemUtil item_green = new ItemUtil(green, "解除する", null);

        //赤羊毛
        Wool red = new Wool(DyeColor.RED);
        ItemUtil item_red = new ItemUtil(red, "解除しない", null);

        inv.setItem(11, item_green.toItemStack());
        inv.setItem(14, item_red.toItemStack());

        return inv;
    }

    public static Inventory openPlayerInventory(Player player) {
        InventoryUtil inv = new InventoryUtil(Bukkit.createInventory(null, 9 * 6, player.getName() + "のインベントリ"));
        PlayerInventory player_inv = player.getInventory();
        int a = 0;
        for (int n = 36; n < 44; n++) {
            inv.setItem(0, n, player_inv.getItem(a));
            a++;
        }
        int b = 0;
        for (int n = 9; n < 35; n++) {
            inv.setItem(0, b, player_inv.getItem(n));
            b++;
        }
        inv.replaceItem(0, new ItemUtil(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), " ", null).toItemStack(), 27, 36);
        inv.setItem(0, 53, new ItemUtil(Material.BARRIER, "閉じる", null).toItemStack());

        return inv.getInventory(0);
    }

}
