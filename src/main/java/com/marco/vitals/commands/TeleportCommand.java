package com.marco.vitals.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("vitals.teleport")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            }
            switch (args.length) {
                case 1 -> {
                    Player destination = Bukkit.getPlayer(args[0]);
                    if (destination == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                        return true;
                    }
                    // TODO: Add back functionality
                    player.teleport(destination);
                }
                case 2 -> {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                        return true;
                    }
                    Player destination = Bukkit.getPlayer(args[1]);
                    if (destination == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[1] + " not found.");
                        return true;
                    }
                    // TODO: Add back functionality
                    target.teleport(destination);
                }
                default -> player.sendMessage(ChatColor.GRAY + "Usage: /tp <player> [target]");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return false;
    }
}
