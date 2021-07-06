package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.Cooldown;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Monk extends Kit implements Listener {


    public Monk() {
        super("Monk", "Desarme seu inimigo!", Material.BLAZE_ROD, Material.BLAZE_ROD, 30000, 10);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return true;
    }

    @EventHandler
    public void ExecuteHability(PlayerInteractEntityEvent e)
    {
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

        if (e.getRightClicked() instanceof Player)
        {
            Player localRightClickedPlayer = (Player)e.getRightClicked();
            if (player.getItemInHand().getType() == Material.BLAZE_ROD && alphaPlayerPvP.verifyKit(this))
            {

                if (Cooldown.hasCooldown(player))
                {
                    player.sendMessage("Aguarde para usar seu kit novamente!");
                    return;
                }

                Cooldown.addCooldown(player, (int) GetCooldown());
                int integerWhitRandomValue = (new Random()).nextInt(localRightClickedPlayer.getInventory().getSize() - 10 + 1 + 10);

                ItemStack randomItemStackOfInventory = localRightClickedPlayer.getInventory().getItem(integerWhitRandomValue);
                ItemStack newItemInHand = localRightClickedPlayer.getItemInHand();
                localRightClickedPlayer.setItemInHand(randomItemStackOfInventory);
                localRightClickedPlayer.getInventory().setItem(integerWhitRandomValue, newItemInHand);
            }
        }
    }
}
