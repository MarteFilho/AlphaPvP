package Domain.Arenas.Defaults;

import Domain.Arenas.Arena;
import Domain.Arenas.ArenaManager;
import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.CombatLog;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Fps extends Arena implements Listener
{

    public Fps()
    {
        super("Fps");
    }
}
