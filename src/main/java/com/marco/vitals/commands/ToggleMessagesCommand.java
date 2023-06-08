package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleMessagesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "togglemessages")) return true;
            boolean current = Boolean.TRUE.equals(Vitals.toggledMessages.get(player.getUniqueId()));
            Vitals.toggledMessages.put(player.getUniqueId(), !current);
            sender.sendMessage(ChatColor.GRAY + "Message toggled " + (current ? "on" : "off") + ".");

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }
}
