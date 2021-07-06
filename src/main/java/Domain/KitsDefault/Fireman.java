package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Fireman extends Kit implements Listener
{

    public Fireman() {
        super("Fireman", "Não receba dado de fogo!", Material.WATER_BUCKET, null, 3000, 0);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return false;
    }

    @EventHandler
    public void ExecuteHability(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player player = (Player)e.getEntity();
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
            if (alphaPlayerPvP.verifyKit(this) && (
                    e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                            e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                            e.getCause() == EntityDamageEvent.DamageCause.LAVA))
                e.setCancelled(true);
        }
    }
}
