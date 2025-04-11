package io.github.Vz0n.neko.image.impl;

import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.util.HttpUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class NekosLifeProvider implements ImageProvider {

    private final String DEFAULT_ENDPOINT = "https://nekos.life/api/v2/img/neko";

    @Nullable
    public URL getNewImageURL(){

        try {

            JSONObject jsonObj = HttpUtil.getJSONResponse(new URI(DEFAULT_ENDPOINT).toURL());
            if (jsonObj == null || !(jsonObj.get("url") instanceof String image_url)) return null;

            URI uri = new URI(image_url);

            return uri.toURL();

        } catch(IOException | ParseException | URISyntaxException e){
            e.printStackTrace();

            return null;
        }

    }

}
