package models.player;

import models.functionalities.ApplicationForm;
import models.functionalities.events.EventImpl;
import models.functionalities.events.EventType;
import models.functionalities.shop.Boost;
import models.functionalities.shop.BoostType;
import models.functionalities.shop.Shop;
import models.guild.*;
import models.player.equipment.*;
import serialization.ExtentManager;

import java.time.LocalDate;
import java.util.EnumSet;

public class PlayerTest {
    //REMOVE THIS

    public static void main(String[] args) {
        //serializationTest();
        //testApplicationForm();
        //testGuildAchievement();
        // testEventCreation();
        //testEQ();
    }

/*    private static void testEQ() {

        Stats stats = new Stats(1, 2, 3);

        Equipment common = new CommonEQ("Mieczyk", 1, stats);
        System.out.println(common);
        Equipment legendary = new LegendaryEQ("Legendarny Mieczyk",
                10, stats, 1, 2, 0);
        System.out.println(legendary);
        Equipment magic = new MagicEqImpl("Magiczny Miezcyk", 5, stats, 15);
        System.out.println(magic);
        Equipment magicLegendary = new MagicLegendaryEQ("Super Mieczyk", 25, stats, 5, 5, 5, 6);
        System.out.println(magicLegendary);

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

        ApplicationForm ap = new ApplicationForm("message123", guild1, player1);

        GuildAchievement ga = new GuildAchievement("achiName", "reqs", guild1);

        Log l = new Log("hello", guild1);

        EventImpl event = new EventImpl(guild1, player1, "name", LocalDate.now(), LocalDate.now(),
                1, 2, 3, EnumSet.of(EventType.GOLD_EVENT));

        Equipment eq = new MagicEqImpl("name", 1,
                new Stats(1, 2, 3), 1);

        Shop s = player1.getShop();

        Boost b = new Boost("boost", guild1, s, LocalDate.now(), 5, BoostType.REGION_BOOST);


        System.out.println("P:" + Player.getPlayersExtent().size());
        System.out.println("G:" + Guild.getGuildsExtent().size());
        System.out.println("R:" + Region.getRegionsExtent().size());
        System.out.println("F:" + Faction.getFactionsExtent().size());
        System.out.println("AF:" + ApplicationForm.getApplicationFormsExtent().size());
        System.out.println("GA:" + GuildAchievement.getGuildAchievementsExtent().size());
        System.out.println("L:" + Log.getLogsExtent().size());
        System.out.println("E:" + EventImpl.getEventsExtent().size());
        System.out.println("EQ:" + Equipment.getEquipmentsExtent().size());
        System.out.println("SH:" + Shop.getShopsExtent().size());
        System.out.println("B:" + Boost.getBoostsExtent().size());
        ExtentManager.save();

        Player.clearExtension();
        Guild.clearExtension();
        Region.clearExtension();
        Faction.clearExtension();
        ApplicationForm.clearExtension();
        GuildAchievement.clearExtension();
        Log.clearExtension();
        EventImpl.clearExtension();
        Equipment.clearExtension();
        Shop.clearExtension();
        Boost.clearExtension();

        System.out.println("\nP:" + Player.getPlayersExtent().size());
        System.out.println("G:" + Guild.getGuildsExtent().size());
        System.out.println("R:" + Region.getRegionsExtent().size());
        System.out.println("F:" + Faction.getFactionsExtent().size());
        System.out.println("AF:" + ApplicationForm.getApplicationFormsExtent().size());
        System.out.println("GA:" + GuildAchievement.getGuildAchievementsExtent().size());
        System.out.println("L:" + Log.getLogsExtent().size());
        System.out.println("E:" + EventImpl.getEventsExtent().size());
        System.out.println("EQ:" + Equipment.getEquipmentsExtent().size());
        System.out.println("SH:" + Shop.getShopsExtent().size());
        System.out.println("B:" + Boost.getBoostsExtent().size());

        ExtentManager.load();
        System.out.println("\nP:" + Player.getPlayersExtent().size());
        System.out.println("G:" + Guild.getGuildsExtent().size());
        System.out.println("R:" + Region.getRegionsExtent().size());
        System.out.println("F:" + Faction.getFactionsExtent().size());
        System.out.println("AF:" + ApplicationForm.getApplicationFormsExtent().size());
        System.out.println("GA:" + GuildAchievement.getGuildAchievementsExtent().size());
        System.out.println("L:" + Log.getLogsExtent().size());
        System.out.println("E:" + EventImpl.getEventsExtent().size());
        System.out.println("EQ:" + Equipment.getEquipmentsExtent().size());
        System.out.println("SH:" + Shop.getShopsExtent().size());
        System.out.println("B:" + Boost.getBoostsExtent().size());
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


    }*/

}
