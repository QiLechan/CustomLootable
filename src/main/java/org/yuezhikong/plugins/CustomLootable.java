package org.yuezhikong.plugins;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.yuezhikong.plugins.event.onDestroyChest;
import org.yuezhikong.plugins.event.onLeftCheck;

public class CustomLootable extends JavaPlugin {
    private static FileConfiguration config = null;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(new onLeftCheck(), this);
        getServer().getPluginManager().registerEvents(new onDestroyChest(), this);
    }

    @Override
    public void onDisable() {
        PlayerInteractEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
    }
    public static FileConfiguration getServerconfig() {
        return config;
    }
}
