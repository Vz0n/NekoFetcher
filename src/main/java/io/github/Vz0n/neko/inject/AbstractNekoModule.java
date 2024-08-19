package io.github.Vz0n.neko.inject;

import com.google.inject.AbstractModule;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.NekoConfiguration;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;


public class AbstractNekoModule extends AbstractModule {

    private final NekoFetcher pluginInstance;

    public AbstractNekoModule(NekoFetcher plugin){
        this.pluginInstance = plugin;
    }  

    public void configure(){

        bind(ImageProvider.class).to(NekosLifeProvider.class);
        bind(NekoFetcher.class).toInstance(pluginInstance);
        bind(NekoConfiguration.class).toInstance(new NekoConfiguration(pluginInstance));

    }
}
