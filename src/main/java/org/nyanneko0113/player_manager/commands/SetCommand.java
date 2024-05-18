package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.SetManager;
import org.nyanneko0113.player_manager.util.TextUtil;

import java.awt.*;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("command_log")) {
            Player player = (Player) send;
            if (player.isOp()) {
                if (!SetManager.isLogEnable(player)) {
                    SetManager.setCommandLog(true, player);
                    send.sendMessage(TextUtil.TEXT_INFO + "コマンドログを有効にしました。");
                }
                else {
                    SetManager.setCommandLog(false, player);
                    send.sendMessage(TextUtil.TEXT_INFO + "コマンドログを無効にしました。");
                }
            }
            else {
                send.sendMessage(TextUtil.TEXT_ERROR + "OPを持っていないプレイヤーのため有効にできませんでした。");
            }
        }
        return false;
    }

}
