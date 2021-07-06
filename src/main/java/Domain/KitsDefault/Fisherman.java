package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class Fisherman extends Kit implements Listener
{

    public Fisherman() {
        super("Fisherman", "Pesque os inimigos com sua vara!", Material.FISHING_ROD, Material.FISHING_ROD, 3000, 5);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return true;
    }

    @EventHandler
    public void ExecuteHability(PlayerFishEvent e)
    {
        Player player = e.getPlayer();
        Entity coughedEntity = e.getCaught();
        Block localBlockOfHook = e.getHook().getLocation().getBlock();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        if (coughedEntity != null && coughedEntity != localBlockOfHook && alphaPlayerPvP.verifyKit(this))
            coughedEntity.teleport(player.getPlayer().getLocation());
    }
}
