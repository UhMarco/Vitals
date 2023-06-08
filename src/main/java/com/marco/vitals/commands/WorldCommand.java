package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport.world")) return true;

            if (args.length == 0) {
                player.sendMessage(ChatColor.GRAY + "Usage: /world <world> [player]");
                return true;
            }

            World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                player.sendMessage(ChatColor.RED + "World " + args[0] + " not found");
                return true;
            }

            Player target = args.length == 2 ? Bukkit.getPlayer(args[1]) : player;
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[1] + " not found.");
                return true;
            } else if (target != player && Vitals.lacksPermission(player, "teleport.world.others")) return true;
            else if (world == target.getWorld()) return true;

            Vitals.overseerReport(player.getName() + " switched " + (target != player ? target.getName() : "") + "world to " + world.getName());
            Vitals.backs.put(target.getUniqueId(), target.getLocation());
            target.teleport(world.getSpawnLocation());
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> worlds = new ArrayList<>();
            Bukkit.getWorlds().forEach(w -> {
                if (w != ((Player) sender).getWorld()) worlds.add(w.getName());
            });
            return worlds;
        }
        return null;
    }
}
