package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class Magma extends Kit implements Listener {

    public Magma() {
        super("Magma", "Coloque fogo em seus oponentes ao ataca-los", Material.LAVA_BUCKET, null, 20000, 0);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return false;
    }

    @EventHandler
    public void ExecuteHability(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player playerDamaged = (Player)e.getEntity();
            Player playerDamager = (Player)e.getDamager();
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerDamager.getUniqueId());

            if (alphaPlayerPvP.verifyKit(this))
            {
                int integerOfChance = (new Random()).nextInt(100);

                if (integerOfChance > 0 && integerOfChance < 31)
                    playerDamaged.setFireTicks(150);
            }
        }
    }
}
