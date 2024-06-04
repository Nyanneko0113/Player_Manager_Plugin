package org.nyanneko0113.player_manager.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryUtil {

    private final List<Inventory> inv_list = new ArrayList<>();
    private int page;

    public InventoryUtil() {}

    public InventoryUtil(Inventory... invs){
        inv_list.addAll(Arrays.asList(invs));
    }

    public void addInventory(Inventory inv) {
        inv_list.add(inv);
    }

    public void removeInventory(Inventory inv) {
        inv_list.remove(inv);
    }

    public void addItem(int page, ItemStack item) {
        if (page >= 1) {
            if (!inv_list.isEmpty()) {
                Inventory inv = inv_list.get(page - 1);
                inv.addItem(item);
            }
            else {
                throw new NullPointerException("インベントリが存在しません。");
            }
        }
        else {
            throw new IllegalArgumentException("数字が範囲外です。");
        }
    }

    public void addAllInventory(ItemStack item) {
        for (Inventory inv : inv_list) {
            inv.addItem(item);
        }
    }

    public void setAllInventory(int i, ItemStack item) {
        for (Inventory inv : inv_list) {
            inv.setItem(i, item);
        }
    }

    public void removeItem(int page, ItemStack item) {
        if (page >= 1) {
            if (!inv_list.isEmpty()) {
                Inventory inv = inv_list.get(page - 1);
                inv.removeItem(item);
            }
            else {
                throw new NullPointerException("インベントリが存在しません。");
            }
        }
        else {
            throw new IllegalArgumentException("数字が範囲外です。");
        }
    }

    public Inventory getInventory(int page) {
        if (page >= 1) {
            return inv_list.get(page - 1);
        }
        else {
            throw new IllegalArgumentException("数字が範囲外です。");
        }
    }

}
