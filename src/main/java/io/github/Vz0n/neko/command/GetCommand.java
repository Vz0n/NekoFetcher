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
import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.classes.NekoConfiguration;

import java.util.Optional;

public class GetCommand implements CommandExecutor {

    private NekoFetcher plugin;

    @Inject
    public GetCommand(NekoFetcher instance){
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(!(sender instanceof Player player)){
            sender.sendMessage("You must be a player to use that command.");
            return false;
        }

        NekoConfiguration config = plugin.getNekoConfig();
        ImageProvider provider = plugin.getImageProvider();

        if(config.isCooldownEnabled()){
            long time = plugin.getUsesContainer().getRateLimit(player.getUniqueId());

            if(time > 0L){
                player.sendActionBar(config.getDecoratedMessage("error_ratelimited", 
                                     "%time%", String.valueOf(time)));
                return false;
            }

            plugin.getUsesContainer().addUse(player.getUniqueId());
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.FILLED_MAP){
            player.sendMessage(config.getDecoratedMessage("filled_map_not_in_hand"));
            return false;
        }
            
        player.sendMessage(config.getDecoratedMessage("getting_neko_image"));

        MapMeta mapMeta = (MapMeta) item.getItemMeta();
        Optional<MapView> view = provider.getImage(mapMeta.getMapView());

        if(view.isEmpty()){
            player.sendMessage(config.getDecoratedMessage("error_getting_image"));
            return false;
        }

        mapMeta.setMapView(view.get());
        item.setItemMeta(mapMeta);
        player.sendMessage(config.getDecoratedMessage("success_getting_image"));
        
        return true;

    }
    
}
