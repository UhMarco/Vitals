package com.marco.vitals.listeners;

import com.marco.vitals.Vitals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        Vitals.backs.remove(uuid);
        Vitals.conversations.remove(uuid);
    }
}
