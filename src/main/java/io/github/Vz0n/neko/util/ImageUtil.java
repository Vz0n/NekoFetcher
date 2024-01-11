package io.github.Vz0n.neko.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Nullable;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ImageUtil {

    // Get the image from REST and resize it to 128x128, as this is 
    // the only dimesions that will be used because Minecraft's maps
    // are small. Finally return the pixel array to pass the image
    // asynchronously.
    @Nullable
    public static BufferedImage getRESTProcessedImage(String url, String field){

     try{
       
       JSONObject jsonObj = HttpUtil.getJSONResponse(url);
       if(jsonObj == null || !(jsonObj.get(field) instanceof String)) return null;
    
       BufferedImage image = HttpUtil.getImage((String) jsonObj.get(field));
       
       if(image == null) return null;

       Graphics graphics = image.getGraphics();
       graphics.drawImage(image, 0, 0, 128, 128, null);
       graphics.dispose();
       
       return image;

     } catch(IOException | ParseException e){
        e.printStackTrace();
        return null;
     }

    }
  
    
}
