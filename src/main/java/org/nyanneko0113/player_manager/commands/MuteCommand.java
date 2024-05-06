package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.nyanneko0113.player_manager.manager.MuteManager;

import java.io.IOException;

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
        return false;
    }

}
