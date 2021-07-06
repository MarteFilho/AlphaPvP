package Events.Player;

import DataContext.AlphaPlayerMongo;
import Domain.Player.AlphaPlayerPvP;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLoginEvent implements Listener
{
    @EventHandler
    public void onAsyncPlayerPreLogin(org.bukkit.event.player.PlayerLoginEvent e)
    {
        AlphaPlayerMongo alphaPlayerMongo = new AlphaPlayerMongo();
        AlphaPlayerPvP alphaPlayerPvP = alphaPlayerMongo.Find(e.getPlayer());

        if (alphaPlayerPvP == null)
        {
            AlphaPlayerPvP newAlphaPlayerPvP = (new AlphaPlayerPvP(e.getPlayer()));
            newAlphaPlayerPvP.addInStore();
            return;
        }

        alphaPlayerPvP.addInStore();
    }
}
