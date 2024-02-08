package org.yuezhikong.plugins.event;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.yuezhikong.plugins.CustomLootable;

import java.util.List;

import static org.bukkit.GameMode.CREATIVE;

public class onDestroyChest implements Listener {
    private FileConfiguration config = CustomLootable.getServerconfig();
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event)
    {
        List<String> chestList = config.getStringList("ChestList");
        int X = event.getBlock().getX();
        int Y = event.getBlock().getY();
        int Z = event.getBlock().getZ();
        for (String UUID : chestList){
            if((config.getInt("Chests." + UUID + ".X"))== X && (config.getInt("Chests." + UUID + ".Y") == Y) && (config.getInt("Chests." + UUID + ".Z") == Z)){
                if (!(event.getPlayer().hasPermission("customlootable.admin") || event.getPlayer().getGameMode().equals(CREATIVE))){
                    event.setCancelled(true);
                }
                else {
                    if ((config.getInt("Chests." + UUID + ".X"))== X && (config.getInt("Chests." + UUID + ".Y") == Y) && (config.getInt("Chests." + UUID + ".Z") == Z)){
                        config.set("Chests." + UUID, null);
                        chestList.removeIf(UUID::equals);
                        config.set("ChestList", chestList);
                        CustomLootable.getPlugin(CustomLootable.class).saveConfig();
                        event.getPlayer().sendMessage("已移除战利品箱");
                        return;
                    }
                }
            }
        }
    }
}
