package io.github.Vz0n.neko.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextFormatter {

    // Don't need to add special settings so
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component returnAsComponent(String text){
        
        return MINI_MESSAGE.deserialize(text);

    }
    
}
