package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeleportHereCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport.others")) return true;
            if (args.length == 0) {
                player.sendMessage(ChatColor.GRAY + "Usage: /tphere <player>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                return true;
            } else if (target == player) return true;

            Vitals.overseerReport(player.getName() + " teleported " + target.getName() + " to themself");
            Vitals.backs.put(target.getUniqueId(), target.getLocation());
            target.teleport(player);
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p != sender && p.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase())) players.add(p.getName());
            });
            return players;
        }
        return Collections.emptyList();
    }
}
