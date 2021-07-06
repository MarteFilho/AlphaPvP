package Utils;

import Utils.Kit.Cooldown;
import martefilho.alphapvp.AlphaPvP;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;

public class ActionBar {

    private static PacketPlayOutChat packet;

    public ActionBar(String text)
    {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        this.packet = packet;
    }

    public static void sendToPlayerProgressBar(Player p, int time)
    {
        BukkitTask task = (new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (!Cooldown.hasCooldown(p))
                {
                    this.cancel();
                    PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + " " + "\"}"), (byte) 2);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                }

                double required_progress = time;
                double current_progress = Cooldown.getCooldown(p);
                double progress_percentage = current_progress/required_progress;
                StringBuilder sb = new StringBuilder();
                int bar_length = (int) current_progress + 10;
                for(int i = 0; i < bar_length; i++)
                {
                    if(i < bar_length * progress_percentage)
                    {
                        sb.append("§a|");
                    } else {
                        sb.append("§c|");
                    }
                }
                PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "Kit Thor " + sb.toString() + " §f" + current_progress + " segundos" + "\"}"), (byte) 2);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
            }
        }).runTaskTimer(AlphaPvP.getPlugin(), 0L, 2L);
    }

    public void sendToAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);;
        }
    }

}
