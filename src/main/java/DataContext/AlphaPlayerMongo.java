package DataContext;

import Domain.Arenas.Arena;
import Domain.Arenas.ArenaStatus;
import Domain.Kit;
import Domain.KitsDefault.None;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.DataContext.MongoDb;
import alphanetwork.core.Domain.Ranks.Member;
import alphanetwork.core.Domain.Ranks.Rank;
import alphanetwork.core.Domain.Tags.Tag;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Updates.set;


public class AlphaPlayerMongo
{
    public static MongoDb mongoDb = null;

    public AlphaPlayerMongo()
    {
        if (mongoDb == null)
            mongoDb = MongoDb.connect("alpha_players");
    }

    public AlphaPlayerPvP Create(Player player)
    {

        AlphaPlayerPvP alphaPlayerPvP = new AlphaPlayerPvP(player);

        Document newPlayerModel = new Document();

        newPlayerModel.append("playerId", player.getUniqueId());
        newPlayerModel.append("name", player.getName());
        newPlayerModel.append("rank", "Membro");
        newPlayerModel.append("kills", 0);
        newPlayerModel.append("deaths", 0);
        newPlayerModel.append("killstreak", 0);
        newPlayerModel.append("coins", 0);

        Member playerRank = new Member();
        Document rank = pojoToDocRank(playerRank);
        newPlayerModel.append("rank", rank);

        List<Kit> listKits = new ArrayList<>();
        listKits.add(new None());
        List<Document> kits = pojoToDoc(listKits);
        newPlayerModel.append("kitspurchased", kits);

        List<Document> arenaStatus = pojoToDocArenaStatus(new LinkedList<>(alphaPlayerPvP.getArenaStatus()));
        newPlayerModel.append("arenasStatus", arenaStatus);

        mongoDb.Create(newPlayerModel);

        return alphaPlayerPvP;
    }

    public AlphaPlayerPvP Find(Player player)
    {
        Document playerModelDb = mongoDb.Find("playerId", player.getUniqueId());

        if (playerModelDb == null)
            return null;


        AlphaPlayerPvP alphaPlayerPvP = new AlphaPlayerPvP(player);
        alphaPlayerPvP.setKills(playerModelDb.getInteger("kills"));
        alphaPlayerPvP.setDeaths(playerModelDb.getInteger("deaths"));
        alphaPlayerPvP.setKillStreak(playerModelDb.getInteger("killstreak"));
        alphaPlayerPvP.setCoins(playerModelDb.getInteger("coins"));

        LinkedList<Kit> allKits = Kit.GetAllKits();
        List<Document> playerKitsPurchasedDocuments;

        playerKitsPurchasedDocuments = (List<Document>)playerModelDb.get("kitspurchased");
        LinkedList<Kit> newPlayerKitsPurchased = new LinkedList<>();

        for (Document document: playerKitsPurchasedDocuments
             )
        {
            String kitName = document.getString("name");
            for (Kit kit: allKits
            )
            {
                if (kit.GetName().equalsIgnoreCase(kitName))
                    newPlayerKitsPurchased.add(kit);
            }
        }

        alphaPlayerPvP.setKitsPurchased(newPlayerKitsPurchased);

        Document playerRankDocument = (Document) playerModelDb.get("rank");
        String playerRankName = playerRankDocument.getString("name");
        Bukkit.getConsoleSender().sendMessage(playerRankName);

        LinkedList<Rank> allRanks = Rank.getAllRanks();
        for ( Rank rank : allRanks
             ) {
            if (playerRankName.equalsIgnoreCase(rank.getName()))
                alphaPlayerPvP.setRank(rank);
        }
        Bukkit.getConsoleSender().sendMessage(alphaPlayerPvP.getRank().getName());
        LinkedList<ArenaStatus> playerArenaStatus = new LinkedList<>();
        List<Document> playerArenasStatus = (List<Document>) playerModelDb.get("arenasStatus");
        for (Document document : playerArenasStatus
             )
        {
            String arenaName = document.getString("name");
            for (Arena arena : Arena.getAllArenas()
                 )
            {
                if (arenaName.equalsIgnoreCase(arena.getName()))
                {
                    ArenaStatus arenaStatus = new ArenaStatus(alphaPlayerPvP, arena, document.getInteger("playerKills"),
                            document.getInteger("playerKillStreak"), document.getInteger("playerDeaths"));

                    playerArenaStatus.add(arenaStatus);
                }
            }
        }

        alphaPlayerPvP.setArenaStatus(playerArenaStatus);


        return alphaPlayerPvP;
    }

    public void UpdatePlayer(AlphaPlayerPvP alphaPlayerPvP)
    {
        LinkedList<Bson> updateOperations = new LinkedList<>();
        Bukkit.getConsoleSender().sendMessage(alphaPlayerPvP.getRank().getName());
        Bson updateName = set("name", alphaPlayerPvP.getName());
        Bson updateRank = set("rank", pojoToDocRank(alphaPlayerPvP.getRank()));
        Bson updateCoins = set("coins", alphaPlayerPvP.getCoins());
        Bson updateKitsPurchased = set("kitspurchased", pojoToDoc(alphaPlayerPvP.getKitsPurchased()));
        Bson updateArenasStatus= set("arenasStatus", pojoToDocArenaStatus(alphaPlayerPvP.getArenaStatus()));

        updateOperations.add(updateName);
        updateOperations.add(updateRank);
        updateOperations.add(updateCoins);
        updateOperations.add(updateKitsPurchased);
        updateOperations.add(updateArenasStatus);

        mongoDb.Update("playerId", alphaPlayerPvP.getUUID(), updateOperations);
    }


    public static void DisableLogs()
    {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver.cluster" );
        mongoLogger.setLevel(Level.SEVERE);
    }

    private static List<Document> pojoToDoc(List<Kit> kits)
    {
        List<Document> listKits = new ArrayList();
        for (Kit kit : kits
             )
        {
            Document doc = new Document();

            doc.put("name", kit.GetName());
            doc.put("description", kit.GetDescription());
            doc.put("price", kit.GetPrice());
            listKits.add(doc);
        }
        return listKits;
    }

    private static List<Document> pojoToDocArenaStatus(List<ArenaStatus> arenaStatus)
    {
        List<Document> listArenaStatus = new ArrayList();
        for (ArenaStatus localArenaStatus : arenaStatus
        )
        {
            Document doc = new Document();

            doc.put("name", localArenaStatus.getArena().getName());
            doc.put("playerKills", localArenaStatus.getKills());
            doc.put("playerDeaths", localArenaStatus.getDeaths());
            doc.put("playerKillStreak", localArenaStatus.getKillStreak());

            listArenaStatus.add(doc);
        }
        return listArenaStatus;
    }

    private static Document pojoToDocRank(Rank rank)
    {
        Document rankDoc = new Document();

        rankDoc.put("name", rank.getName());

        LinkedList<Document> tags = new LinkedList<Document>();
        for (Tag tag : rank.getTags()
             ) {
            Document tagDoc = new Document();
            tagDoc.put("prefix", tag.getPrefix());
            tagDoc.put("suffix", tag.getSuffix());
            tags.add(tagDoc);
        }


        rankDoc.put("tags", tags);

        return rankDoc;
    }
}
