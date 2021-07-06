package Events.Kit.FirstKit;

import Domain.Player.AlphaPlayerPvP;
import Events.Kit.GUIs.FirstKitGUI;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class OpenFirstInventoryKit implements Listener {
    @EventHandler
    public void openFirstInventoryKits(org.bukkit.event.player.PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        ItemStack item = player.getItemInHand();

        if (alphaPlayerPvP.inSpawn()) {

            if (e.getAction().name().contains("RIGHT") && item.hasItemMeta()
                    && item.getItemMeta().getDisplayName().equalsIgnoreCase("§aSelecionar Kit"))
            {
                e.setCancelled(true);
                FirstKitGUI.create(alphaPlayerPvP);
            }
        }
    }
}
