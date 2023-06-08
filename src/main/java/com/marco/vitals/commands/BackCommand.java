package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("vitals.back")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            }

            Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : player;

            if (target != null && player != target && !player.hasPermission("vitals.back.others")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            } else if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                return true;
            }

            Location location = Vitals.backs.get(target.getUniqueId());

            if (location == null) {
                sender.sendMessage(ChatColor.RED + "No location to return to.");
                return true;
            }

            if (player == target) {
                Vitals.overseerReport(player.getDisplayName() + " teleported to a previous location.");
            } else {
                Vitals.overseerReport(player.getDisplayName() + " teleported " + target.getDisplayName() + " to a previous location.");
            }
            Vitals.backs.put(target.getUniqueId(), target.getLocation());
            target.teleport(location);
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return false;
    }
}
