package org.aoichaan0513.jmps.Command;

import org.aoichaan0513.jmps.API.CoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseFile implements CommandExecutor {
    JavaPlugin plugin;

    public BaseFile(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CoreAPI.hasPlayer(sender)) {
            Player sp = (Player) sender;
            if (CoreAPI.hasPermission(sp, 1)) {

            }
            sender.sendMessage(CoreAPI.getErrorPrefix() + "権限がありません。");
            return true;
        } else {
            return true;
        }
    }
}
