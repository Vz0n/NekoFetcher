package io.github.Vz0n.neko.component;

import io.github.Vz0n.neko.NekoFetcher;

// Simple interface that represents a plugin component
public interface NekoComponent {

    // The method that gets called when initializing the module, and also
    // when reloading it
    void init(NekoFetcher plugin);

}
