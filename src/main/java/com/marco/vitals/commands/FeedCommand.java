package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class FeedCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "feed")) return true;
            if (args.length == 0) {
                player.setFoodLevel(20);
                Vitals.overseerReport(player.getName() + " fed themself");
            } else {
                if (Vitals.lacksPermission(player, "feed.others")) return true;
                if (args[0].equalsIgnoreCase("all")) {
                    Bukkit.getOnlinePlayers().forEach(target -> {
                        target.setFoodLevel(20);
                    });
                    Vitals.overseerReport(player.getName() + " fed all");
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player not found.");
                    } else {
                        target.setFoodLevel(20);
                        Vitals.overseerReport(player.getName() + " fed " + target.getName());
                    }
                }
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
