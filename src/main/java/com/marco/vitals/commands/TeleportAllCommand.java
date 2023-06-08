package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport.all")) return true;

            Player target = args.length != 0 ? Bukkit.getPlayer(args[0]) : player;

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                return true;
            }

            Vitals.overseerReport(player.getName() + " teleported all players to " + (player == target ? "themself" : target.getName()));

            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p != target) {
                    Vitals.backs.put(p.getUniqueId(), p.getLocation());
                    p.teleport(target);
                }
            });

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }
}
