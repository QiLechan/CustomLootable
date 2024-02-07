package org.yuezhikong.plugins.event;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.World;
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
                String world = event.getPlayer().getWorld().getUID().toString();
                List<String> chestList = config.getStringList("ChestList");
                for (String UUID : chestList){
                    if ((config.getInt("Chests." + UUID + ".X"))== X && (config.getInt("Chests." + UUID + ".Y") == Y) && (config.getInt("Chests." + UUID + ".Z") == Z)){
                        event.getPlayer().sendMessage("此箱子已经绑定过");
                        return;
                    }
                }
                config.set("Chests." + uuid + ".World", world);
                config.set("Chests." + uuid + ".X", X);
                config.set("Chests." + uuid + ".Y", Y);
                config.set("Chests." + uuid + ".Z", Z);
                chestList.add(uuid.toString());
                config.set("ChestList", chestList);
                CustomLootable.getPlugin(CustomLootable.class).saveConfig();
                BaseComponent Uuid = new TextComponent(uuid.toString());
                Uuid.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("点击填充指令到输入框")));
                Uuid.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/clbind " + uuid + " "));
                Uuid.setColor(ChatColor.RED);
                event.getPlayer().spigot().sendMessage(Uuid);
                event.getPlayer().sendMessage("请输入/clbind <uuid> <id>绑定战利品表");
            }
        }
    }
}
