package org.yuezhikong.plugins.event;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.yuezhikong.plugins.CustomLootable;
import org.yuezhikong.plugins.task.Refresh;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.STICK;
import static org.yuezhikong.plugins.task.Refresh.*;

public class onOpenChest implements Listener {
    private FileConfiguration config = CustomLootable.getServerconfig();
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && Objects.requireNonNull(event.getClickedBlock()).getType().equals(CHEST)))
            return;
        List<String> chestList = config.getStringList("ChestList");
        int X = event.getClickedBlock().getX();
        int Y = event.getClickedBlock().getY();
        int Z = event.getClickedBlock().getZ();
        for (String UUID : chestList){
            if(!((config.getInt("Chests." + UUID + ".X"))== X && (config.getInt("Chests." + UUID + ".Y") == Y) && (config.getInt("Chests." + UUID + ".Z") == Z)))
                continue;
            String BindID = config.getString("Chests." + UUID + ".BindID");
            if (BindID == null)
                continue;
            switch (BindID){
                case "A":{
                    long time = TimeUnit.MILLISECONDS.toSeconds(AtimestampAfterAdding - System.currentTimeMillis());
                    event.getPlayer().sendMessage(ChatColor.RED + "距离下一次刷新还有" + ChatColor.GOLD + time + ChatColor.RED + "秒");
                    break;
                }
                case "B":{
                    long time = TimeUnit.MILLISECONDS.toSeconds(BtimestampAfterAdding - System.currentTimeMillis());
                    event.getPlayer().sendMessage(ChatColor.RED + "距离下一次刷新还有" + ChatColor.GOLD + time + ChatColor.RED + "秒");
                    break;
                }
                case "C":{
                    long time = TimeUnit.MILLISECONDS.toSeconds(CtimestampAfterAdding - System.currentTimeMillis());
                    event.getPlayer().sendMessage(ChatColor.RED + "距离下一次刷新还有" + ChatColor.GOLD + time + ChatColor.RED + "秒");
                    break;
                }
            }
        }
    }
}
