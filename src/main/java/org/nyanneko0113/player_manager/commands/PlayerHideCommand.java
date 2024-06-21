package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.PlayerDataManager;

import java.io.IOException;

public class PlayerHideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hide")) {
            try {
                PlayerDataManager.addHidePlayer((Player) send, Bukkit.getPlayer(args[0]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
