package io.github.Vz0n.neko.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import com.google.inject.Inject;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.github.Vz0n.neko.image.ImageProvider;
import io.github.Vz0n.neko.classes.CooldownContainer;
import io.github.Vz0n.neko.classes.NekoConfiguration;

public class GetCommand implements CommandExecutor {

    private NekoConfiguration config;
    private ImageProvider provider;
    private CooldownContainer cooldowns;

    @Inject
    public GetCommand(NekoConfiguration config, ImageProvider imgProvider, CooldownContainer container){
        this.config = config;
        this.provider = imgProvider;
        this.cooldowns = container;
    }

    public boolean onCommand(CommandSender executor, Command cmd, String label, String[] args){
    
        if(executor instanceof Player){

            // Need to do this because Java doesn't let me to use
            // new syntax with nested if
            Player p = (Player) executor;

            if(cooldowns.isInCooldown(p.getUniqueId())){
                p.sendMessage(config.getDecoratedMessage("error_ratelimited"));
                return false;
            }
    
            ItemStack item = p.getInventory().getItemInMainHand();

            if(item.getType() != Material.FILLED_MAP){
                p.sendMessage(config.getDecoratedMessage("filled_map_not_in_hand"));
                return false;
            }
            
            p.sendMessage(config.getDecoratedMessage("getting_neko_image"));

            MapMeta mapMeta = (MapMeta) item.getItemMeta();
            MapView view = provider.getImage(mapMeta.getMapView());

            if(view.equals(mapMeta.getMapView())){
                p.sendMessage(config.getDecoratedMessage("error_getting_image"));
                return false;
            }
              
            item.setItemMeta(mapMeta);
            p.sendMessage(config.getDecoratedMessage("success_getting_image"));
            return true;

        } else {

            executor.sendMessage("You must be a player to use that command.");
            return false;

        }

    }
    
}
