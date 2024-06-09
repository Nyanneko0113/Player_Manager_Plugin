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
        if (!inv_list.isEmpty()) {
            Inventory inv = inv_list.get(page);
            inv.addItem(item);
        }
        else {
            throw new NullPointerException("インベントリが存在しません。");
        }
    }

    public void setItem(int page, int slot, ItemStack item) {
        if (!inv_list.isEmpty()) {
            Inventory inv = inv_list.get(page);
            inv.setItem(slot, item);
        }
        else {
            throw new NullPointerException("インベントリが存在しません。");
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

    public void replaceItem(int page, ItemStack olditem, ItemStack newitem) {
        Inventory inv = inv_list.get(page);

        for (int n = 0; n < inv.getSize(); n++) {
            ItemStack item = inv.getItem(n);
            if (item.equals(olditem)) {
                inv.setItem(n, newitem);
            }
        }
    }

    public void replaceItem(int page, ItemStack item, int start, int end) {
        Inventory inv = inv_list.get(page);

        int a = Math.min(start, end);
        int b = Math.max(start, end);
        for (int n = a; n < b; n++) {
            inv.setItem(n, item);
        }
    }

    public void clearAllItem(){
        inv_list.forEach(Inventory::clear);
    }

    public Inventory getInventory(int page) {
        return inv_list.get(page);
    }

}
