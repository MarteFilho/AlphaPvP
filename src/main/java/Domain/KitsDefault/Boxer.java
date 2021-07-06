package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Boxer extends Kit implements Listener {

    public Boxer() {
        super("Boxer", "Cause mais dano e receba menos dano!", Material.STONE_SWORD, null, 5000, 0);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return false;
    }

    @EventHandler
    public void ExecuteHability(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player playerDamaged = (Player)e.getEntity();
            Player playerDamager = (Player)e.getDamager();

            AlphaPlayerPvP alphaPlayerPvPDamaged = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerDamaged.getUniqueId());
            AlphaPlayerPvP alphaPlayerPvPDamager = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerDamager.getUniqueId());

            double damage = e.getDamage();

            if (alphaPlayerPvPDamager.verifyKit(this))
                damage += 0.25D;

            if (alphaPlayerPvPDamager.verifyKit(this))
                damage -= 0.25D;

            e.setDamage(damage);
        }
    }
}
