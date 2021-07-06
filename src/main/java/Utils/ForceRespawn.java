package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ForceRespawn
{
    public static void Respawn(Player player)
    {
        try {

            String bukkitVersion = Bukkit.getServer().getClass()
                    .getPackage().getName().substring(23);

            Class<?> cp = Class.forName("org.bukkit.craftbukkit."
                    + bukkitVersion + ".entity.CraftPlayer");
            Class<?> clientCmd = Class.forName("net.minecraft.server."
                    + bukkitVersion + ".PacketPlayInClientCommand");
            Class enumClientCMD = Class.forName("net.minecraft.server."
                    + bukkitVersion + ".PacketPlayInClientCommand$EnumClientCommand");

            Method handle = cp.getDeclaredMethod("getHandle");

            Object entityPlayer = handle.invoke(player);

            Constructor<?> packetConstr = clientCmd
                    .getDeclaredConstructor(enumClientCMD);
            Enum<?> num = Enum.valueOf(enumClientCMD, "PERFORM_RESPAWN");

            Object packet = packetConstr.newInstance(num);

            Object playerConnection = entityPlayer.getClass()
                    .getDeclaredField("playerConnection").get(entityPlayer);
            Method send = playerConnection.getClass().getDeclaredMethod("a",
                    clientCmd);

            send.invoke(playerConnection, packet);

        } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                NoSuchMethodException | ClassNotFoundException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}
