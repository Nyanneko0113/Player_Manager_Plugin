package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.player_manager.manager.BanManager;

import java.util.Date;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("normal_ban")) {
            BanManager.normalBan(Bukkit.getPlayer(args[0]), args[1]);
            send.sendMessage(args[0] + "をBanしました。");
        }
        else if (cmd.getName().equalsIgnoreCase("temp_ban")) {
            BanManager.tempBan(Bukkit.getPlayer(args[0]), args[1], new Date(System.currentTimeMillis() + Long.parseLong(args[2]) * Long.parseLong(args[3])));
            send.sendMessage(args[0] + "をTempBanしました。");
        }
        return false;
    }

}
