package com.marco.vitals.commands;

import com.google.common.collect.ImmutableList;
import com.marco.vitals.Vitals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameModeCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("vitals.gamemode")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            }

            int index = label.equals("gamemode") || label.equals(("gm")) ? 1 : 0;
            Player target = (args.length > index) ? Bukkit.getPlayer(args[index]) : player;

            if (target != null && player != target && !player.hasPermission("vitals.gamemode.other")) {
                player.sendMessage(ChatColor.RED + "Insufficient permissions.");
                return true;
            } else if (target == null) {
                player.sendMessage(ChatColor.RED + "Player " + args[index] + " not found.");
                return true;
            }

            if (index == 1) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GRAY + "Usage: /gamemode <gamemode> [target]");
                    return true;
                }
                String gm = args[0].toLowerCase();
                switch (gm) {
                    case "creative", "c" -> updateGameMode(player, target, GameMode.CREATIVE);
                    case "survival", "s" -> updateGameMode(player, target, GameMode.SURVIVAL);
                    case "spectator", "sp" -> updateGameMode(player, target, GameMode.SPECTATOR);
                    case "adventure", "a" -> updateGameMode(player, target, GameMode.ADVENTURE);
                    default -> player.sendMessage(ChatColor.GRAY + "Usage: /gamemode <gamemode> [target]");
                }
            } else {
                switch (label) {
                    case "gmc" -> updateGameMode(player, target, GameMode.CREATIVE);
                    case "gms" -> updateGameMode(player, target, GameMode.SURVIVAL);
                    case "gmsp" -> updateGameMode(player, target, GameMode.SPECTATOR);
                    case "gma" -> updateGameMode(player, target, GameMode.ADVENTURE);
                }
            }

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return false;
    }

    private void updateGameMode(Player sender, Player target, GameMode gm) {
        if (target.getGameMode() == gm) return;
        target.setGameMode(gm);
        target.sendMessage("Gamemode updated.");
        if (sender != target) {
            Vitals.overseerReport(sender.getDisplayName() + " set " + target.getDisplayName() + "'s gamemode to " + gm.toString().toLowerCase());
        } else {
            Vitals.overseerReport(sender.getDisplayName() + " set their gamemode to " + gm.toString().toLowerCase());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if ((label.equals("gamemode") || label.equals("gm")) && args.length == 1) {
            return ImmutableList.of("creative", "survival", "adventure", "spectator");
        } else {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
            return players;
        }
    }
}
