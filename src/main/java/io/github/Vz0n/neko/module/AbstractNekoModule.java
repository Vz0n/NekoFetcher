package io.github.Vz0n.neko.module;

import com.google.inject.AbstractModule;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;
import io.github.Vz0n.neko.util.NekoConfiguration;

public class AbstractNekoModule extends AbstractModule {
    
    private NekoFetcher pluginInstance;

    public AbstractNekoModule(NekoFetcher plugin){
        this.pluginInstance = plugin;
    }  

    public void configure(){
        // Class bindings
        // For now, as nekos.life is the only provider this can safely be done
        bind(ImageProvider.class).to(NekosLifeProvider.class);

        // Instance bindings
        bind(NekoFetcher.class).toInstance(pluginInstance);
        bind(NekoConfiguration.class).toInstance(pluginInstance.getNekoConfig());
    }

}
