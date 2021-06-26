package serialization;

import models.guild.*;
import models.player.Player;
import models.player.PlayerLocation;

public abstract class DefaultDataManager {

    public static void CreateDefaultData() {

        Player player = new Player("s18333", 23, new PlayerLocation("Void", 123, -123), "Student");


        Player player1 = new Player("Albert3113", 15, new PlayerLocation("Orgrimmar", 35, -51), "Warrior");
        Player player2 = new Player("Gustaw08", 23, new PlayerLocation("Budyenk A", 17, 46), "Archer");
        Player player3 = new Player("AnnKa", 45, new PlayerLocation("Lost City", -45, 51), "Warlock");
        Player player4 = new Player("MagicAndrew", 60, new PlayerLocation("Torghast", 46, 17), "Druid");
        Player player5 = new Player("Pavl0", 23, new PlayerLocation("Marsalkovska", -55, -551), "Seaker");
        Player player6 = new Player("MarcoSolo", 99, new PlayerLocation("Kosyckowa", 8, 6), "Mage");

        Player player7 = new Player("Falko", 75, new PlayerLocation("Auberdine", 35, -51), "Mage");
        Player player8 = new Player("Asiup", 53, new PlayerLocation("Dalaran", 17, 46), "Monk");
        Player player9 = new Player("Oddray", 43, new PlayerLocation("Dolanaar", -45, 51), "Warlock");
        Player player10 = new Player("Herilland", 60, new PlayerLocation("Goldshire", 46, 17), "Druid");
        Player player11 = new Player("Jenean", 22, new PlayerLocation("Kharanos", -55, -551), "Demon Hunter");
        Player player12 = new Player("Pedro", 93, new PlayerLocation("Lakeshire", 8, 6), "Death Knight");

        Player player13 = new Player("FastFizzle", 75, new PlayerLocation("Dragon Isles", 55, -76), "Archer");
        Player player14 = new Player("Billownozzle", 43, new PlayerLocation("GM Island", 57, 84), "Monk");
        Player player15 = new Player("Wobblecub", 33, new PlayerLocation("Fendris Isle", -74, 67), "Warlock");
        Player player16 = new Player("Pumplight", 43, new PlayerLocation("Kul Tiras", 54, 62), "Warlock");
        Player player17 = new Player("Rustpatch", 27, new PlayerLocation("Janeiro's Point", -56, -551), "Knight");
        Player player18 = new Player("Castcoil", 53, new PlayerLocation("Echo Isles", 35, 65), "Mage");

        //System.out.printf("created players: \n%s \n%s \n%s\n%n", player1, player2, player3);

        Region region1 = new Region("Jinno");
        Region region2 = new Region("Easter Kingdoms");
        Region region3 = new Region("DunMorogg");

        //System.out.printf("created regions: \n%s \n%s \n%s\n%n", region1, region2, region3);

        Leader leader1 = new Leader("Thanatos");
        Leader leader2 = new Leader("Azmodan");
        Leader leader3 = new Leader("Gucio");

        //System.out.printf("created leaders: \n%s \n%s \n%s\n%n", leader1, leader2, leader3);

        Faction faction1 = new Faction("Marauders", leader1, region1);
        Faction faction2 = new Faction("Syndicate", leader2, region2);
        Faction faction3 = new Faction("Alliance", leader3, region3);

        //System.out.printf("created factions: \n%s \n%s \n%s\n%n", faction1, faction2, faction3);

        player1.becomeGuildFounder();
        player2.becomeGuildFounder();
        player3.becomeGuildFounder();
        player4.becomeGuildFounder();
        player5.becomeGuildFounder();
        player6.becomeGuildFounder();

        Guild guild1 = new Guild("Very_Strong_Guild", player1, faction2);
        Guild guild2 = new Guild("Not_So_Strong_Guild", player2, faction1);
        Guild guild3 = new Guild("Gidlia_Española", player3, faction3);
        Guild guild4 = new Guild("Starzy_Spokojni", player4, faction3);
        Guild guild5 = new Guild("Tibia_Klasztor", player5, faction2);
        Guild guild6 = new Guild("ForSigmar", player6, faction1);

        guild1.addGuildMember(player7);
            player7.becomeGuildOfficer();
        guild1.addGuildMember(player8);

        guild2.addGuildMember(player13);

        guild3.addGuildMember(player9);

        guild6.addGuildMember(player10);
        guild6.addGuildMember(player11);
        guild6.addGuildMember(player12);
        guild6.addGuildMember(player14);
            player14.becomeGuildOfficer();
        guild6.addGuildMember(player15);
        guild6.addGuildMember(player16);
        guild6.addGuildMember(player17);
        guild6.addGuildMember(player18);


        for(Player p : Player.getPlayersExtent()){
            p.setReputationAwarded((float) (Math.random() * 1234.0f));
        }
        player1.setMessageOfTheDay("que?");
        player2.setMessageOfTheDay("ktoś coś dungeon dziś?");
        player3.setMessageOfTheDay("WTB 4x [Dark Cristal]");
        player5.setMessageOfTheDay("i can't join you tomorrow, sorry ;/");
        player7.setMessageOfTheDay("panda3");
        player8.setMessageOfTheDay("public static void what");
        player10.setMessageOfTheDay(":D");
        player11.setMessageOfTheDay("WTS Fire Mieczyk +13 4/6 + mats");
        player12.setMessageOfTheDay("Any1 for [Wild Hogger slain 0/1]??");
        player18.setMessageOfTheDay("Hello guys!");

        GuildAchievement achi1 = new GuildAchievement("Wealhty!", "Earn 5.000 Gold as a Guild Member", guild6);
        GuildAchievement achi2 = new GuildAchievement("True Developer!", "Deafeat Tomashvodan!", guild6);
        GuildAchievement achi3 = new GuildAchievement("Well Known!", "Your Guild have to earn 1.000 RP", guild6);

        //System.out.printf("created guilds: \n%s \n%s \n%s\n%n", guild1, guild2, guild3, guild4, guild5, guild6);
        System.out.println("[Custom Data Has Been Created]");
        ExtentManager.save();
    }

}
