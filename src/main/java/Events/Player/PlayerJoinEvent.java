package Events.Player;

import DataContext.AlphaPlayerMongo;
import Domain.Arenas.ArenaManager;
import Domain.Arenas.Defaults.Arena;
import Domain.Player.AlphaPlayerPvP;
import Utils.TabList;
import Utils.TitleMessage;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class PlayerJoinEvent implements Listener
{
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        AlphaPlayerMongo alphaPlayerMongo = new AlphaPlayerMongo();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        alphaPlayerPvP.setPlayer(player);

        if (alphaPlayerMongo.Find(player) == null)
        {
            alphaPlayerMongo.Create(player);
        }

        alphaPlayerPvP.clearPlayer();
        alphaPlayerPvP.setTag();
        alphaPlayerPvP.CreateDefaultItems();
        alphaPlayerPvP.CreateScoreBoard();
        alphaPlayerPvP.setInSpawn(true);
        alphaPlayerPvP.setActualArena(new Arena());

        event.setJoinMessage("Bem-vindo, " + event.getPlayer().getName() + "!");

        String header = "\n§bAlpha - PvP\n";
        String footer = "\n§eWebSite: §bwww.alphamc.com\n §eLoja: §bwww.alphamc.com/loja\n§eDiscord: §bwww.alphamc.com/discord";
        TabList.Create(player, header, footer);
        TitleMessage.Create(player, "§b§lALPHA", "§eSeja bem-vindo!");

        player.teleport(ArenaManager.GetLocation("Spawn"));
    }
}
