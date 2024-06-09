package org.nyanneko0113.player_manager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.nyanneko0113.player_manager.commands.*;
import org.nyanneko0113.player_manager.listener.InventoryClickListener;
import org.nyanneko0113.player_manager.listener.PlayerChatListener;
import org.nyanneko0113.player_manager.listener.PlayerCommandListener;
import org.nyanneko0113.player_manager.listener.PlayerLoginListener;
import org.nyanneko0113.player_manager.manager.MuteManager;

public class PlayerManager extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager plm = getServer().getPluginManager();

        MuteManager.taskRun();

        //mute
        getCommand("normal_mute").setExecutor(new MuteCommand());
        getCommand("temp_mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new MuteCommand());
        getCommand("mute_list").setExecutor(new MuteCommand());

        //ban
        getCommand("normal_ban").setExecutor(new BanCommand());
        getCommand("temp_ban").setExecutor(new BanCommand());

        //kick
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("all_kick").setExecutor(new KickCommand());

        //info
        getCommand("playerinfo").setExecutor(new PlayerInfoCommand());

        //set
        getCommand("command_log").setExecutor(new SetCommand());

        //player
        getCommand("open_inv").setExecutor(new OpenInventoryCommand());

        //listener
        plm.registerEvents(new PlayerChatListener(), this);
        plm.registerEvents(new PlayerCommandListener(), this);
        plm.registerEvents(new InventoryClickListener(), this);
        plm.registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static PlayerManager getInstance() {
        return getPlugin(PlayerManager.class);
    }
}
