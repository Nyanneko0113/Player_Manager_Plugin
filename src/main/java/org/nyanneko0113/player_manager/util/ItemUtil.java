package org.nyanneko0113.player_manager.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    private final ItemStack item;

    //ItemStack用
    public ItemUtil(ItemStack item, String name, List<String> lore) {
        this.item = item;

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore == null ? new ArrayList<>() : lore);
        item.setItemMeta(meta);
    }

    //エンチャント
    public ItemUtil(Material item, String name, List<String> lore, Enchantment enc, int level) {
        this.item = new ItemStack(item);

        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore == null ? new ArrayList<>() : lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        this.item.addUnsafeEnchantment(enc, level);
        this.item.setItemMeta(meta);
    }

    //羊毛用
    public ItemUtil(Wool wool, String name, List<String> lore) {
        this.item = wool.toItemStack(1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore == null ? new ArrayList<>() : lore);

        item.setItemMeta(meta);
    }

    //Material用
    public ItemUtil(Material item, String name, List<String> lore) {
        this.item = new ItemStack(item);

        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore == null ? new ArrayList<>() : lore);

        this.item.setItemMeta(meta);
    }

    //プレイヤーの頭用
    public ItemUtil(OfflinePlayer player, String name, List<String> lore) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    //プレイヤーの頭用
    public ItemUtil(String player, String name, List<String> lore) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player);
        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    //ItemStackへの出力
    public ItemStack toItemStack() {
        return item;
    }

    public Material getMaterial() {
        return item.getType();
    }
}
