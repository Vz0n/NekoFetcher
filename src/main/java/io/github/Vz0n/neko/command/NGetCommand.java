package io.github.Vz0n.neko.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.github.Vz0n.neko.image.ImageProvider;

public class NGetCommand implements CommandExecutor {

    private ImageProvider provider;

    public NGetCommand(ImageProvider provider){
        this.provider = provider;
    }

    public boolean onCommand(CommandSender executor, Command cmd, String label, String[] args){

        if(executor instanceof Player p){
              p.sendMessage("Getting your image...");
              ItemStack map = new ItemStack(Material.FILLED_MAP);
              MapMeta mapMeta = (MapMeta) map.getItemMeta();
              mapMeta.setMapView(provider.getImage(mapMeta.getMapView()));
              map.setItemMeta(mapMeta);
              p.sendMessage("Fap with your neko UwU!");
        }

        executor.sendMessage("You can't use this from console");
        return false;
    }
    
}
