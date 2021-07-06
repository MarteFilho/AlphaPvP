package Utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabList {

    public static void Create(Player player, String header, String footer) {
        PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;

        IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabHeader);
        try {
            Field headerFild = packet.getClass().getDeclaredField("b");
            headerFild.setAccessible(true);
            headerFild.set(packet, tabFooter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerConnection.sendPacket(packet);
    }


}
