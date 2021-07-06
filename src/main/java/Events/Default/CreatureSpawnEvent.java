package Events.Default;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CreatureSpawnEvent implements Listener
{

    @EventHandler
    public void onCreatureSpawn(org.bukkit.event.entity.CreatureSpawnEvent e) {
        if (e.getSpawnReason() != org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.DISPENSE_EGG &&
                e.getSpawnReason() != org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CUSTOM &&
                e.getSpawnReason() != org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)
            e.setCancelled(true);
    }
}
