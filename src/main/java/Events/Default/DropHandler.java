package Events.Default;

import martefilho.alphapvp.AlphaPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class DropHandler implements Listener
{
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e)
    {
        Item item = e.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), item::remove, 80L);
    }
}
