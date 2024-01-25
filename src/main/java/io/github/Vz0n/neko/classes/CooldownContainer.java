package io.github.Vz0n.neko.classes;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

// Class for storing player's cooldown time to use /nget.
// Does not need additional checks because HashMap's nature.
public class CooldownContainer {
    
    private HashMap<UUID, Integer> timeStore = new HashMap<>();
    private int cooldownTime;

    public CooldownContainer(Plugin instance, int cooldownTime){
        // Multiply by 60 as the config is in seconds and we are working
        // with milliseconds.
        this.cooldownTime = cooldownTime * 60;
        this.startCleanTask(instance);
    }

    public void setCooldown(UUID player, int time){
        this.timeStore.put(player, time);
    }

    private void removeCooldown(UUID player){
        this.timeStore.remove(player);
    }

    public void clearPlayers(){
        this.timeStore.clear();
    }

    public boolean isInCooldown(UUID player){
        long playerTime = timeStore.getOrDefault(player, 0);

        return !isPastTime(playerTime);    
    }

    private boolean isPastTime(long time){
        return ((System.nanoTime() / 60) - time) > this.cooldownTime;
    }

    private void startCleanTask(Plugin plugin){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Entry<UUID, Integer> entry : timeStore.entrySet()){
                 plugin.getLogger().info("[DEBUG] Executing clean task...");

                 if(this.isPastTime(cooldownTime))
                    plugin.getLogger().info("Removing one player from the cooldown container");
                    this.removeCooldown(entry.getKey());
            }
        }, 20L, 60L);
    }

}
