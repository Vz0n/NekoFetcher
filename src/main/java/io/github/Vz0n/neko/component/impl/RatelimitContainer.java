package io.github.Vz0n.neko.component.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.inject.Inject;
import org.bukkit.plugin.Plugin;

import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.component.NekoComponent;

// Class for storing player's cooldown/limit time to use /nget.
public class RatelimitContainer implements NekoComponent {
    
    // The value of the HashMap is a two elements array where
    // First element is the amount of uses of the command.
    // Second element is the timestamp when PlayerUses > nMaxUses was reached, if happened.
    private HashMap<UUID, long[]> cooldownStore = new HashMap<>();
    private int cooldownTime;
    private int maxUses;

    @Inject
    public RatelimitContainer(NekoFetcher plugin){
        this.startCleanTask(plugin);

        // Multiply by 1000 as the config is in seconds, we are working
        // with milliseconds.
        this.cooldownTime = plugin.getNekoConfig().getCooldownTime() * 1000;
        this.maxUses = plugin.getNekoConfig().getImageRate();
    }

    public void addUse(UUID player){

        long[] playerLimit = this.cooldownStore.getOrDefault(player, new long[]{0L, 0L});
        playerLimit[0] = playerLimit[0] + 1L;

        // Player reached the max uses, so we assign a timestamp to identify 
        // when the limit was reached.
        if(playerLimit[0] >= maxUses) playerLimit[1] = System.currentTimeMillis();

        this.cooldownStore.put(player, playerLimit);
    }

    private void removeCooldown(UUID player){
        this.cooldownStore.remove(player);
    }

    public void clearPlayers(){
        this.cooldownStore.clear();
    }

    // Returns the remaining time in seconds for the next use, in case that the player is rate limited.
    public long getRateLimit(UUID player){

        long[] playerLimit = cooldownStore.get(player);
    
        if(playerLimit == null) return 0L;

        return playerLimit[0] < maxUses
                ? 0L : (cooldownTime - (System.currentTimeMillis() - playerLimit[1])) / 1000;
    }

    private void startCleanTask(Plugin plugin){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Map.Entry<UUID, long[]> entry : this.cooldownStore.entrySet()){
                long timestamp = entry.getValue()[1];

                if(getRateLimit(entry.getKey()) <= 0 && timestamp != 0L){
                    this.removeCooldown(entry.getKey());
                }
            }
        }, 20L, 60L);
    }

}