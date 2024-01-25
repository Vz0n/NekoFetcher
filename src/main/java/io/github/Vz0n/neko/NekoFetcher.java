package io.github.Vz0n.neko;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.command.NekoCommand;
import io.github.Vz0n.neko.module.AbstractNekoModule;
import io.github.Vz0n.neko.classes.NekoConfiguration;
import io.github.Vz0n.neko.classes.CooldownContainer;

public class NekoFetcher extends JavaPlugin {

    @Inject private NekoConfiguration config;
    @Inject private CooldownContainer cooldownContainer;

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
        // Clear players from PlayerCooldownContainer if there are.
        this.cooldownContainer.clearPlayers();
        getLogger().info("Plugin disabled!");
    }

    public NekoConfiguration getNekoConfig() {
        return config;
    }

    public CooldownContainer getCooldowns() {
        return cooldownContainer;
    }

}
