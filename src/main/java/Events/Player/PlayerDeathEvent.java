package Events.Player;

import Domain.Arenas.ArenaManager;
import Domain.Arenas.ArenaStatus;
import Domain.Player.AlphaPlayerPvP;
import Utils.Document;
import Utils.ForceRespawn;
import alphanetwork.core.Domain.AlphaPlayerManager;
import martefilho.alphapvp.AlphaPvP;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.LinkedList;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent e) {
        e.setDeathMessage(null);

        Player playerDeath = e.getEntity();
        AlphaPlayerPvP playerDeathModel = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerDeath.getUniqueId());

        Iterator<ItemStack> iterator = e.getDrops().iterator();
        while (iterator.hasNext()) {
            ItemStack item = iterator.next();
            if (item != null && !item.getType().equals(Material.AIR)) {
                if (item.getType().equals(Material.STONE_SWORD)
                        || item.getType().equals(playerDeathModel.getFirstKit().GetItem())
                        || item.getType().equals(playerDeathModel.getSecondKit().GetItem()))
                    iterator.remove();
            }
        }
        if (e.getEntity() instanceof Player) {
            if (playerDeath.getKiller() instanceof Player) {
                Player playerKiller = playerDeath.getKiller();
                AlphaPlayerPvP playerKillerModel = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(playerKiller.getUniqueId());

                LinkedList<ArenaStatus> playerKillerArenaStatus = playerKillerModel.getArenaStatus();
                ArenaStatus playerKillerArena = playerKillerArenaStatus.stream().filter(arenaStatus ->
                        arenaStatus.getArena().getName().equalsIgnoreCase(playerKillerModel.getActualArena().getName()))
                        .collect(Document.toSingleton());

                playerKillerArena.AddKill();
                playerKillerArena.CheckKillStreak();
                playerKillerArenaStatus.set(playerKillerArenaStatus.indexOf(playerKillerArena), playerKillerArena);
                playerKillerModel.AddCoins();
                playerKillerModel.UpdateScoreBoard();
                playerKiller.sendMessage("Você matou: " + playerDeath.getName());
                playerKillerModel.setArenaStatus(playerKillerArenaStatus);

                LinkedList<ArenaStatus> playerDeathArenaStatus = playerDeathModel.getArenaStatus();
                ArenaStatus playerDeathArena = playerDeathArenaStatus.stream().filter(arenaStatus ->
                        arenaStatus.getArena().getName().equalsIgnoreCase(playerDeathModel.getActualArena().getName()))
                        .collect(Document.toSingleton());

                playerDeathArena.AddDeath();
                playerDeathArena.CheckLostKillStreak(playerKiller.getName());
                playerDeathArenaStatus.set(playerDeathArenaStatus.indexOf(playerDeathArena), playerDeathArena);
                playerDeathModel.RemoveCoins();
                playerDeathModel.UpdateScoreBoard();
                playerDeath.sendMessage("Você morreu para: " + playerKiller.getName());
                playerDeathModel.setArenaStatus(playerDeathArenaStatus);
            }


            Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), () ->
            {
                ForceRespawn.Respawn(playerDeath);
                playerDeathModel.clearPlayer();
                playerDeathModel.CreateDefaultItems();
                playerDeath.teleport(ArenaManager.GetLocation("Spawn"));
                playerDeathModel.setInSpawn(true);
            }, 3L);
        }
    }
}
