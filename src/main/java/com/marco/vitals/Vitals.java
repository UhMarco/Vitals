package com.marco.vitals;

import com.marco.vitals.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Vitals extends JavaPlugin {
    public static Map<UUID, Boolean> overseers = new HashMap<>();
    // True means disabled - allowing this feature to be enabled by default.

    public static Map<UUID, Location> backs = new HashMap<>();

    public static void overseerReport(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("vitals.overseer") && !Boolean.TRUE.equals(overseers.get(player.getUniqueId()))) {
                player.sendMessage(ChatColor.GRAY + "[Vitals: " + message + "]");
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[Vitals: " + message + "]");
    }

    public static boolean lacksPermission(Player player, String permission) {
        if (!player.hasPermission("vitals." + permission)) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions.");
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("tphere").setExecutor(new TeleportHereCommand());
        getCommand("tppos").setExecutor(new TeleportPositionCommand());
        getCommand("tpall").setExecutor(new TeleportAllCommand());
        getCommand("top").setExecutor(new TopCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("overseer").setExecutor(new OverseerCommand());
    }
}