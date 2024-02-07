package org.yuezhikong.plugins;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.yuezhikong.plugins.command.Bind;
import org.yuezhikong.plugins.event.onDestroyChest;
import org.yuezhikong.plugins.event.onLeftCheck;
import org.yuezhikong.plugins.task.Refresh;

import java.io.File;

public class CustomLootable extends JavaPlugin {
    private static FileConfiguration config = null;
    private static FileConfiguration lootable = null;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.saveResource("lootable.yml", false);
        config = getConfig();
        File file = new File(getDataFolder(), "lootable.yml");
        lootable = YamlConfiguration.loadConfiguration(file);
        this.getCommand("clbind").setExecutor(new Bind());
        getServer().getPluginManager().registerEvents(new onLeftCheck(), this);
        getServer().getPluginManager().registerEvents(new onDestroyChest(), this);
        getServer().getPluginManager().registerEvents(new Refresh(), this);
        getLogger().info("[自定义战利品]加载成功");
    }

    @Override
    public void onDisable() {
        PlayerInteractEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        ServerLoadEvent.getHandlerList().unregister(this);
        getLogger().info("[自定义战利品]卸载成功");
    }
    public static FileConfiguration getServerconfig() {
        return config;
    }
    public static FileConfiguration getLootable() {
        return lootable;
    }
}
