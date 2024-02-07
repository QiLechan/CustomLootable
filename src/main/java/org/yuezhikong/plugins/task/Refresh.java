package org.yuezhikong.plugins.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.yuezhikong.plugins.CustomLootable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Refresh implements Listener {
    private static FileConfiguration config = CustomLootable.getServerconfig();
    private static FileConfiguration lootable = CustomLootable.getLootable();
    private static Chest chest = null;
    @EventHandler
    public void ServerLoadEvent(ServerLoadEvent event){
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> chestList = config.getStringList("ChestList");
                for (String UUID : chestList){
                    int X = config.getInt("Chest." + UUID + ".X");
                    int Y = config.getInt("Chest." + UUID + ".Y");
                    int Z = config.getInt("Chest." + UUID + ".Z");
                    java.util.UUID wuid = java.util.UUID.fromString(Objects.requireNonNull(config.getString("Chest." + UUID + ".World")));
                    World world = Bukkit.getWorld(wuid);
                    Location location = new Location(world, X, Y, Z);
                    Block block = location.getBlock();
                    if (block.getType() == Material.CHEST) {
                        chest = (Chest) block;
                    }
                    String BindID = config.getString("Chest." + UUID + ".BindID");
                    int Max = lootable.getInt("Loots." + BindID + ".Max");
                    int Min = lootable.getInt("Loots." + BindID + ".Min");
                    List<String> items = lootable.getStringList("Loots." + BindID + ".items");
                    for (String item : items){
                        int amount = (int) Math.floor(Math.random() * (Max - Min + 1) + Min);
                        chest.getInventory().addItem(new ItemStack(Material.valueOf(item), amount));
                    }
                }
                Bukkit.broadcastMessage("§a[CustomLootable]§r 战利品刷新完成！");
            }
        }.runTaskTimer(CustomLootable.getPlugin(CustomLootable.class), 20, 20);
    }
}
