package models.guild;

import models.exception.DataValidationException;
import models.player.Player;

import java.io.Serializable;
import java.util.*;

public class Faction implements Serializable {

    private static List<Faction> factionsExtent = new ArrayList<>();

    private static int factionId = 0;

    private String factionName;
    private Leader leader;
    private Region region;
    private int regionId;

    private Set<Guild> guilds = new HashSet<>();

    /**
     * Faction Constructor
     */
    public Faction(String factionName, Leader leader, Region region) {
        setFactionName(factionName);
        setLeader(leader);
        setRegionId(++factionId);
        setRegion(region);

        factionsExtent.add(this);
    }

    /**
     * Faction Name
     */
    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        if (factionName == null) {
            throw new DataValidationException("Faction must have a name!");
        }
        this.factionName = factionName;
    }

    public int getRegionId() {
        return regionId;
    }

    /**
     * Faction Leader
     */
    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader newLeader) {
        if (this.leader == newLeader) {
            return;
        }
        if (newLeader == null) {
            throw new DataValidationException("New Leader cannot be null!");
        } else {
            this.leader = newLeader;
        }
    }

    /**
     * Faction Region
     */
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region newRegion) {
        if (this.region == newRegion) {
            return;
        }
        if (this.region != null) {

            Region tmpRegion = this.region;
            this.region = null;
            tmpRegion.removeFaction(this);

            if (newRegion != null) {
                this.region = newRegion;
                newRegion.addFaction(this);
            }

        } else {
            this.region = newRegion;
            newRegion.addFaction(this);

        }
        //this.region = newRegion;
    }

    public void setRegionId(int regionId) {
        if (regionId <= 0) {
            throw new DataValidationException("ID is required!");
        }
        if (this.region != null) {
            Region tmpRegion = this.region;
            this.region.removeFaction(this);
            this.regionId = regionId;
            tmpRegion.addFaction(this);
        } else {
            this.regionId = regionId;
        }
        //this.regionId = regionId;
    }

    /**
     * Faction Extension
     */
    public static List<Faction> getFactionsExtent() {
        return Collections.unmodifiableList(factionsExtent);
    }

    public static void setFactionExtent(List<Faction> factionExtent) {
        if (factionExtent == null) {
            throw new DataValidationException("extent cannot be null");
        }
        Faction.factionsExtent = factionExtent;
    }

    //testing
    /*public static void clearExtension() {
        factionsExtent.clear();
    }*/

    /**
     * Guild Association
     */
    public Set<Guild> getGuilds() {
        return Collections.unmodifiableSet(guilds);
    }

    public void addGuild(Guild guild) {
        if (guild == null) {
            throw new DataValidationException("Guild is null!");
        }
        if (this.guilds.contains(guild)) {
            return;
        }
        this.guilds.add(guild);
        guild.setFaction(this);
    }

    public void removeGuild(Guild guild) {
        if (!this.guilds.contains(guild)) {
            return;
        }
        this.guilds.remove(guild);
        guild.setFaction(null);
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Name: %s, Leader: %s, Region: %s",
                getFactionName(), getLeader().getLeaderName(), getRegion().getRegionName());
    }

}
