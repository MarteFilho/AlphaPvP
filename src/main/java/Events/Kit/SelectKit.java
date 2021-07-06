package Events.Kit;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.TitleMessage;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SelectKit implements Listener
{

    @EventHandler
    public void selectKits(InventoryClickEvent e)
    {
        if (e.getWhoClicked() instanceof Player)
        {
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            String localInventoryName = e.getInventory().getName();
            if (localInventoryName.equalsIgnoreCase("Selecionar kit")
                    || localInventoryName.equalsIgnoreCase("Selecionar kit 2") && clickedItem != null)
            {
                e.setCancelled(true);
                AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

                if (!alphaPlayerPvP.inSpawn())
                {
                    player.sendMessage("Você só pode selecionar kits no spawn!");
                    player.closeInventory();
                    return;
                }

                if (clickedItem.getType() == alphaPlayerPvP.getFirstKit().GetItemMenu()
                        || clickedItem.getType() == alphaPlayerPvP.getSecondKit().GetItemMenu())
                {
                    player.sendMessage("Você já selecionou esse kit!");
                    player.closeInventory();
                    return;
                }

                for (Kit localKit : Kit.GetAllKits())
                {
                    if (clickedItem.getType() == localKit.GetItemMenu()) {
                        player.closeInventory();

                        if (localInventoryName.equalsIgnoreCase("Selecionar kit"))
                            alphaPlayerPvP.setFirstKit(localKit);

                        if (localInventoryName.equalsIgnoreCase("Selecionar kit 2"))
                            alphaPlayerPvP.setSecondKit(localKit);

                        player.sendMessage("Você selecionou o kit " + localKit.GetName().toUpperCase());
                        TitleMessage.Create(player, "§a" + localKit.GetName(), "§fselecionado!");
                        player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 10, 2);
                        break;
                    }
                }
            }
        }

    }
}
