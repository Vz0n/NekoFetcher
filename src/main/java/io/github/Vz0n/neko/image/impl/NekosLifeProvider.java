package io.github.Vz0n.neko.image.impl;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.json.simple.parser.ParseException;

import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.renderer.NekoRenderer;
import io.github.Vz0n.neko.util.HttpUtil;

public class NekosLifeProvider implements ImageProvider {

    private final String DEFAULT_ENDPOINT = "https://nekos.life/api/v2/img/neko";
    private Logger logger;
    
    // We need a logger to show errors on console.
    // TODO: improve this
    public NekosLifeProvider(Logger pluginLogger){
      this.logger = pluginLogger;
    }

    public MapView getImage(MapView map){

        try{

            // Fetch endpoint
            Map<String, String> resp = HttpUtil.getJSONResponse(DEFAULT_ENDPOINT);

            // Server is unavailable for requests
            if(resp == null) return null;
            
            // Get image returned from endpoint
            BufferedImage image = HttpUtil.getImage(resp.get("url"));
            
            // Server returned a non-image response
            if(image == null) return null;
          
            // Set the map renderer and lock it.
            map.addRenderer(
               new NekoRenderer(image)
            );
            map.setLocked(true);

            return map;

        } catch(IOException e){
            logger.warning("An exception ocurred while fetching the image: " + e.getMessage());
            return map;
          
        } catch(ParseException e){
            logger.warning("An exception ocurred while parsing data: " + e.getMessage());
            return map;
        }
        
    }
}
