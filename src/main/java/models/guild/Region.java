package models.guild;

import models.exception.DataValidationException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Region implements Serializable {

    private static List<Region> regionsExtent = new ArrayList<>();

    private String regionName;

    private Map<Integer, Faction> factions = new HashMap<>();

    public Region(String regionName) {
        this.regionName = regionName;

        regionsExtent.add(this);
    }

    /**
     * Region Name
     */
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Faction Association
     */
    public Map<Integer, Faction> getFactions() {
        return Collections.unmodifiableMap(factions);
    }

    public void addFaction(Faction f) {
        if (f == null) {
            throw new DataValidationException("Faction is required!");
        }
        if (this.factions.containsKey(f.getRegionId())) {
            return;
        }
        this.factions.put(f.getRegionId(), f);
        f.setRegion(this);
    }

    public void removeFaction(Faction f) {
        if (f == null) {
            throw new DataValidationException("Faction is required!");
        }
        if (!this.factions.containsKey(f.getRegionId())) {
            return;
        }
        this.factions.remove(f.getRegionId());
        f.setRegion(null);
    }

    public Set<Faction> getFactionList() {
        if (factions != null) {
            return new HashSet<>(factions.values());
        }
        return new HashSet<>();
    }

    public Faction findFactionById(Integer id) {
        return this.factions.get(id);
    }

    public Set<Faction> getFactionListByLeader(String leaderName) {
        return getFactionList()
                .stream()
                .filter(x -> x.getLeader().getLeaderName().equals(leaderName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Faction Extension
     */
    public static List<Region> getRegionExtent() {
        return Collections.unmodifiableList(regionsExtent);
    }

    public static void setRegionExtent(List<Region> regionExtent) {
        if (regionExtent == null) {
            throw new DataValidationException("extent cannot be null");
        }
        Region.regionsExtent = regionExtent;
    }

    //testing
    public static void clearExtension() {
        regionsExtent.clear();
    }

    /**
     * Utilities
     */
    @Override
    public String toString() {
        return String.format("Name: %s", getRegionName());
    }

}
