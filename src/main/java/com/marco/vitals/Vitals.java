package com.marco.vitals;

import com.marco.vitals.commands.GamemodeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Vitals extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("gamemode").setExecutor(new GamemodeCommand());
    }
}