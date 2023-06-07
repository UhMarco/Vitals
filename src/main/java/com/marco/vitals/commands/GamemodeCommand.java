package com.marco.vitals.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
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

            // TODO: Send message to admins that someone has updated someone's gamemode.

            if (index == 1) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GRAY + "Usage: /gamemode <gamemode> [target]");
                    return true;
                }
                String gm = args[0].toLowerCase();
                switch (gm) {
                    case "creative", "c" -> target.setGameMode(GameMode.CREATIVE);
                    case "survival", "s" -> target.setGameMode(GameMode.SURVIVAL);
                    case "spectator", "sp" -> target.setGameMode(GameMode.SPECTATOR);
                    case "adventure", "a" -> target.setGameMode(GameMode.ADVENTURE);
                    default -> player.sendMessage(ChatColor.GRAY + "Usage: /gamemode <gamemode> [target]");
                }
            } else {
                switch (label) {
                    case "gmc" -> target.setGameMode(GameMode.CREATIVE);
                    case "gms" -> target.setGameMode(GameMode.SURVIVAL);
                    case "gmsp" -> target.setGameMode(GameMode.SPECTATOR);
                    case "gma" -> target.setGameMode(GameMode.ADVENTURE);
                }
            }

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Only players can run this command.");
        return false;
    }
}
