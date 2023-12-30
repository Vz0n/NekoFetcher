package io.github.Vz0n.neko.image.impl;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.bukkit.map.MapView;

import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.renderer.NekoRenderer;
import io.github.Vz0n.neko.util.HttpUtil;

public class NekosLifeProvider implements ImageProvider {

    private final String DEFAULT_ENDPOINT = "https://nekos.life/api/v2/image/neko";
    
    @Nullable
    public MapView getImage(MapView map){

        Map<String, String> resp = HttpUtil.getJSONResponse(DEFAULT_ENDPOINT);

        if(resp == null){ 
          // Return the same object, indicating an error.
          return null;
        }

        try{

          URL url = new URL(resp.get("url"));
          HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
          
          if(conn.getResponseCode() != 200 && 
                            !conn.getContentType().startsWith("image/")){
              return null;
          }

          BufferedImage img = ImageIO.read(conn.getInputStream());
          Graphics graphic = img.getGraphics();
          graphic.drawImage(img, 0, 0, 64, 64, null);
          graphic.dispose();
          
          map.addRenderer(
              new NekoRenderer(img)
          );

          return map;

        } catch(IOException e){

          return null;
          
        }
        
    }
}
