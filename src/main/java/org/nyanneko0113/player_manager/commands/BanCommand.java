package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.BanManager;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.awt.*;
import java.util.Date;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("normal_ban")) {
            if (args.length == 0) {
                send.sendMessage(TextUtil.TEXT_ERROR + "プレイヤーを指定してください。");
            }
            else {
                Player player = Bukkit.getPlayer(args[0]);
                if (args.length == 1) BanManager.normalBan(player, "None.");
                BanManager.normalBan(player, args[1]);
                send.sendMessage(TextUtil.TEXT_INFO + player.getName() + "をBanしました。");
            }
        }
        else if (cmd.getName().equalsIgnoreCase("temp_ban")) {
            if (args.length == 0) {
                send.sendMessage(TextUtil.TEXT_ERROR + "プレイヤーを指定してください。");
            }
            else {
                Player player = Bukkit.getPlayer(args[0]);
                Date date = new Date(System.currentTimeMillis() + Long.parseLong(args[2]) * Long.parseLong(args[3]));
                BanManager.tempBan(player, args[1], date);
                send.sendMessage(TextUtil.TEXT_INFO + player.getName() + "をTempBanしました。");
            }
        }
        return false;
    }

}
