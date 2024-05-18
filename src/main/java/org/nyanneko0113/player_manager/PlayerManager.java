package org.nyanneko0113.player_manager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.nyanneko0113.player_manager.commands.MuteCommand;
import org.nyanneko0113.player_manager.commands.PlayerInfoCommand;
import org.nyanneko0113.player_manager.commands.SetCommand;
import org.nyanneko0113.player_manager.listener.PlayerChatListener;
import org.nyanneko0113.player_manager.listener.PlayerCommandListener;
import org.nyanneko0113.player_manager.manager.MuteManager;

public class PlayerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager plm = getServer().getPluginManager();

        MuteManager.taskRun();

        getCommand("normal_mute").setExecutor(new MuteCommand());
        getCommand("temp_mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new MuteCommand());
        getCommand("mute_list").setExecutor(new MuteCommand());
        getCommand("playerinfo").setExecutor(new PlayerInfoCommand());
        getCommand("command_log").setExecutor(new SetCommand());

        plm.registerEvents(new PlayerChatListener(), this);
        plm.registerEvents(new PlayerCommandListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static PlayerManager getInstance() {
        return getPlugin(PlayerManager.class);
    }
}
