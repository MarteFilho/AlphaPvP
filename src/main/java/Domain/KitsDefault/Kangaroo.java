package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.Cooldown;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Kangaroo extends Kit implements Listener
{
    public static final List<UUID> listOfKangaroo = new ArrayList<>();


    public Kangaroo() {
        super("Kangaroo", "Como um foguete, se movimente mais rápido!",
                Material.FIREWORK, Material.FIREWORK, 5000, 4);
    }

    @Override
    public void RemoveAbility() { }

    @Override
    public boolean HasItem() {
        return true;
    }

    public boolean hasItem() {
        return true;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        if (alphaPlayerPvP.verifyKit(this) && e.getAction()
                != Action.PHYSICAL && player.getItemInHand().getType() == this.GetItem())
        {
            e.setCancelled(true);
            if (listOfKangaroo.contains(player.getUniqueId()))
                return;

            if (Cooldown.hasCooldown(player))
            {
                player.setVelocity(new Vector(0.0D, -1.0D, 0.0D));
                player.sendMessage("Aguarde para usar seu kit novamente!");
                return;
            }

            Vector localVector = player.getEyeLocation().getDirection();
            if (player.isSneaking()) {
                localVector = localVector.multiply(2.5F).setY(0.5F);
            } else {
                localVector = localVector.multiply(0.5F).setY(1.0F);
            }
            player.setFallDistance(-1.0F);
            player.setVelocity(localVector);
            listOfKangaroo.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        Player player = (Player)e.getEntity();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        if (!alphaPlayerPvP.verifyKit(this))
            return;
        if (e.isCancelled())
            return;
        if (!(e.getDamager() instanceof Player))
            return;
        Cooldown.addCooldown(player, (int) GetCooldown());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (listOfKangaroo.contains(e.getPlayer().getUniqueId()) && (
                e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN)
                        .getType() != Material.AIR || e.getPlayer().isOnGround()))
            listOfKangaroo.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
            if (alphaPlayerPvP.verifyKit(this) && e.getCause() == EntityDamageEvent.DamageCause.FALL &&
                    e.getDamage() > 12.0D)
                e.setDamage(10.0D);
        }
    }
}
