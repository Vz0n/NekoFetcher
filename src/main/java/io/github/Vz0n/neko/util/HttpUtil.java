package io.github.Vz0n.neko.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpUtil {

    @Nullable
    public static JSONObject getJSONResponse(String url) throws IOException, ParseException, URISyntaxException {

          JSONParser parser = new JSONParser();

          // Create URI and connection
          URL urlObj = new URI(url).toURL();
          HttpsURLConnection httpsConn = (HttpsURLConnection) urlObj.openConnection();

          // Server is not available
          if(httpsConn.getResponseCode() != 200) return null;
    
            
          InputStream stream = httpsConn.getInputStream();

          return (JSONObject) parser.parse(
                  new String(stream.readAllBytes())
          );
          
    }
 
    @Nullable
    public static BufferedImage getImage(String url) throws IOException, URISyntaxException {

        URL urlObj = new URI(url).toURL();
        HttpsURLConnection httpsConn = (HttpsURLConnection) urlObj.openConnection();

        // Response isn't a image and/or server is unavailable.
        if(!httpsConn.getContentType().startsWith("image/")) return null;

        return ImageIO.read(httpsConn.getInputStream());

    }

}
