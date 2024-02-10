package io.github.Vz0n.neko.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.inject.Inject;

import io.github.Vz0n.neko.NekoFetcher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent.Builder;
import net.kyori.adventure.text.format.TextColor;

public class NekoCommand implements CommandExecutor {

    private NekoFetcher plugin;

    @Inject
    public NekoCommand(NekoFetcher instance){
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

       String argument = args.length > 0 ? args[0] : "version";
       Builder message = Component.text();

       message.append(plugin.getNekoConfig().getDecoratedMessage("neko_command_header"));
       message.appendNewline();
       switch(argument){
           case "version":
               message.append(Component.text("Version of the plugin: " + plugin.getPluginMeta().getVersion())
                             .color(TextColor.fromHexString("#532CF0")));
               break;
           case "reload":
               plugin.reloadNekoConfig();
               message.append(Component.text("Plugin configuration reloaded! Remember that some changes require a restart.")
                      .color(TextColor.fromHexString("#532CF0")));
               break;
           case "help":
               message.append(Component.text("The only commands of this plugin are this and /nget, bruh.")
                      .color(TextColor.fromHexString("#F71010")));
               break;
       }
       
       sender.sendMessage(message.build());

       return true;
    }
}
