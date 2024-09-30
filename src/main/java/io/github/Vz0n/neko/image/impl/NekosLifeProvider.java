package io.github.Vz0n.neko.image.impl;

import java.awt.image.BufferedImage;
import java.util.concurrent.Future;

import com.google.inject.Inject;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.renderer.NekoRenderer;
import io.github.Vz0n.neko.util.ImageUtil;

public class NekosLifeProvider implements ImageProvider {

    private final String DEFAULT_ENDPOINT = "https://nekos.life/api/v2/img/neko";
    private NekoFetcher plugin;
    
    @Inject
    public NekosLifeProvider(NekoFetcher plugin){
      this.plugin = plugin;
    }

    public NekoRenderer getNewImageRenderer(){

        NekoRenderer renderer = new NekoRenderer();
        BufferedImage image = ImageUtil.getRESTImage(DEFAULT_ENDPOINT, "url");

        if(image == null){
            plugin.getLogger().warning("Something went wrong while processing a image! Look above");
            plugin.getLogger().warning("Check your internet connectivity or report to the developer.");
            return renderer;
        }

        // Set the renderer image and clear other renderers.
        renderer.setImage(image);

        return renderer;
    }
}
