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

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        HumanEntity player = event.getWhoClicked();

        if (inv.getTitle().contains(ChatColor.DARK_AQUA + "ミュートしているプレイヤー")) {
            event.setCancelled(true);

            if (event.getSlot() == 53) {
                String page = inv.getTitle().replace(ChatColor.DARK_AQUA + "ミュートしているプレイヤー(", "").replace("ページ目)", "");

                player.openInventory(InventoryManager.inv_page.getInventory(Integer.parseInt(page) + 1));
            }
        }

    }
}
