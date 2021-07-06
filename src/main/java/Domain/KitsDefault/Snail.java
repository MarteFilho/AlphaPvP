package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Snail extends Kit implements Listener {
    public Snail() {
        super("Snail", "Deixe seus inimigos lentos!", Material.WEB, null, 50000, 0);
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
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerDamager.getUniqueId());
            if (alphaPlayerPvP.verifyKit(this))
            {
                int integerOfChance = (new Random()).nextInt(100);
                if (integerOfChance > 0 && integerOfChance < 31)
                    playerDamaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 1));
            }
        }
    }
}
