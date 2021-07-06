package Events.Default;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RepairItemsHandler implements Listener
{
    @EventHandler
    public void onInteractRepair(PlayerInteractEvent e) {
        Player localPlayer = e.getPlayer();
        ItemStack localItemStack = localPlayer.getItemInHand();
        if ((e.getAction() == Action.LEFT_CLICK_AIR ||
                e.getAction() == Action.LEFT_CLICK_BLOCK ||
                e.getAction() == Action.RIGHT_CLICK_AIR ||
                e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
                localItemStack.getType() == Material.FISHING_ROD ||
                        localItemStack.getType().toString().contains("_SWORD") ||
                        localItemStack.getType().toString().contains("_AXE")))
            localItemStack.setDurability((short)0);
    }
}
