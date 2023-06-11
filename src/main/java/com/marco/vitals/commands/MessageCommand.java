package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.GRAY + "Usage: /message <player> <message>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
            return true;
        } else if (sender instanceof Player && Vitals.hasToggledMessages((Player) sender)) {
            sender.sendMessage(ChatColor.RED + "You have messages disabled.");
            return true;
        } else if (Vitals.hasToggledMessages(target) && !sender.hasPermission("vitals.bypass.tpm")) {
            sender.sendMessage(ChatColor.RED + "That person is not available.");
            return true;
        }

        if (sender instanceof Player player) {
            Vitals.conversations.put(player.getUniqueId(), target.getUniqueId());
            Vitals.conversations.put(target.getUniqueId(), player.getUniqueId());
        } else {
            Vitals.conversations.remove(target.getUniqueId());
        }

        handleMessage(sender, target, Arrays.copyOfRange(args, 1, args.length));
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
        else return Collections.emptyList();
    }

    public static void handleMessage(CommandSender sender, Player target, String[] msg) {
        List<String> argsList = Arrays.asList(msg);
        String message = String.join(" ", argsList);

        ChatColor color = ChatColor.GRAY;

        sender.sendMessage(color + "(To " + target.getDisplayName() + color + ") " + message);
        String senderName = sender instanceof Player ? ((Player) sender).getDisplayName() : "CONSOLE";
        target.sendMessage(color + "(From " + senderName + color + ") " + message);
        if (Vitals.shouldPlayMessageSound(target)) target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);

        // Bespoke overseer report since there's no need to alert sender or target if they're overseers.
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.hasPermission("vitals.overseer") && !Boolean.TRUE.equals(Vitals.overseers.get(p.getUniqueId())) && p != sender && p != target) {
                p.sendMessage(ChatColor.GRAY + "[Vitals: " + "(" + sender.getName() + " -> " + target.getName() + ") " + message + "]");
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[Vitals: " + "(" + sender.getName() + " -> " + target.getName() + ") " + message + "]");
    }
}
