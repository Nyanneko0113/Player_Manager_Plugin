package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.BanManager;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

                LocalDateTime dates = LocalDateTime.now();
                int year = dates.getYear(),
                        month = dates.getMonth().getValue(),
                        day = dates.getDayOfMonth(),
                        hour = dates.getHour(),
                        min = dates.getMinute();

                if (args.length >= 8) year = Integer.parseInt(args[7]);
                if (args.length >= 7) month = Integer.parseInt(args[6]);
                if (args.length >= 6) day = Integer.parseInt(args[5]);
                if (args.length >= 5) hour = Integer.parseInt(args[4]);
                if (args.length >= 4) min = Integer.parseInt(args[3]);
                int sec = Integer.parseInt(args[2]);

                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime local_date = LocalDateTime.of(year, month, day, hour, min, sec);
                ZonedDateTime zonedDateTime = ZonedDateTime.of(local_date, zone);
                Instant instant = zonedDateTime.toInstant();

                Date date = Date.from(instant);
                BanManager.tempBan(player, args[1], date);
                send.sendMessage(TextUtil.TEXT_INFO + player.getName() + "をTempBanしました。");
            }
        }
        return false;
    }

}
