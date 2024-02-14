package io.github.Vz0n.neko.classes;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

// Class for storing player's cooldown/limit time to use /nget.
public class RatelimitContainer {
    
    // The value of the HashMap is a Long array where:
    // Value 0 is the timestamp when PlayerUses > nMaxUses was reached, if happened.
    // Value 1 is the amount of uses of the command.
    private HashMap<UUID, long[]> cooldownStore = new HashMap<>();
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
            this.cooldownStore.put(player, new long[]{0L, 1L});
            return;
        }

        long[] playerLimit = this.cooldownStore.get(player);
        playerLimit[1] = playerLimit[1] + 1L;

        // Player reached the max uses, so we assign a timestamp to identify 
        // when the limit was reached.
        if(playerLimit[1] == nMaxUses) playerLimit[0] = System.currentTimeMillis();
    
        this.cooldownStore.replace(player, playerLimit);
    }

    private void removeCooldown(UUID player){
        this.cooldownStore.remove(player);
    }

    public void clearPlayers(){
        this.cooldownStore.clear();
    }

    // Returns the remaining time in seconds, in case that the player is ratelimited.
    public long getRatelimit(UUID player){

        long[] playerLimit = cooldownStore.getOrDefault(player, new long[]{0L, 0L});
    
        if(playerLimit[1] < nMaxUses) return 0L;

        return (cooldownTime - (System.currentTimeMillis() - playerLimit[0])) / 1000;    
    }

    private boolean isPastTime(long time){
        return (System.currentTimeMillis() - time) > this.cooldownTime;
    }

    private void startCleanTask(Plugin plugin){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Entry<UUID, long[]> entry : cooldownStore.entrySet()){
                long time = entry.getValue()[0];

                if(time != 0L && this.isPastTime(time)){
                    this.removeCooldown(entry.getKey());
                }
            }
        }, 20L, 60L);
    }

}
