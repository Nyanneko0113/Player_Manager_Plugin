package org.nyanneko0113.player_manager.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.nyanneko0113.player_manager.manager.SetManager;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (SetManager.isLogEnable(player)) {
            for (Player cmd_log_players : SetManager.getEnableLogPlayer()) {
                cmd_log_players.sendMessage(ChatColor.GRAY + "[CmdLog]" + player.getName() + ":" + event.getMessage());
            }
        }
    }
}
