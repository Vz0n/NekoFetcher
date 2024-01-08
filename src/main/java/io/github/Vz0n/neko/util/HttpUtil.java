package io.github.Vz0n.neko.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpUtil {
    
    private static JSONParser parser = new JSONParser();

    @Nullable
    public static JSONObject getJSONResponse(String url) throws IOException, ParseException {

          HttpsURLConnection htsconn = (HttpsURLConnection) 
                               new URL(url).openConnection();

          // Server is not available
          if(htsconn.getResponseCode() != 200) return null;
    
            
          InputStream stream = htsconn.getInputStream();
          JSONObject obj = (JSONObject) parser.parse(
            new String(stream.readAllBytes())
          );
               
          return obj;
    }
 
    @Nullable
    public static BufferedImage getImage(String url) throws IOException {
      
      HttpsURLConnection conn = (HttpsURLConnection) 
                               new URL(url).openConnection();

      // Response isn't a image and/or server is unavailable.
      // nekos.life doesn't return a image when it's down.
      if(!conn.getContentType().startsWith("image/")) return null;

      BufferedImage img = ImageIO.read(conn.getInputStream());
      Graphics graphic = img.getGraphics();
      graphic.drawImage(img, 0, 0, 128, 128, null);
      graphic.dispose();

      return img;
    }
}
