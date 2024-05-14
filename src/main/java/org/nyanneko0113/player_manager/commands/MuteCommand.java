package org.nyanneko0113.player_manager.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.player_manager.manager.MuteManager;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.io.IOException;
import java.util.Date;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("normal_mute")) {
            try {
                MuteManager.normalMute(Bukkit.getOfflinePlayer(args[0]), args[1]);
                send.sendMessage("ミュートしました。");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (cmd.getName().equalsIgnoreCase("temp_mute")) {
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(args[2]) * Long.parseLong(args[3]));
            try {
                MuteManager.tempMute(Bukkit.getOfflinePlayer(args[0]), args[1], date);
                send.sendMessage("ミュートしました。");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (cmd.getName().equalsIgnoreCase("mute_list")) {
            String list = StringUtils.join(MuteManager.getMuteList(), ",");
            send.sendMessage(TextUtil.TEXT_INFO + "ミュートしているプレイヤー:" + list);
        }
        return false;
    }

}
