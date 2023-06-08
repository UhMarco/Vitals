package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            boolean current = Boolean.TRUE.equals(Vitals.toggledSounds.get(player.getUniqueId()));
            Vitals.toggledSounds.put(player.getUniqueId(), !current);
            sender.sendMessage(ChatColor.GRAY + "Message sounds " + (current ? "on" : "off") + ".");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }
}
