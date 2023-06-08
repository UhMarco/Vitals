package com.marco.vitals;

import com.marco.vitals.commands.GameModeCommand;
import com.marco.vitals.commands.OverseerCommand;
import com.marco.vitals.commands.TeleportCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Vitals extends JavaPlugin {
    public static Map<UUID, Boolean> overseers = new HashMap<>();
    // True means disabled - allowing this feature to be enabled by default.

    public static void overseerReport(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("vitals.overseer") && !Boolean.TRUE.equals(overseers.get(player.getUniqueId()))) {
                player.sendMessage(ChatColor.GRAY + "[Vitals: " + message + "]");
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[Vitals: " + message + "]");
    }

    @Override
    public void onEnable() {
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("overseer").setExecutor(new OverseerCommand());
    }
}