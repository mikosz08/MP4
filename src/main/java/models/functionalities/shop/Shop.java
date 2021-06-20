package models.functionalities.shop;

import models.exception.DataValidationException;
import models.functionalities.events.EventImpl;
import models.guild.GuildAchievement;
import models.guild.Log;
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

    /**
     * Boost Association
     */
    public Set<Boost> getBoosts() {
        return Collections.unmodifiableSet(boosts);
    }

    public void addBoost(Boost newBoost) {
        if (newBoost == null) {
            throw new DataValidationException("Boost is required!");
        }
        if (newBoost.getShop() != this) {
            throw new DataValidationException("Boost is not related to this Guild!");
        }
        this.boosts.add(newBoost);
    }

    public void removeBoost(Boost boost) {
        if (!this.boosts.contains(boost)) {
            return;
        }
        this.boosts.remove(boost);
        boost.deleteShop();
    }

    public void deleteBoost() {
        List<Boost> copiedBoosts = new ArrayList<>(this.boosts);
        for (Boost b : copiedBoosts) {
            b.deleteShop();
        }
    }

    /**
     * Player Association
     */
    public Set<Player> getPlayerList() {
        return Collections.unmodifiableSet(playerList);
    }

    public void addPlayer(Player newPlayer) {
        if (newPlayer == null) {
            throw new DataValidationException("Player is required!");
        }
        if (newPlayer.getShop() != this) {
            throw new DataValidationException("Player is not related to this Guild!");
        }
        this.playerList.add(newPlayer);
    }

    public void removePlayer(Player player) {
        if (!this.playerList.contains(player)) {
            return;
        }
        this.playerList.remove(player);
        player.deleteShop();
    }

    public void deletePlayers() {
        List<Player> copiedPlayerList = new ArrayList<>(this.playerList);
        for (Player p : copiedPlayerList) {
            p.deleteShop();
        }
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
    public static void clearExtension() {
        shopsExtent.clear();
    }

}
