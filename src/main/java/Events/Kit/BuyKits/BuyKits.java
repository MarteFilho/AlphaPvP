package Events.Kit.BuyKits;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.LinkedList;

public class BuyKits implements Listener
{
    @EventHandler
    public void buyKits(InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player)
        {
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            String inventoryName = e.getInventory().getName();

            if (inventoryName.equalsIgnoreCase("Comprar kits") && clickedItem != null)
            {
                e.setCancelled(true);
                AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
                alphaPlayerPvP.setPlayer(player);

                if (!alphaPlayerPvP.inSpawn())
                {
                    player.sendMessage("Você só pode comprar kits no spawn!");
                    player.closeInventory();
                    return;
                }

                LinkedList<Kit> playerKitsPurchased;
                playerKitsPurchased = alphaPlayerPvP.getKitsPurchased();

                for (Kit localKit : Kit.GetAllKits())
                {
                    if (clickedItem.getType() == localKit.GetItemMenu())
                    {
                        if (playerKitsPurchased.contains(localKit))
                        {
                            player.closeInventory();
                            player.sendMessage("Você já comprou esse kit!");
                            break;
                        }

                        if (alphaPlayerPvP.getCoins().compareTo(localKit.GetPrice()) < 0)
                        {
                            player.closeInventory();
                            player.sendMessage("Você não possuí dinheiro suficiente para comprar esse kit!");
                            break;
                        }

                        playerKitsPurchased.add(localKit);
                        player.closeInventory();

                        alphaPlayerPvP.setCoins(alphaPlayerPvP.getCoins() - localKit.GetPrice());
                        alphaPlayerPvP.UpdateScoreBoard();
                        player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 10, 2);
                        break;
                    }
                }
                alphaPlayerPvP.setKitsPurchased(playerKitsPurchased);
            }
        }

    }
}
