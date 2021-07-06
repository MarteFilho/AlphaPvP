package Domain.Spawn;

import Utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SpawnManager
{
    private static FileManager fileManager;

    public SpawnManager()
    {
        fileManager = new FileManager("Spawns.yml");
    }


    public void Create(Player player, String spawnName)
    {
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".X", player.getLocation().getX());
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".Y", player.getLocation().getY());
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".Z", player.getLocation().getZ());
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".Pitch", player.getLocation().getPitch());
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".Yaw", player.getLocation().getYaw());
        fileManager.GetFile().set("Spawns." + spawnName.toLowerCase() + ".World", player.getLocation().getWorld().getName());
        fileManager.Save();
    }


    public static Location GetLocation(String spawnName)
    {
        fileManager = new FileManager("Spawns.yml");
        spawnName = spawnName.toLowerCase();
        double x = fileManager.GetFile().getDouble("Spawns." + spawnName + ".X");
        double y = fileManager.GetFile().getDouble("Spawns." + spawnName + ".Y");
        double z = fileManager.GetFile().getDouble("Spawns." + spawnName + ".Z");
        float Pitch = (float)fileManager.GetFile().getDouble("Spawns." + spawnName + ".Pitch");
        float Yaw = (float)fileManager.GetFile().getDouble("Spawns." + spawnName + ".Yaw");
        World world = Bukkit.getWorld(fileManager.GetFile().getString("Spawns." + spawnName + ".World"));

        return new Location(world, x, y, z, Yaw, Pitch);
    }

    public static boolean Exists(String spawnName)
    {
        fileManager = new FileManager("Spawns.yml");
        return fileManager.Exists("Spawns.", spawnName);
    }
}
