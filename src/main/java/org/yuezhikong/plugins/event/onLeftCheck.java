package org.yuezhikong.plugins.event;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.yuezhikong.plugins.CustomLootable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.bukkit.GameMode.CREATIVE;
import static org.bukkit.Material.CHEST;
import static org.bukkit.Material.STICK;

public class onLeftCheck implements Listener {
    private FileConfiguration config = CustomLootable.getServerconfig();
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && Objects.requireNonNull(event.getClickedBlock()).getType().equals(CHEST) && event.getMaterial().equals(STICK)){
            event.setCancelled(true);
            if (!(event.getPlayer().hasPermission("customlootable.admin") || event.getPlayer().getGameMode().equals(CREATIVE))){}
            else {
                UUID uuid = UUID.randomUUID();
                int X = event.getClickedBlock().getX();
                int Y = event.getClickedBlock().getY();
                int Z = event.getClickedBlock().getZ();
                List<String> chestList = config.getStringList("ChestList");
                for (String UUID : chestList){
                    if ((config.getInt("Chests." + UUID + ".X"))== X && (config.getInt("Chests." + UUID + ".Y") == Y) && (config.getInt("Chests." + UUID + ".Z") == Z)){
                        event.getPlayer().sendMessage("此箱子已经绑定过");
                        return;
                    }
                }
                config.set("Chests." + uuid + ".X", X);
                config.set("Chests." + uuid + ".Y", Y);
                config.set("Chests." + uuid + ".Z", Z);
                chestList.add(uuid.toString());
                config.set("ChestList", chestList);
                CustomLootable.getPlugin(CustomLootable.class).saveConfig();
            }
        }
    }
}
