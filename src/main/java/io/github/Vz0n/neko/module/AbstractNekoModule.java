package io.github.Vz0n.neko.module;

import com.google.inject.AbstractModule;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;
import io.github.Vz0n.neko.classes.NekoConfiguration;
import io.github.Vz0n.neko.classes.RatelimitContainer;

public class AbstractNekoModule extends AbstractModule {
    
    private NekoFetcher pluginInstance;
    private NekoConfiguration configInstance;
    private RatelimitContainer cooldownInstance;

    public AbstractNekoModule(NekoFetcher plugin){
        this.pluginInstance = plugin;
        this.configInstance = new NekoConfiguration(plugin);
        this.cooldownInstance = new RatelimitContainer(plugin, 
                                                configInstance.getCooldownTime(),
                                                configInstance.getImageLimit());
    }  

    public void configure(){
        // Class bindings
        bind(ImageProvider.class).to(NekosLifeProvider.class);

        // Instance bindings
        bind(NekoFetcher.class).toInstance(pluginInstance);
        bind(NekoConfiguration.class).toInstance(configInstance);
        bind(RatelimitContainer.class).toInstance(cooldownInstance);
        
    }

}
