package io.github.Vz0n.neko.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.inject.Inject;

import io.github.Vz0n.neko.classes.NekoConfiguration;

public class NekoCommand implements CommandExecutor {

    private NekoConfiguration config;

    @Inject
    public NekoCommand(NekoConfiguration config){
        this.config = config;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

       sender.sendMessage(config.getDecoratedMessage("neko_command_header"));
       return true;
    }
}
