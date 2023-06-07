package com.marco.vitals.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            Player target = (args.length >= 1) ? Bukkit.getPlayer(args[0]) : player;

            if (!player.hasPermission("vitals.gamemode") || (player != target && !player.hasPermission("vitals.gamemode.others"))) {
                player.sendMessage("Insufficient permissions.");
                return true;
            }

            switch (label) {
                case "gamemode":
                case "gm":
                    break;
                case "gmc":
                    break;
                case "gms":
                    break;
                case "gmsp":
                    break;
            }

            return true;
        }
        return false;
    }
}
