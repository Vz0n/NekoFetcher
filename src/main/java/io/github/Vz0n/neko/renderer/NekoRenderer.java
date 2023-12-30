package io.github.Vz0n.neko.renderer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MinecraftFont;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.Image;

public class NekoRenderer extends MapRenderer {

    private Image image;

    public NekoRenderer(Image image){
        super();
        this.image = image;
    }

    public void render(MapView view, MapCanvas canvas, Player player){
        canvas.drawText(1, 1, MinecraftFont.Font, "uwu owo");
    }
    
}
