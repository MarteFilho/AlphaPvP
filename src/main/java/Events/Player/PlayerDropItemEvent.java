package Events.Player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDropItemEvent implements Listener
{
    @EventHandler
    public void onPlayerDropItem(org.bukkit.event.player.PlayerDropItemEvent e)
    {
        Material material = e.getItemDrop().getItemStack().getType();
        String materialString = material.toString();
        if (!materialString.contains("MUSHROOM") && material != Material.BOWL && material != Material.GOLDEN_APPLE
            && material != Material.ENDER_PEARL && material != Material.EXP_BOTTLE && material != Material.GLASS_BOTTLE)
            e.setCancelled(true);
    }
}

