package serialization;

import models.exception.DataValidationException;
import models.guild.Faction;
import models.guild.Guild;
import models.guild.Region;
import models.player.Player;

import java.io.*;
import java.util.List;

public class ExtentManager {
    private final static String EXTENT_FILE_PATH = "src/main/java/serialization/save.ser";

    private static void saveExtent() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(EXTENT_FILE_PATH))) {
            output.writeObject(Player.getPlayersExtent());
            output.writeObject(Guild.getGuildExtent());
            output.writeObject(Faction.getFactionExtent());
            output.writeObject(Region.getRegionExtent());
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
            throw new DataValidationException("nothing to load");
        }
        loadExtent();
    }

}
