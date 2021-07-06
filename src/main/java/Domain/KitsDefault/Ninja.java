package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.Cooldown;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import java.util.HashMap;

public class Ninja extends Kit {

    public static HashMap<Player, Player> hashMapNinja = new HashMap<>();

    public Ninja() {
        super("Ninja", "Teletransporte para o seu oponente!", Material.EMERALD, null, 30000, 5.00);
    }



    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return false;
    }


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent localEntityDamageByEntityEvent) {

        if (localEntityDamageByEntityEvent.getEntity() instanceof Player &&
                localEntityDamageByEntityEvent.getDamager() instanceof Player) {
            Player entityDamaged = (Player)localEntityDamageByEntityEvent.getEntity();
            Player entityDamager = (Player)localEntityDamageByEntityEvent.getDamager();

            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(entityDamager.getUniqueId());

            if (alphaPlayerPvP.verifyKit(this))
            {
                if (localEntityDamageByEntityEvent.isCancelled())
                    return;

                hashMapNinja.put(entityDamager, entityDamaged);
            }
        }
    }


    @EventHandler
    public void ExecuteAbility(PlayerToggleSneakEvent localPlayerToggleSneakEvent)
    {
        Player player = localPlayerToggleSneakEvent.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

        if (alphaPlayerPvP.verifyKit(this) && player.isSneaking())
        {
            if (Cooldown.hasCooldown(player)) {
                player.sendMessage("Aguarde para usar seu kit novamente!");
                return;
            }


            if (this.hashMapNinja.containsKey(player))
            {
                Player playerToTeleport = this.hashMapNinja.get(player);
                AlphaPlayerPvP alphaPlayerPvPToTeleport = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerToTeleport.getUniqueId());
                if (playerToTeleport.isOnline())
                {
                    if (player.getLocation().distance(playerToTeleport.getLocation()) < 60 &&
                        !alphaPlayerPvPToTeleport.getActualArena().getName().equalsIgnoreCase("Spawn"))
                    {
                        Cooldown.addCooldown(player, (int) GetCooldown());
                        player.sendMessage("Você usou o ninja.");
                        player.teleport(playerToTeleport.getLocation());
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.5F, 1.5F);
                    }
                    else if (alphaPlayerPvPToTeleport.getActualArena().getName().equalsIgnoreCase("Spawn"))
                        {
                            this.hashMapNinja.remove(player);
                        }
                    else
                        {
                        player.sendMessage("muito longe de você");
                    }
                }
                else
                    {
                    player.sendMessage("maluco saiu do servidor.");
                    this.hashMapNinja.remove(player);
                }
            }
        }
    }
}
