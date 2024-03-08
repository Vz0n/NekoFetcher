package io.github.Vz0n.neko.classes;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

// Class for storing player's cooldown/limit time to use /nget.
public class RatelimitContainer {
    
    // The value of the HashMap is a Long array where
    // Value 0 is the amount of uses of the command.
    // Value 1 is the timestamp when PlayerUses > nMaxUses was reached, if happened.
    private HashMap<UUID, long[]> cooldownStore = new HashMap<>();
    private int cooldownTime;
    private int maxUses;

    public RatelimitContainer(Plugin instance, NekoConfiguration config){
        // Multiply by 1000 as the config is in seconds, we are working
        // with milliseconds.
        this.cooldownTime = config.getCooldownTime() * 1000;
        this.maxUses = config.getImageLimit();

        this.startCleanTask(instance);
    }

    public void addUse(UUID player){

        if(!this.cooldownStore.containsKey(player)){
            this.cooldownStore.put(player, new long[]{1L, 0L});
            return;
        }

        long[] playerLimit = this.cooldownStore.get(player);
        playerLimit[0] = playerLimit[0] + 1L;

        // Player reached the max uses, so we assign a timestamp to identify 
        // when the limit was reached.
        if(playerLimit[0] >= maxUses) playerLimit[1] = System.currentTimeMillis();
    
        this.cooldownStore.replace(player, playerLimit);
    }

    private void removeCooldown(UUID player){
        this.cooldownStore.remove(player);
    }

    public void clearPlayers(){
        this.cooldownStore.clear();
    }

    // Returns the remaining time in seconds, in case that the player is rate limited.
    public long getRateLimit(UUID player){

        long[] playerLimit = cooldownStore.get(player);
    
        if(playerLimit == null) return 0L;

        return playerLimit[0] < maxUses
                ? 0L : (cooldownTime - (System.currentTimeMillis() - playerLimit[1])) / 1000;
    }

    private boolean isPastTime(long time){
        return (System.currentTimeMillis() - time) > this.cooldownTime;
    }

    private void startCleanTask(Plugin plugin){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Entry<UUID, long[]> entry : cooldownStore.entrySet()){
                long[] playerEntry = entry.getValue();

                if(playerEntry[0] >= maxUses
                        && this.isPastTime(playerEntry[1])) this.removeCooldown(entry.getKey());
            }
        }, 20L, 60L);
    }

}
