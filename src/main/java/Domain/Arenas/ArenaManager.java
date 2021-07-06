package Domain.Arenas;

import Utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ArenaManager
{
    private static FileManager fileManager;

    public ArenaManager()
    {
        fileManager = new FileManager("Arenas.yml");
    }


    public void Create(Player player, String arenaName)
    {
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".X", player.getLocation().getX());
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".Y", player.getLocation().getY());
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".Z", player.getLocation().getZ());
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".Pitch", player.getLocation().getPitch());
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".Yaw", player.getLocation().getYaw());
        fileManager.GetFile().set("Arenas." + arenaName.toLowerCase() + ".World", player.getLocation().getWorld().getName());
        fileManager.Save();
    }


    public static Location GetLocation(String arenaName)
    {
        fileManager = new FileManager("Arenas.yml");
        arenaName = arenaName.toLowerCase();
        double x = fileManager.GetFile().getDouble("Arenas." + arenaName + ".X");
        double y = fileManager.GetFile().getDouble("Arenas." + arenaName + ".Y");
        double z = fileManager.GetFile().getDouble("Arenas." + arenaName + ".Z");
        float Pitch = (float)fileManager.GetFile().getDouble("Arenas." + arenaName + ".Pitch");
        float Yaw = (float)fileManager.GetFile().getDouble("Arenas." + arenaName + ".Yaw");
        World world = Bukkit.getWorld(fileManager.GetFile().getString("Arenas." + arenaName + ".World"));

        return new Location(world, x, y, z, Yaw, Pitch);
    }

    public static boolean Exists(String arenaName)
    {
        fileManager = new FileManager("Arenas.yml");
        return fileManager.Exists("Arenas.", arenaName);
    }
}
