package Utils;

import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class Soup implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSoup(PlayerInteractEvent localPlayerInteractEvent)
    {
        Player player = localPlayerInteractEvent.getPlayer();
        Material material = player.getItemInHand().getType();

        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

        if (alphaPlayerPvP.getActualArena().getName().equalsIgnoreCase("Spawn"))
            return;

        if (material != Material.MUSHROOM_SOUP)
            return;

        if ((localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK ||
                localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR) &&
                player.getHealth() < player.getMaxHealth())
        {
            int restores = 7;
            localPlayerInteractEvent.setCancelled(true);

            if (player.getHealth() + restores <= player.getMaxHealth())
            {
                player.setHealth(player.getHealth() + restores);
            } else
                {
                player.setHealth(player.getMaxHealth());
            }
            player.setItemInHand(ItemStackManager.Create("Tigela", Arrays.asList(""), Material.BOWL, 1));
        }
    }
}
