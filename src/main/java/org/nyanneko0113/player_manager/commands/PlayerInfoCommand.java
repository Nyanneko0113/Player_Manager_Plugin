package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.net.InetSocketAddress;
import java.util.Arrays;

public class PlayerInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd ,String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("playerinfo")) {
            if (args.length >= 1) {
                Player player = Bukkit.getPlayer(args[0]);
                InetSocketAddress address = player.getAddress();
                send.sendMessage(TextUtil.TEXT_INFO + "UUID:" + player.getUniqueId() + "\n" +
                        address.getAddress().getHostAddress());
            }
        }
        return false;
    }

}
