package io.github.Vz0n.neko.image.impl;

import javax.imageio.stream.ImageInputStream;
import java.net.URL;

import org.bukkit.map.MapView;
import org.json.simple.JSONObject;

import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.util.HttpUtil;

public class NekosLifeProvider implements ImageProvider {

    private final String DEFAULT_ENDPOINT = "https://nekos.life/api/v1/image";
    
    public MapView getImage(MapView map){

        JSONObject resp = HttpUtil.getJSONResponse(DEFAULT_ENDPOINT);

        if(resp == null){ 
          // Return the same object, indicating an error.
          return map;
        }
        

    
        return null;
    }
}
