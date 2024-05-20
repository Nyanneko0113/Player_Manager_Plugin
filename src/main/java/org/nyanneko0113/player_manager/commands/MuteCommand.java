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
import java.time.LocalDateTime;
import java.util.Date;

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
                else if (args.length <= 4) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    Date date = new Date(System.currentTimeMillis() + Long.parseLong(args[2]) * Long.parseLong(args[3]));
                    MuteManager.tempMute(player, args[1], date);
                    send.sendMessage("ミュートしました。");
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
