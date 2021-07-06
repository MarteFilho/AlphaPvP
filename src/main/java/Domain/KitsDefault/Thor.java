package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.Cooldown;
import alphanetwork.core.Domain.AlphaPlayerManager;
import martefilho.alphapvp.AlphaPvP;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class Thor extends Kit implements Listener
{
    public Thor() {
        super("Thor", "Envie raios e destrua seus inimigos!", Material.WOOD_AXE, Material.WOOD_AXE, 5000, 10);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return true;
    }

    @EventHandler
    public void ExecuteHability(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        if (alphaPlayerPvP.verifyKit(this) && player.getItemInHand().getType() == GetItem())
        {
            e.setCancelled(true);

            if (Utils.Kit.Cooldown.hasCooldown(player))
            {
                player.sendMessage("Aguarde para usar seu kit novamente!");
                return;
            }

            Cooldown.addCooldown(player, (int) GetCooldown());

            final Block blockToThor = player.getTargetBlock((HashSet<Byte>) null, 100).getRelative(BlockFace.UP);
            player.getWorld().strikeLightning(blockToThor.getLocation());
            (new BukkitRunnable()
            {
                public void run()
                {
                    if (blockToThor.getType() == Material.FIRE)
                        blockToThor.setType(Material.AIR);
                }
            }).runTaskLater(AlphaPvP.getPlugin(), 25L);
        }
    }
}
