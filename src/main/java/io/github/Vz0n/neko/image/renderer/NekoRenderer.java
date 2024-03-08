package io.github.Vz0n.neko.image.renderer;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.Image;

public class NekoRenderer extends MapRenderer {

    private Image image;
    private boolean rendered;

    public void render(MapView view, MapCanvas canvas, Player player){
        if(rendered) return;

        canvas.drawImage(0, 0, image);
        rendered = true;
    }
    
    public void setImage(Image newImage){
        this.image = newImage;
    }

    public boolean hasImage(){
        return image != null;
    }
}
