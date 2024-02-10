package io.github.Vz0n.neko;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.command.NekoCommand;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.module.AbstractNekoModule;
import io.github.Vz0n.neko.classes.NekoConfiguration;
import io.github.Vz0n.neko.classes.RatelimitContainer;

public class NekoFetcher extends JavaPlugin {

    @Inject private NekoConfiguration config;
    @Inject private RatelimitContainer usesContainer;
    @Inject private ImageProvider provider;

    @Override
    public void onEnable() {
        // Initialize injector and create instances.
        Injector injector = Guice.createInjector(new AbstractNekoModule(this));
        injector.injectMembers(this);
        
        // Register commands
        this.getServer().getPluginCommand("nget")
                .setExecutor(injector.getInstance(GetCommand.class));
        this.getServer().getPluginCommand("neko")
                .setExecutor(injector.getInstance(NekoCommand.class));
                
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    // Just reloads the config object.
    public void reloadNekoConfig() {
        config.reloadFromPlugin(this);
    }

    public NekoConfiguration getNekoConfig() {
        return config;
    }

    public ImageProvider getImageProvider(){
        return provider;
    }

    public RatelimitContainer getUsesContainer(){
        return usesContainer;
    }

}
