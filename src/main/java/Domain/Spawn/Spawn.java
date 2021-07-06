package Domain.Spawn;

import Domain.Arenas.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public abstract class Spawn implements Listener
{
    public Spawn(String name, Double allowedDistance) {
        this.Name = name;
        this.AllowedDistance = allowedDistance;
    }

    String Name;
    Double AllowedDistance;

    public abstract void onLeave(PlayerMoveEvent event);

    public abstract void addDefaultItems(Player player);

    public String getName() {
        return this.Name;
    }

    public Double getAllowedDistance() {
        return AllowedDistance;
    }

    public void teleportPlayer(Player player) {
        player.teleport(ArenaManager.GetLocation(this.Name));
    }
}
