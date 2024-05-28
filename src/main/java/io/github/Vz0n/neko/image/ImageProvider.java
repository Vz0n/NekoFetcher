package io.github.Vz0n.neko.image;

import org.bukkit.map.MapView;

import java.util.Optional;

public interface ImageProvider {

    public Optional<MapView> getImage(MapView map);
    
}
