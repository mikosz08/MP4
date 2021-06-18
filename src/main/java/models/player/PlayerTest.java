package models.player;

import models.guild.Faction;
import models.guild.Guild;
import models.guild.Leader;
import models.guild.Region;
import serialization.ExtentManager;

public class PlayerTest {
    //REMOVE THIS

    public static void main(String[] args) {

        serializationTest();
    }

    private static void serializationTest() {
        Player player1 = new Player("mikosz01", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player2 = new Player("mikosz02", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");

        Leader leader1 = new Leader("Thrash");
        Leader leader2 = new Leader("Azmodan");
        Region region1 = new Region("Jinno");

        Faction faction1 = new Faction("Firelands", leader1, region1);
        Faction faction2 = new Faction("Kalimdor", leader2, region1);

        player1.becomeGuildFounder();
        Guild guild1 = new Guild("CNO", player1, faction1);

        System.out.println(Player.getPlayersExtent().size());
        System.out.println(Guild.getGuildExtent().size());
        System.out.println(Region.getRegionExtent().size());
        System.out.println(Faction.getFactionExtent().size());
        ExtentManager.save();

        Player.clearExtension();
        Guild.clearExtension();
        Region.clearExtension();
        Faction.clearExtension();

        System.out.println(Player.getPlayersExtent().size());
        System.out.println(Guild.getGuildExtent().size());
        System.out.println(Region.getRegionExtent().size());
        System.out.println(Faction.getFactionExtent().size());

        ExtentManager.load();
        System.out.println(Player.getPlayersExtent().size());
        System.out.println(Guild.getGuildExtent().size());
        System.out.println(Region.getRegionExtent().size());
        System.out.println(Faction.getFactionExtent().size());
    }

}
