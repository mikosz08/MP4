package models.functionalities.shop;

import models.exception.DataValidationException;
import models.player.Player;

import java.io.Serializable;
import java.util.*;

public class Shop implements Serializable {

    private static List<Shop> shopsExtent = new ArrayList<>();

    //Collection of players using the shop
    private Set<Player> playerList = new HashSet<>();

    //Collection of Boost shop have to offer
    private Set<Boost> boosts = new HashSet<>();


    public Shop() {
        shopsExtent.add(this);
    }

    public static Shop mainShop = new Shop();

    /**
     * Boost Association
     */
    public Set<Boost> getBoosts() {
        return Collections.unmodifiableSet(boosts);
    }

    public void addBoost(Boost boost) {
        if (boost == null) {
            throw new DataValidationException("Boost is null!");
        }
        if (this.boosts.contains(boost)) {
            return;
        }
        this.boosts.add(boost);
        boost.setShop(this);
    }

    public void removeBoost(Boost boost) {
        if (!this.boosts.contains(boost)) {
            return;
        }

        this.boosts.remove(boost);
        boost.setShop(null);
    }

    /**
     * Player Association
     */
    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(playerList);
    }

    public void addPlayer(Player player) {
        if (player == null) {
            throw new DataValidationException("Player is null!");
        }
        if (this.playerList.contains(player)) {
            return;
        }
        this.playerList.add(player);
        player.setShop(this);
    }

    public void removePlayer(Player player) {
        if (!this.playerList.contains(player)) {
            return;
        }

        this.playerList.remove(player);
        player.setShop(null);
    }

    /**
     * Shop Extension
     */
    public static List<Shop> getShopsExtent() {
        return Collections.unmodifiableList(shopsExtent);
    }

    public static void setLogExtent(List<Shop> shopsExtent) {
        if (shopsExtent == null) {
            throw new DataValidationException("Extent cannot be null!");
        }
        Shop.shopsExtent = shopsExtent;
    }

    //testing
    /*public static void clearExtension() {
        shopsExtent.clear();
    }*/

}
