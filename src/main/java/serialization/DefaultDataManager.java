package serialization;

import models.guild.Faction;
import models.guild.Guild;
import models.guild.Leader;
import models.guild.Region;
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

        System.out.printf("created players: \n%s \n%s \n%s\n%n", player1, player2, player3);

        Region region1 = new Region("Jinno");
        Region region2 = new Region("Easter Kingdoms");
        Region region3 = new Region("DunMorogg");

        System.out.printf("created regions: \n%s \n%s \n%s\n%n", region1, region2, region3);

        Leader leader1 = new Leader("Thanatos");
        Leader leader2 = new Leader("Azmodan");
        Leader leader3 = new Leader("Gucio");

        System.out.printf("created leaders: \n%s \n%s \n%s\n%n", leader1, leader2, leader3);

        Faction faction1 = new Faction("Inquisition", leader1, region1);
        Faction faction2 = new Faction("ITNGuardians", leader2, region2);
        Faction faction3 = new Faction("UsualPlayers", leader3, region3);

        System.out.printf("created factions: \n%s \n%s \n%s\n%n", faction1, faction2, faction3);

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
        guild1.addGuildMember(player8);
        guild3.addGuildMember(player9);
        guild6.addGuildMember(player10);
        guild6.addGuildMember(player11);
        guild6.addGuildMember(player12);

        System.out.printf("created guilds: \n%s \n%s \n%s\n%n", guild1, guild2, guild3, guild4, guild5, guild6);

    }

}
