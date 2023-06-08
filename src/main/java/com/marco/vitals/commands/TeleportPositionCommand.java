package com.marco.vitals.commands;

import com.google.common.collect.ImmutableList;
import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportPositionCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport.position")) return true;

            if (3 > args.length || args.length > 4) {
                player.sendMessage(ChatColor.GRAY + "Usage: /tppos <x> <y> <z> [world]");
                return true;
            } else if (args.length == 4) {
                if (Vitals.lacksPermission(player, "teleport.world")) return true;
                World world = Bukkit.getWorld(args[3]);
                if (world == null) {
                    player.sendMessage(ChatColor.RED + "World " + args[3] + " not found.");
                    return true;
                }
            }

            double x = args[0].startsWith("~") ? player.getLocation().getX() + (args[0].length() > 1 ? Double.parseDouble(args[0].substring(1)) : 0) : Double.parseDouble(args[0]);
            double y = args[1].startsWith("~") ? player.getLocation().getY() + (args[1].length() > 1 ? Double.parseDouble(args[1].substring(1)) : 0) : Double.parseDouble(args[1]);
            double z = args[2].startsWith("~") ? player.getLocation().getZ() + (args[2].length() > 1 ? Double.parseDouble(args[2].substring(1)) : 0) : Double.parseDouble(args[2]);

            World world = args.length != 4 ? player.getWorld() : Bukkit.getWorld(args[3]);
            if (world != player.getWorld() && Vitals.lacksPermission(player, "teleport.world")) return true;
            else if (world == null) {
                player.sendMessage(ChatColor.RED + "World " + args[3] + " not found.");
                return true;
            }

            Vitals.overseerReport(player.getName() + " teleported to " + x + " " + y + " " + z + " in " + world.getName());
            Vitals.backs.put(player.getUniqueId(), player.getLocation());
            player.teleport(new Location(world, x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch()));
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1 -> {
                return ImmutableList.of("~", "~ ~", "~ ~ ~");
            }
            case 2 -> {
                return ImmutableList.of("~", "~ ~");
            }
            case 3 -> {
                return ImmutableList.of("~");
            }
            case 4 -> {
                List<String> worlds = new ArrayList<>();
                Bukkit.getWorlds().forEach(w -> worlds.add(w.getName()));
                return worlds;
            }
            default -> {
                return null;
            }
        }
    }
}
