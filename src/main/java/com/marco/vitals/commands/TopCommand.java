package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TopCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "teleport.top")) return true;

            Player target = args.length != 0 ? Bukkit.getPlayer(args[0]) : player;

            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
                return true;
            } else if (target != player && Vitals.lacksPermission(player, "teleport.top.others")) return true;

            int x = target.getLocation().getBlockX();
            int z = target.getLocation().getBlockZ();

            Location unsafe = new Location(
                    target.getWorld(),
                    x,
                    target.getWorld().getHighestBlockYAt(x, z) + 1,
                    z,
                    target.getLocation().getYaw(),
                    target.getLocation().getPitch()
            );

            // I don't care about safety.

            Vitals.overseerReport(player.getName() + " teleported " + (player == target ? "up" : target.getName() + " up"));
            Vitals.backs.put(target.getUniqueId(), target.getLocation());
            target.teleport(unsafe);

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
