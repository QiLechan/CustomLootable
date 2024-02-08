package org.yuezhikong.plugins.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.yuezhikong.plugins.CustomLootable;

import java.util.List;

public class Bind implements CommandExecutor {
    private FileConfiguration config = CustomLootable.getServerconfig();
    private FileConfiguration lootable = CustomLootable.getLootable();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            return false;
        }
        List<String> chestList = config.getStringList("ChestList");
        for (String UUID : chestList) {
            if (args[0].equals(UUID)){
                config.set("Chests." + UUID + ".BindID", args[1]);
                CustomLootable.getPlugin(CustomLootable.class).saveConfig();
                sender.sendMessage("§a绑定成功");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED+"我们无法找到您参数所表示的目标箱子，操作无法完成");
        return true;
    }
}
