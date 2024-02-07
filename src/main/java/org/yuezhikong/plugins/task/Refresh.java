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
                int i = 0;
                while (i < chestList.size()){
                    if (chestList.get(i) == null){
                        Bukkit.broadcastMessage("§c[CustomLootable]§r 战利品刷新失败！");
                        Bukkit.getScheduler().cancelTasks(CustomLootable.getPlugin(CustomLootable.class));
                    }
                    int X = config.getInt("Chests." + chestList.get(i) + ".X");
                    int Y = config.getInt("Chests." + chestList.get(i) + ".Y");
                    int Z = config.getInt("Chests." + chestList.get(i) + ".Z");
                    World world = Bukkit.getWorld(java.util.UUID.fromString(Objects.requireNonNull(config.getString("Chests." + chestList.get(i) + ".World"))));
                    Location location = new Location(world, X, Y, Z);
                    chest = (Chest) location.getBlock().getState();
                    String BindID = config.getString("Chests." + chestList.get(i) + ".BindID");
                    int Max = lootable.getInt("Loots." + BindID + ".Max");
                    int Min = lootable.getInt("Loots." + BindID + ".Min");
                    List<String> items = lootable.getStringList("Loots." + BindID + ".items");
                    for (String item : items){
                        int amount = (int) Math.floor(Math.random() * (Max - Min + 1) + Min);
                        chest.getInventory().addItem(new ItemStack(Material.valueOf(item), amount));
                    }
                    i++;
                }
                Bukkit.broadcastMessage("§a[CustomLootable]§r 战利品刷新完成！");
            }
        }.runTaskTimer(CustomLootable.getPlugin(CustomLootable.class), 20, 20);
    }
}
