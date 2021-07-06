package Domain.Arenas;

import Domain.Arenas.Defaults.Fps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.LinkedList;
import java.util.List;

public abstract class Arena {
    String Name;

    public Arena(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void teleportPlayer(Player player) {
        player.teleport(ArenaManager.GetLocation(this.Name));
    }

    public static LinkedList<Arena> getAllArenas()
    {
        LinkedList<Arena> allArenas = new LinkedList<>();
        allArenas.add(new Fps());
        allArenas.add(new Domain.Arenas.Defaults.Arena());
        return allArenas;
    }
}
