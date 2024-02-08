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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.yuezhikong.plugins.CustomLootable.executor;

public class Refresh implements Listener {
    private static FileConfiguration config = CustomLootable.getServerconfig();
    private static FileConfiguration lootable = CustomLootable.getLootable();
    private List<String> chestList = config.getStringList("ChestList");
    public static long AtimestampAfterAdding;
    public static long BtimestampAfterAdding;
    public static long CtimestampAfterAdding;
    @EventHandler
    public void ServerLoadEvent(ServerLoadEvent event){
        Runnable taskA = () -> {
            new BukkitRunnable()
            {
                @Override
                public void run() {
                    int i = 0;
                    long currentTimeMillis = System.currentTimeMillis();
                    AtimestampAfterAdding = currentTimeMillis + TimeUnit.MINUTES.toMillis(30);
                    while (i < chestList.size()){
                        if (chestList.get(i) == null){
                            Bukkit.getLogger().warning("有一个战利品箱子刷新失败!");
                            continue;
                        }
                        int X = config.getInt("Chests." + chestList.get(i) + ".X");
                        int Y = config.getInt("Chests." + chestList.get(i) + ".Y");
                        int Z = config.getInt("Chests." + chestList.get(i) + ".Z");
                        World world = Bukkit.getWorld(java.util.UUID.fromString(Objects.requireNonNull(config.getString("Chests." + chestList.get(i) + ".World"))));
                        Location location = new Location(world, X, Y, Z);
                        Chest chest = (Chest) location.getBlock().getState();
                        chest.getInventory().clear();
                        List<String> items = lootable.getStringList("Loots.A.items");
                        Random rand = new Random();
                        int randomNum = rand.nextInt(2)+1;
                        for (int j = 0; j < randomNum; j++){
                            randomNum = rand.nextInt(items.size()+1);
                            String item = items.get(randomNum);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                        /*
                        for (String item : items){
                            // int amount = (int) Math.floor(Math.random() * (Max - Min + 1) + Min);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                         **/
                        i++;
                    }
                    Bukkit.broadcastMessage("§a[CustomLootable]§r 战利品A刷新完成！");
                }
            }.runTask(CustomLootable.getPlugin(CustomLootable.class));
        };
        Runnable taskB = () -> {
            new BukkitRunnable()
            {
                @Override
                public void run() {
                    int i = 0;
                    long currentTimeMillis = System.currentTimeMillis();
                    BtimestampAfterAdding = currentTimeMillis + TimeUnit.MINUTES.toMillis(10);
                    while (i < chestList.size()){
                        if (chestList.get(i) == null){
                            Bukkit.getLogger().warning("有一个战利品箱子刷新失败!");
                            continue;
                        }
                        int X = config.getInt("Chests." + chestList.get(i) + ".X");
                        int Y = config.getInt("Chests." + chestList.get(i) + ".Y");
                        int Z = config.getInt("Chests." + chestList.get(i) + ".Z");
                        World world = Bukkit.getWorld(java.util.UUID.fromString(Objects.requireNonNull(config.getString("Chests." + chestList.get(i) + ".World"))));
                        Location location = new Location(world, X, Y, Z);
                        Chest chest = (Chest) location.getBlock().getState();
                        chest.getInventory().clear();
                        // String BindID = config.getString("Chests." + chestList.get(i) + ".BindID");
                        List<String> items = lootable.getStringList("Loots.B.items");
                        int max = items.size();
                        int min = 1;
                        Random rand = new Random();
                        int maxitems = 4;
                        int minitems = 1;
                        int randomNum = rand.nextInt((maxitems - minitems + 1)) + min;
                        for (int j = 0; j < randomNum; j++){
                            randomNum = rand.nextInt(max) + 1;
                            String item = items.get(randomNum);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                        /**
                        for (String item : items){
                            // int amount = (int) Math.floor(Math.random() * (Max - Min + 1) + Min);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                         **/
                        i++;
                    }
                    Bukkit.broadcastMessage("§a[CustomLootable]§r 战利品B刷新完成！");
                }
            }.runTask(CustomLootable.getPlugin(CustomLootable.class));
        };
        Runnable taskC = () -> {
            new BukkitRunnable()
            {
                @Override
                public void run() {
                    int i = 0;
                    long currentTimeMillis = System.currentTimeMillis();
                    CtimestampAfterAdding = currentTimeMillis + TimeUnit.MINUTES.toMillis(5);
                    while (i < chestList.size()){
                        if (chestList.get(i) == null){
                            Bukkit.getLogger().warning("有一个战利品箱子刷新失败!");
                            continue;
                        }
                        int X = config.getInt("Chests." + chestList.get(i) + ".X");
                        int Y = config.getInt("Chests." + chestList.get(i) + ".Y");
                        int Z = config.getInt("Chests." + chestList.get(i) + ".Z");
                        World world = Bukkit.getWorld(java.util.UUID.fromString(Objects.requireNonNull(config.getString("Chests." + chestList.get(i) + ".World"))));
                        Location location = new Location(world, X, Y, Z);
                        Chest chest = (Chest) location.getBlock().getState();
                        chest.getInventory().clear();
                        // String BindID = config.getString("Chests." + chestList.get(i) + ".BindID");
                        List<String> items = lootable.getStringList("Loots.C.items");
                        int max = items.size();
                        int min = 1;
                        Random rand = new Random();
                        int maxitems = 4;
                        int minitems = 1;
                        int randomNum = rand.nextInt((maxitems - minitems + 1)) + min;
                        for (int j = 0; j < randomNum; j++){
                            randomNum = rand.nextInt(max) + 1;
                            String item = items.get(randomNum);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                        /**
                        for (String item : items){
                            // int amount = (int) Math.floor(Math.random() * (Max - Min + 1) + Min);
                            String ItemID = lootable.getString("ItemSettings." + item + ".ItemID");
                            int amount = lootable.getInt("ItemSettings." + item + ".amount");
                            chest.getInventory().addItem(new ItemStack(Material.valueOf(ItemID), amount));
                        }
                         **/
                        i++;
                    }
                    Bukkit.broadcastMessage("§a[CustomLootable]§r 战利品C刷新完成！");
                }
            }.runTask(CustomLootable.getPlugin(CustomLootable.class));
        };
        executor.scheduleWithFixedDelay(taskA, 3, 30, TimeUnit.MINUTES);
        executor.scheduleWithFixedDelay(taskB, 3, 10, TimeUnit.MINUTES);
        executor.scheduleWithFixedDelay(taskC, 3, 5, TimeUnit.MINUTES);
        taskA.run();
        taskB.run();
        taskC.run();
    }
}
