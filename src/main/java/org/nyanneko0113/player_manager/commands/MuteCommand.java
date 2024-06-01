package org.nyanneko0113.player_manager.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.player_manager.manager.MuteManager;
import org.nyanneko0113.player_manager.util.TextUtil;

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
                    MuteManager.tempMute(player, args[1], date);
                    MuteManager.Mute mute_player = MuteManager.getMute(Bukkit.getPlayer(args[0]));
                    send.sendMessage( TextUtil.TEXT_INFO + "ミュートしました。(期間:" + mute_player.getDateString() + ")");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (cmd.getName().equalsIgnoreCase("mute_list")) {
            send.sendMessage(TextUtil.TEXT_INFO + "ミュートしているプレイヤー:" + MuteManager.getMuteList());
        }
        else if (cmd.getName().equalsIgnoreCase("unmute")) {
            try {
                MuteManager.removeMute(Bukkit.getPlayer(args[0]));
                send.sendMessage(args[0] + "のミュートを解除しました。");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
