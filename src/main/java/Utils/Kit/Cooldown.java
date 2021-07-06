package Utils.Kit;

import Utils.ActionBar;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Cooldown {
    public static Map<UUID, Long> C = new ConcurrentHashMap<>();



    public static void addCooldown(Player player, int time)
    {
        C.put(player.getUniqueId(), System.currentTimeMillis() + time * 1000L);

        ActionBar.sendToPlayerProgressBar(player, time);
    }

    public static double getCooldown(Player p) {
        return C.containsKey(p.getUniqueId()) ? ((C.get(p.getUniqueId()) - System.currentTimeMillis()) / 10 / 100.0D) :
                0.0D;
    }

    public static boolean hasCooldown(Player p) {
        return C.containsKey(p.getUniqueId()) ? (((Long) C.get(p.getUniqueId()) >= System.currentTimeMillis())) : false;
    }

    public static void removeCooldown(Player p) {
        C.remove(p.getUniqueId());
    }

    public static void disable() {
        C.clear();
        C = null;
    }
}
