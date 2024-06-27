package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.WarnManager;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.io.IOException;

public class WarnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("warn")) {
            try {
                Player player = Bukkit.getPlayer(args[0]);
                if (player.isOnline()) {
                    WarnManager.warnPlayer(Bukkit.getPlayer(args[0]), args[1], true);
                    player.sendMessage(TextUtil.TEXT_INFO + "あなたは警告を受けました。");
                    player.sendMessage(TextUtil.TEXT_INFO + "理由：" + WarnManager.getReason(player));
                    send.sendMessage( TextUtil.TEXT_INFO + "プレイヤーに警告しました。");
                }
                else {
                    WarnManager.warnPlayer(Bukkit.getPlayer(args[0]), args[1], false);
                    send.sendMessage(TextUtil.TEXT_INFO + "プレイヤーを警告しました。" + ChatColor.GRAY + "(プレイヤーがオフラインのため次回ログイン時に警告されます。)");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
