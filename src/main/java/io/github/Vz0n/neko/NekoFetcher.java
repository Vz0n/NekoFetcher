package io.github.Vz0n.neko;

import io.github.Vz0n.neko.component.impl.EconomyManager;
import io.github.Vz0n.neko.component.impl.ImageManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.github.Vz0n.neko.component.NekoComponent;
import io.github.Vz0n.neko.component.impl.RatelimitContainer;
import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.command.NekoCommand;
import io.github.Vz0n.neko.inject.AbstractNekoComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class NekoFetcher extends JavaPlugin {

    @Inject private NekoConfiguration config;

    private ArrayList<NekoComponent> loadedComponents = new ArrayList<>();
    private final ImmutableList<Class<? extends NekoComponent>> componentClassList = ImmutableList.of(
            RatelimitContainer.class,
            ImageManager.class,
            EconomyManager.class
    );

    @Override
    public void onEnable() {
        // Initialize injector and create instances.
        Injector injector = Guice.createInjector(new AbstractNekoComponent(this));
        injector.injectMembers(this);

        for(Class<? extends NekoComponent> c : componentClassList){
            try {
                // Don't enable Economy if Vault or the config setting is not enabled
                if(c == EconomyManager.class){
                    if(!this.getServer().getPluginManager().isPluginEnabled("Vault") ||
                            !config.isSettingEnabled("economy", false)) continue;
                }

                this.getLogger().info(String.format("Enabling %s component...", c.getName().split("\\.")[6]));
                loadedComponents.add(injector.getInstance(c));
            } catch(Exception e){
                getLogger().info(
                        String.format("An error happened while loading component: %s", e.getMessage()));
                getLogger().info("If this seems a bug, report it to the plugin developer");
                getLogger().info("The plugin will be disabled.");

                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

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

    @Nullable
    public NekoComponent getNekoComponent(Class<? extends NekoComponent> clazz){
        for(NekoComponent component : loadedComponents){
            if(component.getClass().equals(clazz)) return component;
        }

        // The module is not active, so just return null
        return null;
    }

    public NekoConfiguration getNekoConfig() {
        return config;
    }

}
