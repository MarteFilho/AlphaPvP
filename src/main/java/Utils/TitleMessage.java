package Utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleMessage {

    public static void Create(Player player, String titleMessage, String subtitleMessage)
    {
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":" + "\"" + titleMessage + "\"" + "}"), 40, 60, 60);

        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":" + "\"" + subtitleMessage + "\"" + "}"), 40, 60, 60);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
    }

}