package Utils;


import martefilho.alphapvp.AlphaPvP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;


public class FileManager
{
    private static FileConfiguration fileConfiguration;
    private static File file;

    public FileManager(String name)
    {
        if (!AlphaPvP.getPlugin().getDataFolder().exists())
            AlphaPvP.getPlugin().getDataFolder().mkdir();
        file = new java.io.File("plugins/" + AlphaPvP.getPlugin().getDataFolder().getName(), name);
        if (!file.exists())
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        fileConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration GetFile()
    {
        return fileConfiguration;
    }

    public void Save() {
        try
        {
            fileConfiguration.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean Exists(String configurationName, String predicate) {
        predicate = predicate.toLowerCase();
        return (GetFile().get(configurationName + predicate) != null);
    }
}
