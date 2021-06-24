package serialization;

import models.exception.DataValidationException;
import models.functionalities.ApplicationForm;
import models.functionalities.events.EventImpl;
import models.functionalities.shop.Boost;
import models.functionalities.shop.Shop;
import models.guild.*;
import models.player.Player;
import models.player.equipment.Equipment;

import java.io.*;
import java.util.List;

public class ExtentManager {
    private final static String EXTENT_FILE_PATH = "src/main/java/serialization/save.ser";

    private static void saveExtent() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EXTENT_FILE_PATH))) {
            output.writeObject(Player.getPlayersExtent());
            output.writeObject(Guild.getGuildsExtent());
            output.writeObject(Faction.getFactionsExtent());
            output.writeObject(Region.getRegionsExtent());
            output.writeObject(ApplicationForm.getApplicationFormsExtent());
            output.writeObject(GuildAchievement.getGuildAchievementsExtent());
            output.writeObject(Log.getLogsExtent());
            output.writeObject(EventImpl.getEventsExtent());
            output.writeObject(Equipment.getEquipmentsExtent());
            output.writeObject(Shop.getShopsExtent());
            output.writeObject(Boost.getBoostsExtent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadExtent() {
        try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(EXTENT_FILE_PATH))) {
            Player.setPlayersExtent((List<Player>) output.readObject());
            Guild.setGuildExtent((List<Guild>) output.readObject());
            Faction.setFactionExtent((List<Faction>) output.readObject());
            Region.setRegionExtent((List<Region>) output.readObject());
            ApplicationForm.setApplicationFormExtent((List<ApplicationForm>) output.readObject());
            GuildAchievement.setApplicationFormExtent((List<GuildAchievement>) output.readObject());
            Log.setLogExtent((List<Log>) output.readObject());
            EventImpl.setEventExtent((List<EventImpl>) output.readObject());
            Equipment.setEquipmentExtent((List<Equipment>) output.readObject());
            Shop.setLogExtent((List<Shop>) output.readObject());
            Boost.setBoostsExtent((List<Boost>) output.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        File myObj = new File(EXTENT_FILE_PATH);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveExtent();
    }

    public static void load() {
        File myObj = new File(EXTENT_FILE_PATH);
        if (!myObj.exists()) {
            DefaultDataManager.CreateDefaultData();
            System.out.println("nothing to load");
            return;
        }
        loadExtent();
    }

}
