package Events.Player;

import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageEvent implements Listener
{

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
            if (alphaPlayerPvP.inSpawn())
                e.setCancelled(true);

        }
    }
}
