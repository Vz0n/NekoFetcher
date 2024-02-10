package io.github.Vz0n.neko.classes;


import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NekoConfiguration {

    private FileConfiguration configFile;
    private final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public NekoConfiguration(Plugin plugin){
        // Verify if the main config file exists before setting our
        // FileConfiguration to the plugin's one.
        File dataFolder = plugin.getDataFolder();
        File configFile = new File(dataFolder, "config.yml");

        if(!dataFolder.exists()) dataFolder.mkdir();
        if(!configFile.exists()) plugin.saveDefaultConfig();

        this.configFile = plugin.getConfig();
    }

    public Component getDecoratedMessage(String key, String token, String value){
        ConfigurationSection messagesSection = configFile.getConfigurationSection("messages");
        String message = messagesSection.getString(key, "Missing message key: " + key)
        .replace("%prefix%", configFile.getString("prefix"));

        // Look for variables
        // TODO: make this more dynamic
        if(token != null){
            message = message.replace(token, value);
        }

        return MINI_MESSAGE.deserialize(message);
    }

    public Component getDecoratedMessage(String key){
        
        return this.getDecoratedMessage(key, null, null);
    }

    public int getCooldownTime(){

        return configFile.getInt("cooldown.options.time", 520);
    }

    public int getImageLimit(){

        return configFile.getInt("cooldown.options.imageLimit", 3);
    }

    public boolean isCooldownEnabled(){
        
        return configFile.getBoolean("cooldown.enabled", true);
    }
    
}
