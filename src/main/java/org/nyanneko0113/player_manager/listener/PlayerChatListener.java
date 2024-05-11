package org.nyanneko0113.player_manager.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.nyanneko0113.player_manager.manager.MuteManager;

import java.io.IOException;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws IOException {
        Player player = event.getPlayer();
        MuteManager.Mute mute = MuteManager.getMute(player);
        if (mute != null) {
            event.setCancelled(true);
            MuteManager.MuteType type = mute.getType();
            Bukkit.broadcastMessage(mute.toString());
            if (type.equals(MuteManager.MuteType.NORMAL)) {
                player.sendMessage("あなたはミュートされています。");
            }
            else if (type.equals(MuteManager.MuteType.TEMP)) {
                player.sendMessage("あなたはミュートされています。解除時間:" + mute.getDateString());
            }
        }
    }
}
