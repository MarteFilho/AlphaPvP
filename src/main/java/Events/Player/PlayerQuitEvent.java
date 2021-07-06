package Events.Player;

import DataContext.AlphaPlayerMongo;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener
{
    @EventHandler
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent e)
    {
        e.setQuitMessage(null);
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

        if (alphaPlayerPvP == null)
            return;

        AlphaPlayerMongo alphaPlayerMongo = new AlphaPlayerMongo();
        alphaPlayerMongo.UpdatePlayer(alphaPlayerPvP);

        AlphaPlayerManager.removePlayer(player.getUniqueId());
    }
}
