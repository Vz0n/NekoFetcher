package io.github.Vz0n.neko;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.Vz0n.neko.command.NGetCommand;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;

public class NekoFetcher extends JavaPlugin {

    // Start default provider
    public final ImageProvider DEFAULT_PROVIDER = new NekosLifeProvider();

    @Override
    public void onEnable() {
       getLogger().info("Enabling plugin...");
       this.getServer().getPluginCommand("nget")
                .setExecutor(new NGetCommand(DEFAULT_PROVIDER));
       getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
       getLogger().info("Disabling plugin...");
       // Do stuff
       getLogger().info("Plugin disabled!");
    }

}
