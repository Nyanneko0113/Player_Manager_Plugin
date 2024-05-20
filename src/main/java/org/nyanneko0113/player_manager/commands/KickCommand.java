package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kick")) {
            Bukkit.getPlayer(args[0]).kickPlayer(args[1]);
            send.sendMessage(TextUtil.TEXT_INFO + args[1] + "キックしました。");
        }
        else if (cmd.getName().equalsIgnoreCase("all_kick")) {
            Set<? extends Player> players = Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(player -> !player.isOp())
                    .collect(Collectors.toSet());

            for (Player player : players) {
                player.kickPlayer(args[1]);
                send.sendMessage( TextUtil.TEXT_INFO + "OP以外をキックしました。");
            }
        }
        return false;
    }

}