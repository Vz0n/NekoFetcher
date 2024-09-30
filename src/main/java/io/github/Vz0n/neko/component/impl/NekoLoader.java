package io.github.Vz0n.neko.component.impl;

import com.google.inject.Inject;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.component.NekoComponent;

/* Class for managing stored neko images, it saves actual images and loads it when requested,
   i.e. when the server is started. */
public class NekoLoader implements NekoComponent {

    private NekoFetcher plugin;

    @Inject
    public NekoLoader(NekoFetcher plugin){
        this.plugin = plugin;
    }
}
