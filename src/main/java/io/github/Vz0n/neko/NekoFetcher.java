package io.github.Vz0n.neko;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;
import io.github.Vz0n.neko.util.NekoConfiguration;

public class NekoFetcher extends JavaPlugin {

    public ImageProvider imageProvider;
    public NekoConfiguration config;

    @Override
    public void onEnable() {
       getLogger().info("Enabling plugin...");

       File dataFolder = this.getDataFolder();
       File configFile = new File(dataFolder, "config.yml");

       // Create our config folder and files if doesn't exists
       if(!dataFolder.exists()) dataFolder.mkdir();
       if(!configFile.exists()) this.saveDefaultConfig();
      
       config = new NekoConfiguration(this.getConfig());
       imageProvider = new NekosLifeProvider(this.getLogger());
       this.getServer().getPluginCommand("nget")
                .setExecutor(new GetCommand(this.imageProvider, this.config));
                
       getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
       getLogger().info("Disabling plugin...");
       // Do stuff
       getLogger().info("Plugin disabled!");
    }

}
