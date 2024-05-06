package org.nyanneko0113.player_manager;

import org.bukkit.plugin.java.JavaPlugin;
import org.nyanneko0113.player_manager.commands.MuteCommand;
import org.nyanneko0113.player_manager.listener.PlayerChatListener;

public class PlayerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("normal_mute").setExecutor(new MuteCommand());

        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static PlayerManager getInstance() {
        return getPlugin(PlayerManager.class);
    }
}
