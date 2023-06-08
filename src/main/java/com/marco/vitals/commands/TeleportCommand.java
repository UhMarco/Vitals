package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport")) return true;
            switch (args.length) {
                case 1 -> {
                    Player destination = Bukkit.getPlayer(args[0]);
                    if (destination == null) {
                        player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                        return true;
                    }
                    Vitals.overseerReport(player.getName() + " teleported to " + destination.getDisplayName());
                    Vitals.backs.put(player.getUniqueId(), player.getLocation());
                    player.teleport(destination);
                }
                case 2 -> {
                    if (Vitals.lacksPermission(player, "teleport.others")) return true;
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
                    Vitals.overseerReport(player.getName() + " teleported " + target.getName() + " to " + destination.getName());
                    Vitals.backs.put(target.getUniqueId(), target.getLocation());
                    target.teleport(destination);
                }
                default -> player.sendMessage(ChatColor.GRAY + "Usage: /tp <player> [target]");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) return null;
        List<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (args.length != 1 || p != sender) players.add(p.getName());
        });
        return players;
    }
}
