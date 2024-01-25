package io.github.Vz0n.neko.util;

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

    @Nullable
    public static JSONObject getJSONResponse(String url) throws IOException, ParseException {

          JSONParser parser = new JSONParser();
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
        if(!conn.getContentType().startsWith("image/")) return null;

        return ImageIO.read(conn.getInputStream());

    }

}
