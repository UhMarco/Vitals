package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReplyCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            UUID targetUUID = Vitals.conversations.get(player.getUniqueId());
            Player target = Bukkit.getPlayer(targetUUID);
            if (target == null) {
                sender.sendMessage(ChatColor.GRAY + "No one to reply to.");
                return true;
            } else if (Vitals.hasToggledMessages(player)) {
                sender.sendMessage(ChatColor.RED + "You have messages disabled.");
                return true;
            } else if (Vitals.hasToggledMessages(target) && !sender.hasPermission("vitals.bypass.tpm")) {
                sender.sendMessage(ChatColor.RED + "That person is not available.");
                return true;
            }

            MessageCommand.handleMessage(sender, target, args);
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
