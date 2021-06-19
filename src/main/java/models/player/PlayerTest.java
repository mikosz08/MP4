package models.player;

import models.functionalities.ApplicationForm;
import models.functionalities.events.EventImpl;
import models.functionalities.events.EventType;
import models.guild.*;
import serialization.ExtentManager;

import java.time.LocalDate;
import java.util.EnumSet;

public class PlayerTest {
    //REMOVE THIS

    public static void main(String[] args) {

        //serializationTest();
        //testApplicationForm();
        //testGuildAchievement();
        testEventCreation();
    }

    private static void testEventCreation() {

        Player player1 = new Player("mikosz01", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player2 = new Player("mikosz02", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player3 = new Player("mikosz03", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");

        Leader leader1 = new Leader("Thrash");
        Leader leader2 = new Leader("Azmodan");
        Region region1 = new Region("Jinno");

        Faction faction1 = new Faction("Firelands", leader1, region1);
        Faction faction2 = new Faction("Kalimdor", leader2, region1);

        player1.becomeGuildFounder();
        player2.becomeGuildFounder();

        Guild guild1 = new Guild("CNO", player1, faction1);

        EventImpl event = new EventImpl(guild1, player1, "event", LocalDate.now(), LocalDate.now(),
                1, 1, 1, EnumSet.of(EventType.EXP_EVENT, EventType.GOLD_EVENT, EventType.REP_EVENT));

        System.out.println(event);

    }

    private static void serializationTest() {
        Player player1 = new Player("mikosz01", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player2 = new Player("mikosz02", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player3 = new Player("mikosz03", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");

        Leader leader1 = new Leader("Thrash");
        Leader leader2 = new Leader("Azmodan");
        Region region1 = new Region("Jinno");

        Faction faction1 = new Faction("Firelands", leader1, region1);
        Faction faction2 = new Faction("Kalimdor", leader2, region1);

        player1.becomeGuildFounder();
        player2.becomeGuildFounder();

        Guild guild1 = new Guild("CNO", player1, faction1);
        guild1.addGuildMember(player3);
        Guild guild2 = new Guild("CNO", player1, faction1);
        Guild guild3 = new Guild("CTN", player2, faction2);

        System.out.println(guild1 == guild1);
        System.out.println(guild1.equals(guild1));

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

    private static void testApplicationForm() {

        Player player1 = new Player("mikosz01", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player2 = new Player("mikosz02", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");

        Leader leader2 = new Leader("Azmodan");
        Region region1 = new Region("Jinno");

        Faction faction2 = new Faction("Kalimdor", leader2, region1);

        player1.becomeGuildFounder();

        Guild guild1 = new Guild("CNO", player1, faction2);

        ApplicationForm ap = new ApplicationForm("message", guild1, player1);
        System.out.println(ap);


    }

    private static void testGuildAchievement() {

        Player player1 = new Player("mikosz01", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");
        Player player2 = new Player("mikosz02", 15, new PlayerLocation("Here", 15, 41), PlayerType.GUILD_MEMBER, "Warrior");

        Leader leader2 = new Leader("Azmodan");
        Region region1 = new Region("Jinno");

        Faction faction2 = new Faction("Kalimdor", leader2, region1);

        player1.becomeGuildFounder();

        Guild guild1 = new Guild("CNO", player1, faction2);

        GuildAchievement ga = new GuildAchievement("First achievement!", "gain your first achievement", guild1);
        System.out.println(ga);

        Log log = new Log("message", guild1);
        System.out.println(log);


    }

}
