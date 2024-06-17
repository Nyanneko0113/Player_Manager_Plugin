package org.nyanneko0113.player_manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nyanneko0113.player_manager.manager.InventoryManager;
import org.nyanneko0113.player_manager.util.TextUtil;

public class OpenInventoryCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("open_inv")) {
            if (send instanceof Player) {
                Player player = Bukkit.getPlayer(args[0]);
                Player send_player = (Player) send;
                send_player.openInventory(InventoryManager.openPlayerInventory(player));
            }
            else {
                send.sendMessage(TextUtil.TEXT_ERROR + "このコマンドはコンソールから実行できません。");
            }

        }
        return false;
    }
    
}
