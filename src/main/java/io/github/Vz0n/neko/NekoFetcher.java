package io.github.Vz0n.neko;

import org.bukkit.plugin.java.JavaPlugin;

public class NekoFetcher extends JavaPlugin {

    @Override
    public void onEnable() {
       getLogger().info("Enabling plugin...");
       // Do stuff
       getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
       getLogger().info("Disabling plugin...");
       // Do stuff
       getLogger().info("Plugin disabled!");
    }

}
