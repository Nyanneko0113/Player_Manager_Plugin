package org.nyanneko0113.player_manager;

import org.bukkit.plugin.java.JavaPlugin;
import org.nyanneko0113.player_manager.commands.MuteCommand;
import org.nyanneko0113.player_manager.commands.PlayerInfoCommand;
import org.nyanneko0113.player_manager.listener.PlayerChatListener;
import org.nyanneko0113.player_manager.manager.MuteManager;

public class PlayerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        MuteManager.taskRun();

        getCommand("normal_mute").setExecutor(new MuteCommand());
        getCommand("temp_mute").setExecutor(new MuteCommand());
        getCommand("playerinfo").setExecutor(new PlayerInfoCommand());

        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static PlayerManager getInstance() {
        return getPlugin(PlayerManager.class);
    }
}
