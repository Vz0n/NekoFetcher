package io.github.Vz0n.neko.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.inject.Inject;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.NekoConfiguration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent.Builder;

public class NekoCommand implements CommandExecutor {

    private NekoFetcher plugin;

    @Inject
    public NekoCommand(NekoFetcher instance){
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

       String argument = args.length > 0 ? args[0] : "help";
       NekoConfiguration config = plugin.getNekoConfig();

       Builder message = Component.text();

       switch(argument){
           case "version" -> message.append(config.getDecoratedMessage(
                   "neko_command_version",
                   "%version%",
                   plugin.getDescription().getVersion()
           ));
           case "reload" -> {
               plugin.reloadNekoConfig();
               message.append(config.getDecoratedMessage("neko_command_reload"));
           }
           case "help" -> message.append(config.getDecoratedMessage("neko_command_help"));
       }
       
       sender.sendMessage(message.build());

       return true;
    }
}
