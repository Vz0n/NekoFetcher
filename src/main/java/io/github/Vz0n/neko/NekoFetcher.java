package io.github.Vz0n.neko;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.github.Vz0n.neko.component.NekoComponent;
import io.github.Vz0n.neko.component.impl.RatelimitContainer;
import io.github.Vz0n.neko.command.GetCommand;
import io.github.Vz0n.neko.command.NekoCommand;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.inject.AbstractNekoModule;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class NekoFetcher extends JavaPlugin {

    @Inject private NekoConfiguration config;
    @Inject private ImageProvider provider;

    private ArrayList<NekoComponent> loadedComponents = new ArrayList<>();
    private final ImmutableList<Class<? extends NekoComponent>> componentClassList = ImmutableList.of(
            RatelimitContainer.class
    );

    @Override
    public void onEnable() {
        // Initialize injector and create instances.
        Injector injector = Guice.createInjector(new AbstractNekoModule(this));
        injector.injectMembers(this);

        for(Class<? extends NekoComponent> c : componentClassList){
            try {
                loadedComponents.add(injector.getInstance(c));
            } catch(Exception e){
                this.getLogger().severe("One or more components failed to load!");
                this.getLogger().info(String.format("While loading %s: %s", c.getCanonicalName(),
                        e.getMessage()));
                this.getLogger().info("If this is a bug, report it to the plugin developer");
                this.getLogger().info("Plugin will be disabled.");

                // Not the best way tho
                this.setEnabled(false);
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
        for(NekoComponent c : loadedComponents){
            if(c.getClass().equals(clazz)) return c;
        }

        // The module is not active, so just return null
        return null;
    }

    public NekoConfiguration getNekoConfig() {
        return config;
    }

    public ImageProvider getImageProvider(){
        return provider;
    }


}
