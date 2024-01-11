package io.github.Vz0n.neko;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.module.AbstractNekoModule;
import io.github.Vz0n.neko.util.NekoConfiguration;

public class NekoFetcher extends JavaPlugin {

    private ImageProvider imageProvider;
    private NekoConfiguration config;

    @Override
    public void onEnable() {
        getLogger().info("Enabling plugin...");
      
        // Initialize injector and create instances.
        Injector injector = Guice.createInjector(new AbstractNekoModule(this));
        imageProvider = injector.getInstance(ImageProvider.class);
        
        // Register commands
        this.getServer().getPluginCommand("nget")
                .setExecutor(new GetCommand(config, imageProvider));
                
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin...");
        // Do stuff
        getLogger().info("Plugin disabled!");
    }

    public NekoConfiguration getNekoConfig(){

        if(config == null){
            // Check config files and main plugin directory, create
            // if they does not exists.
            File dataFolder = this.getDataFolder();
            File configFile = new File(dataFolder, "config.yml");

            if(!dataFolder.exists()) dataFolder.mkdir();
            if(!configFile.exists()) this.saveDefaultConfig();

            config = new NekoConfiguration(this.getConfig());
        }

        return config;
    }

}
