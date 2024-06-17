package org.nyanneko0113.player_manager.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;
import org.nyanneko0113.player_manager.commands.MuteCommand;
import org.nyanneko0113.player_manager.manager.InventoryManager;
import org.nyanneko0113.player_manager.manager.MuteManager;

import java.io.IOException;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) throws IOException {
        Inventory inv = event.getInventory();
        HumanEntity player = event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getCurrentItem() != null) {

            //ミュートリストGUI
            if (inv.getTitle().contains(ChatColor.DARK_AQUA + "ミュートしているプレイヤー")) {
                event.setCancelled(true);

                String page = inv.getTitle().replace(ChatColor.DARK_AQUA + "ミュートしているプレイヤー(", "").replace("ページ目)", "");
                if (event.getSlot() == 53 && item.getType().equals(Material.SKULL_ITEM)) {
                    player.openInventory(InventoryManager.mute_invs.getInventory(Integer.parseInt(page) + 1));
                }
                else {
                    SkullMeta meta = (SkullMeta) event.getCurrentItem().getItemMeta();
                    player.openInventory(InventoryManager.openMute(meta.getOwningPlayer()));
                }
            }

            //ミュート解除GUI
            else if (inv.getTitle().contains("のミュート解除しますか？")) {
                event.setCancelled(true);

                if (event.getSlot() == 11) {
                    String player_name = inv.getTitle().replace("のミュート解除しますか？", "");

                    MuteManager.removeMute(Bukkit.getOfflinePlayer(player_name));
                    player.sendMessage("ミュートを解除しました。");
                }
            }

            //プレイヤーインベントリ
            else if (inv.getTitle().contains("のインベントリ")) {
                event.setCancelled(true);

                if (event.getSlot() == 8) {
                    player.closeInventory();
                }
            }

        }
    }
}
