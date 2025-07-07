package io.github.Vz0n.neko.component.impl;

import com.google.inject.Inject;
import io.github.Vz0n.neko.NekoFetcher;
import io.github.Vz0n.neko.component.NekoComponent;

import net.milkbowl.vault.economy.Economy;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class EconomyManager implements NekoComponent {

    private Economy eco;

    @Inject
    public EconomyManager(NekoFetcher plugin){
        this.eco = plugin.getServer().getServicesManager()
                                     .getRegistration(Economy.class).getProvider();
    }

    public boolean withdrawMoney(Player p, double money){
        EconomyResponse resp = eco.withdrawPlayer(p, money);

        return resp.transactionSuccess();
    }

    public boolean hasMoney(Player p, double money){
        return eco.has(p, money);
    }

    public String formatBalance(double money){
        return eco.format(money);
    }
}
