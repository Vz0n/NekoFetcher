package io.github.Vz0n.neko.inject;

import com.google.inject.AbstractModule;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.NekoConfiguration;


public class AbstractNekoComponent extends AbstractModule {

    private final NekoFetcher pluginInstance;

    public AbstractNekoComponent(NekoFetcher plugin){
        this.pluginInstance = plugin;
    }  

    public void configure(){
        
        bind(NekoFetcher.class).toInstance(pluginInstance);
        bind(NekoConfiguration.class).toInstance(new NekoConfiguration(pluginInstance));

    }
}
