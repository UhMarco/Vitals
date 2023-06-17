package com.marco.vitals.commands;

import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Vitals.lacksPermission(player, "heal")) return true;
            if (args.length == 0) {
                player.setHealth(20);
                player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
                Vitals.overseerReport(player.getName() + " healed themself");
            } else {
                if (Vitals.lacksPermission(player, "heal.others")) return true;
                if (args[0].equalsIgnoreCase("all")) {
                    Bukkit.getOnlinePlayers().forEach(target -> {
                        target.setHealth(20);
                        target.getActivePotionEffects().forEach(e -> target.removePotionEffect(e.getType()));
                    });
                    Vitals.overseerReport(player.getName() + " healed all");
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player not found.");
                    } else {
                        target.setHealth(20);
                        target.getActivePotionEffects().forEach(e -> target.removePotionEffect(e.getType()));
                        Vitals.overseerReport(player.getName() + " healed " + target.getName());
                    }
                }
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            players.add("all");
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p != sender && p.getName().toLowerCase().startsWith(args[args.length - 1].toLowerCase())) players.add(p.getName());
            });
            return players;
        }
        return Collections.emptyList();
    }
}
