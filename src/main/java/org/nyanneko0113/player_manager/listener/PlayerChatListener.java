package org.nyanneko0113.player_manager.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.nyanneko0113.player_manager.manager.MuteManager;
import org.nyanneko0113.player_manager.manager.PlayerDataManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PlayerChatListener implements Listener {

    //ミュート用
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

    //チャット非表示用
    @EventHandler
    public void onChatHide(AsyncPlayerChatEvent event) throws IOException {

        for (Player players : event.getRecipients()) {
            if (!PlayerDataManager.getPlayerData(Bukkit.getPlayer(players.getName())).contains(event.getPlayer())) {
                players.sendMessage( "<" + event.getPlayer().getName() + ">: " + event.getMessage());
            }
        }

        event.setCancelled(true);
    }
}
