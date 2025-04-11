package io.github.Vz0n.neko.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpUtil {

    @Nullable
    public static JSONObject getJSONResponse(URL url) throws IOException, ParseException, URISyntaxException {

          JSONParser parser = new JSONParser();

          // Create URI and connection
          HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();

          // Server is not available
          if(httpsConn.getResponseCode() != 200) return null;
    
            
          InputStream stream = httpsConn.getInputStream();

          return (JSONObject) parser.parse(
                  new String(stream.readAllBytes())
          );
          
    }
 
    @Nullable
    public static InputStream getImage(URL url) throws IOException, URISyntaxException {

        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();

        // Response isn't an image and/or server is unavailable.
        if(!httpsConn.getContentType().startsWith("image/")) return null;

        return httpsConn.getInputStream();

    }

}
