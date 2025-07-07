package io.github.Vz0n.neko.command;

import io.github.Vz0n.neko.component.impl.EconomyManager;
import io.github.Vz0n.neko.component.impl.ImageManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import com.google.inject.Inject;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.NekoConfiguration;
import io.github.Vz0n.neko.image.renderer.NekoRenderer;
import io.github.Vz0n.neko.component.impl.RatelimitContainer;

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

        EconomyManager eco = (EconomyManager) plugin.getNekoComponent(EconomyManager.class);
        ImageManager manager = (ImageManager) plugin.getNekoComponent(ImageManager.class);
        RatelimitContainer usesContainer = (RatelimitContainer) plugin
                .getNekoComponent(RatelimitContainer.class);

        NekoConfiguration config = plugin.getNekoConfig();
        ItemStack item = player.getInventory().getItemInMainHand();
        double price = config.getImagePrice();

        if(eco != null && !eco.hasMoney(player, price)){
            player.sendMessage(config.getDecoratedMessage("no_money"));
            return false;
        }

        if(config.isSettingEnabled("cooldown", true)){
            long time = usesContainer.getRateLimit(player.getUniqueId());

            if(time > 0L){
                player.sendActionBar(config.getDecoratedMessage("error_ratelimited", 
                                     "%time%", String.valueOf(time)));
                return false;
            }
        }

        if(item.getType() != Material.FILLED_MAP){
            player.sendMessage(config.getDecoratedMessage("filled_map_not_in_hand"));
            return false;
        }

        MapView view = ((MapMeta) item.getItemMeta()).getMapView();
        player.sendMessage(config.getDecoratedMessage("getting_neko_image"));

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Optional<NekoRenderer> result = manager.getRendererWithImage();

            if(result.isEmpty()) {
                player.sendMessage(config.getDecoratedMessage("error_getting_image"));
                return;
            }

            if(eco != null) {
                 if(!eco.withdrawMoney(player, price)){
                     player.sendMessage(config.getDecoratedMessage(
                             "error_purchasing_image", "%money%", eco.formatBalance(price)));
                     return;
                 }

                 player.sendMessage(config.getDecoratedMessage("purchased_image", "%money%",
                         eco.formatBalance(price)));
            }

            NekoRenderer renderer = result.get();

            view.getRenderers().clear();
            view.addRenderer(renderer);
            view.setLocked(true);

            usesContainer.addUse(player.getUniqueId());
            player.sendMessage(config.getDecoratedMessage("success_getting_image"));
        });
        
        return true;

    }
    
}
