package io.github.Vz0n.neko.util;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NekoConfiguration {

    private FileConfiguration configFile;
    private final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public NekoConfiguration(FileConfiguration file){
        this.configFile = file;
    }

    public Component getDecoratedMessage(String key){
        ConfigurationSection messagesSection = configFile.getConfigurationSection("messages");

        return MINI_MESSAGE.deserialize(
            messagesSection.getString(key, "Missing message key: " + key)
        );
    }
    
}
