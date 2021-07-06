package Events.Kit.SecondKit;

import Domain.Player.AlphaPlayerPvP;
import Events.Kit.GUIs.FirstKitGUI;
import Events.Kit.GUIs.SecondKitGUI;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class OpenSecondInventoryKit implements Listener {
    @EventHandler
    public void openSecondInventoryKits(org.bukkit.event.player.PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        ItemStack item = player.getItemInHand();

        if (alphaPlayerPvP.inSpawn()) {

            if (e.getAction().name().contains("RIGHT") && item.hasItemMeta() &&
                    item.getItemMeta().getDisplayName().equalsIgnoreCase("§aSelecionar Kit 2"))
            {
                e.setCancelled(true);
                SecondKitGUI.create(alphaPlayerPvP);
            }
        }
    }
}
