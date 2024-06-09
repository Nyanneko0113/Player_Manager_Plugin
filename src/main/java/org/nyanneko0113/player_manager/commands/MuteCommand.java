package org.nyanneko0113.player_manager.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Skull;
import org.nyanneko0113.player_manager.manager.InventoryManager;
import org.nyanneko0113.player_manager.manager.MuteManager;
import org.nyanneko0113.player_manager.util.TextUtil;
import org.nyanneko0113.player_manager.util.TimeUtil;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("normal_mute")) {
            try {
                if (args.length == 0) {
                    send.sendMessage(TextUtil.TEXT_ERROR + "ミュートするプレイヤーを指定してください。");
                }
                else if (args.length <= 2){
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

                    if (args.length == 1) MuteManager.normalMute(player, "None.");
                    MuteManager.normalMute(player, args[1]);

                    send.sendMessage( TextUtil.TEXT_INFO + player.getName() + "をミュートしました。");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (cmd.getName().equalsIgnoreCase("temp_mute")) {

            try {
                if (args.length == 0) {
                    send.sendMessage(TextUtil.TEXT_ERROR + "ミュートするプレイヤーを指定してください。");
                }
                else if (args.length >= 3) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

                    Date date = TimeUtil.toDate(args);
                    MuteManager.tempMute(player, args[1], date);
                    MuteManager.Mute mute_player = MuteManager.getMute(Bukkit.getPlayer(args[0]));
                    send.sendMessage( TextUtil.TEXT_INFO + "ミュートしました。(期間:" + mute_player.getDateString() + ")");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (cmd.getName().equalsIgnoreCase("mute_list")) {
            Player player = (Player) send;
            player.openInventory(InventoryManager.openMuteList());
        }
        else if (cmd.getName().equalsIgnoreCase("unmute")) {
            try {
                MuteManager.removeMute(Bukkit.getOfflinePlayer(args[0]));
                send.sendMessage(args[0] + "のミュートを解除しました。");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
