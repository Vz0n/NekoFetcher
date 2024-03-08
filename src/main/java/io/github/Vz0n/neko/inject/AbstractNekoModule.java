package io.github.Vz0n.neko.inject;

import com.google.inject.AbstractModule;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.classes.NekoConfiguration;
import io.github.Vz0n.neko.classes.RatelimitContainer;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;

public class AbstractNekoModule extends AbstractModule {
    
    private final NekoFetcher pluginInstance;
    private NekoConfiguration config;
    private RatelimitContainer ratelimitContainer;

    public AbstractNekoModule(NekoFetcher plugin){
        this.pluginInstance = plugin;

        // Not the best way tho
        this.config = new NekoConfiguration(plugin);
        this.ratelimitContainer = new RatelimitContainer(plugin, config);
    }  

    public void configure(){
        // Class bindings
        bind(ImageProvider.class).to(NekosLifeProvider.class);

        bind(NekoFetcher.class).toInstance(pluginInstance);
        bind(NekoConfiguration.class).toInstance(config);
        bind(RatelimitContainer.class).toInstance(ratelimitContainer);
        
    }

}
