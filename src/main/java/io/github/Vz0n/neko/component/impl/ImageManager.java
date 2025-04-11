package io.github.Vz0n.neko.component.impl;

import com.google.inject.Inject;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.component.NekoComponent;
import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.image.impl.NekosLifeProvider;
import io.github.Vz0n.neko.image.renderer.NekoRenderer;
import io.github.Vz0n.neko.util.HttpUtil;
import io.github.Vz0n.neko.util.StringUtils;

import java.awt.*;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Class for managing stored neko images, it saves images and loads it when requested,
 * i.e. when the server is started
 */
// TODO: Improve image handling and saving. This implementation seems too wholesome
public class ImageManager implements NekoComponent {

    private final Logger pluginLogger;
    private final ImageProvider nekoImageProvider;
    private final File cacheDir;

    @Inject
    public ImageManager(NekoFetcher plugin){
        this.pluginLogger = plugin.getLogger();
        this.cacheDir = new File(plugin.getDataFolder(), "storage");
        this.nekoImageProvider = new NekosLifeProvider(); // It's the only available provider for now

        if(!this.cacheDir.exists()) cacheDir.mkdir();
    }

    /**
     * Saves a map to the disk
     *
     * @param img The BufferedImage object to save
     * @param filename URL of the image
     */
    private void saveImage(BufferedImage img, String filename, String extension){

        // Compute checksum
        String hash = StringUtils.computeHash(filename);

        File cacheFile = new File(cacheDir, hash + extension);

        try {
            if(!cacheFile.createNewFile()){
                throw new IOException("Error creating cache file. Check permissions");
            }

            FileOutputStream out = new FileOutputStream(cacheFile);

            ImageIO.write(img, extension.substring(1), out);
            out.close();

        } catch(IOException e){
            pluginLogger.warning("Failed to save map with hash " + hash + " into cache!");
            pluginLogger.warning("Exception was: " + e.getMessage());

        }

    }

    /**
     * Gets a map from the disk
     * @param filename The image URL
     * @return An Optional with a NekoRenderer. If an error occurs while fetching the image,
     * the Optional will be empty.
     */
    private Optional<NekoRenderer> getRendererWithImage(String filename, String extension){

        String hash = StringUtils.computeHash(filename);
        File cacheFile = new File(cacheDir, hash + extension);

        try {
            FileInputStream stream = new FileInputStream(cacheFile);
            NekoRenderer renderer = new NekoRenderer();

            BufferedImage bufferedImage = ImageIO.read(stream);
            renderer.setImage(bufferedImage);

            stream.close();
            return Optional.of(renderer);
        } catch (IOException e) {
            pluginLogger.warning("Failed to get image with hash " + hash + " from cache!");
            pluginLogger.warning("Exception was: " + e.getMessage());

            return Optional.empty();
        }
    }


    /**
     * Gets a random cat girl image from the image provider.
     *
     * @return A NekoRenderer with the image.
    **/
    public Optional<NekoRenderer> getRendererWithImage(){

        URL url = nekoImageProvider.getNewImageURL();

        String[] pathParts = url.getFile().split("/");
        // The last part of the filepath it's the filename
        String filename = pathParts[pathParts.length - 1];

        int dotPos = filename.indexOf('.');
        String extension = filename.substring(dotPos);

        if(isImageInStorage(filename, extension)){
            return this.getRendererWithImage(filename, extension);
        }

        // The image is not in cache, so let's get it
        try {
            InputStream stream = HttpUtil.getImage(url);

            if(stream == null) throw new IOException("An error happened while fetching an image link. Provider is up?");

            BufferedImage img = ImageIO.read(stream);

            // Resize image
            Graphics graph = img.createGraphics();
            graph.drawImage(img, 0, 0, 128, 128, null);
            graph.dispose();

            NekoRenderer renderer = new NekoRenderer();
            renderer.setImage(img);

            // Save the image on the storage
            this.saveImage(img, filename, extension);

            return Optional.of(renderer);
        } catch(IOException | URISyntaxException e){
            pluginLogger.warning("Failed to get a map from the image provider!");
            pluginLogger.warning("Exception was: " + e.getMessage());

            return Optional.empty();
        }
    }

    private boolean isImageInStorage(String filename, String extension){
        String hash = StringUtils.computeHash(filename);
        File cacheFile = new File(cacheDir, hash + extension);

        return cacheFile.exists();
    }
}
