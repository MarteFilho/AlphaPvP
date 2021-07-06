package Utils.Kit;

import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import martefilho.alphapvp.AlphaPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatLog implements Listener
{
    public static ConcurrentHashMap<Player, UUID> concurrentMapOfCombat = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Player, BukkitTask> concurrentMapOfTask = new ConcurrentHashMap<>();


    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if (e.isCancelled())
            return;
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player entity = (Player)e.getEntity();
            Player damager = (Player)e.getDamager();
            addCombat(entity, damager);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombatLogout(PlayerQuitEvent e)
    {
        Player playerLogout = e.getPlayer();
        AlphaPlayerPvP playerLogoutModel = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerLogout.getUniqueId());

        if (onCombat(playerLogout))
        {
            UUID lastPlayerCombatUUID = getLastCombat(playerLogout);
            Player lastPlayerCombat = Bukkit.getPlayer(lastPlayerCombatUUID);
            AlphaPlayerPvP lastPlayerCombatModel = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(lastPlayerCombatUUID);

            lastPlayerCombat.sendMessage("Você matou " + playerLogout.getName());
            lastPlayerCombatModel.UpdateScoreBoard();

            playerLogout.sendMessage("Você morreu cabaço!");
            playerLogoutModel.UpdateScoreBoard();
        }
    }

    public static void addCombat(final Player playerToAdd, Player toCombat)
    {

        concurrentMapOfCombat.put(playerToAdd, toCombat.getUniqueId());
        concurrentMapOfTask.put(playerToAdd, (new BukkitRunnable() {
            public void run()
            {
                removeCombat(playerToAdd);
            }
        }).runTaskLater(AlphaPvP.getPlugin(), 160L));
    }

    public static void removeCombat(Player playerToRemove)
    {
        concurrentMapOfCombat.remove(playerToRemove);
        if (concurrentMapOfTask.containsKey(playerToRemove))
        {
            if (concurrentMapOfTask.get(playerToRemove) != null)
                concurrentMapOfTask.get(playerToRemove).cancel();
            concurrentMapOfTask.remove(playerToRemove);
        }
    }

    public static boolean onCombat(Player playerToCheck)
    {
        return concurrentMapOfCombat.containsKey(playerToCheck);
    }

    public static UUID getLastCombat(Player playerToCheck)
    {
        return concurrentMapOfCombat.get(playerToCheck);
    }
}
