package org.nyanneko0113.player_manager.listener;

import org.bukkit.BanEntry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.nyanneko0113.player_manager.manager.BanManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        PlayerLoginEvent.Result result = event.getResult();
        Player player = event.getPlayer();
        BanEntry ban_player = BanManager.getBan(player);

        if (result.equals(PlayerLoginEvent.Result.KICK_BANNED)) {
            if (BanManager.isBan(player)) {
                if (ban_player.getExpiration() == null) {
                    event.setKickMessage("あなたはBanされています。" + "\n" +
                            "理由: " + ban_player.getReason());
                }
                else {
                    event.setKickMessage("あなたはBanされています。" + "\n" +
                            "理由: " + ban_player.getReason() + "\n" +
                            "Banが解除日:" + getDateString(ban_player.getExpiration()));
                }

            }
        }
    }

    private String getDateString(Date date) {
        LocalDateTime datetime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return datetime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));
    }

}
