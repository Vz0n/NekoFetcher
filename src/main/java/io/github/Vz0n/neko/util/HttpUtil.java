package io.github.Vz0n.neko.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpUtil {
    
    private static JSONParser parser = new JSONParser();

    @Nullable
    public static JSONObject getJSONResponse(String url){

        try{
          HttpsURLConnection htsconn = (HttpsURLConnection) 
                               new URL(url).openConnection();

          if(htsconn.getResponseCode() != 200){
            // getLogger().severe("A requested provider didn't answer properly. Status code:", 
            //                 htsconn.getResponseCode());
            return null;
          }
            
          InputStream stream = htsconn.getInputStream();
          JSONObject obj = (JSONObject) parser.parse(
            new String(stream.readAllBytes())
          );
               
          return obj;
  
        } catch(IOException e){
            // Error happened on the connection.
            // getLogger().severe("Error connecting to a provider!", e.getCause())
            return null;
        } catch(ParseException e){
            // Server didn't return JSON data.
            // getLogger().severe("Error parsing data from provider!", e.getCause())
            return null;
        }
    }
    
}
