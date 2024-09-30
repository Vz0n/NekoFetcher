package io.github.Vz0n.neko;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class NekoConfiguration {

    private FileConfiguration configFile;
    private File configFileHandle;
    private final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public NekoConfiguration(NekoFetcher plugin){
        // Verify if the main config file exists before setting our
        // FileConfiguration to the plugin's one.
        File dataFolder = plugin.getDataFolder();
        this.configFileHandle = new File(dataFolder, "config.yml");

        if(!dataFolder.exists()) dataFolder.mkdir();
        if(!this.configFileHandle.exists()) plugin.saveDefaultConfig();

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

    public void reloadFromPlugin(Plugin plugin){
        plugin.reloadConfig();
        this.configFile = plugin.getConfig();
    }

    public Component getDecoratedMessage(String key){
        
        return this.getDecoratedMessage(key, null, null);
    }

    public boolean isSettingEnabled(String setting, boolean def){
        return this.configFile.getBoolean(setting + ".enabled", def);
    }

    public int getCooldownTime(){
        return configFile.getInt("cooldown.options.time", 520);
    }

    public double getImagePrice(){
        return configFile.getDouble("economy.map_price", 300.0);
    }

    public int getImageRate(){
        return configFile.getInt("cooldown.options.imageLimit", 3);
    }

}
