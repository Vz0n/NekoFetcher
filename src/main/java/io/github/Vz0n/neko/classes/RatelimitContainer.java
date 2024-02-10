package io.github.Vz0n.neko.classes;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

// Class for storing player's cooldown time to use /nget.
public class RatelimitContainer {
    
    // The value of the key is a Integer array, where element 0 is the cooldown timestamp
    // and element 1 is the n of uses by the user identified by UUID.
    private HashMap<UUID, Long[]> cooldownStore = new HashMap<>();
    private int cooldownTime;
    private int nMaxUses;

    public RatelimitContainer(Plugin instance, int cooldownTime, int maxUses){
        // Multiply by 1000 as the config is in seconds and we are working
        // with milliseconds.
        this.cooldownTime = cooldownTime * 1000;
        this.nMaxUses = maxUses;
        this.startCleanTask(instance);
    }

    public void addUse(UUID player){

        if(!this.cooldownStore.containsKey(player)){
            this.cooldownStore.put(player, new Long[]{System.currentTimeMillis(), 1L});
            return;
        }

        Long[] times = this.cooldownStore.get(player);
        times[1] = times[1] + 1L;

        this.cooldownStore.replace(player, times);
    }

    private void removeCooldown(UUID player){
        this.cooldownStore.remove(player);
    }

    public void clearPlayers(){
        this.cooldownStore.clear();
    }

    // Returns the remaining time in seconds, in case that the player is ratelimited.
    public Long getRatelimit(UUID player){

        Long[] playerTime = cooldownStore.getOrDefault(player, new Long[]{0L, 0L});

        if(playerTime[1] <= nMaxUses) return 0L;

        return (cooldownTime - (System.currentTimeMillis() - playerTime[0])) / 1000;    
    }

    private boolean isPastTime(long time){
        return (System.currentTimeMillis() - time) > this.cooldownTime;
    }

    private void startCleanTask(Plugin plugin){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Entry<UUID, Long[]> entry : cooldownStore.entrySet()){
                 if(this.isPastTime(entry.getValue()[0])){
                    this.removeCooldown(entry.getKey());
                 }
            }
        }, 20L, 60L);
    }

}
