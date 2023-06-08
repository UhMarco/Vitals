package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OverseerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("vitals.overseer")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            }
            boolean current = Boolean.TRUE.equals(Vitals.overseers.get(player.getUniqueId()));
            Vitals.overseers.put(player.getUniqueId(), !current);
            sender.sendMessage(ChatColor.GRAY + "[Vitals: Overseer " + (current ? "enabled" : "disabled") + "]");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return false;
    }
}
