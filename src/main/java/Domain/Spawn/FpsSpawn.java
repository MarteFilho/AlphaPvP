package Domain.Spawn;

import Domain.Arenas.ArenaManager;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FpsSpawn extends Spawn
{

    public FpsSpawn() {
        super("FpsSpawn", 15.0D);
    }

    @Override
    public void onLeave(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        Location location;

        if (ArenaManager.Exists(this.getName())) {
            location = ArenaManager.GetLocation(this.getName());
        } else {
            return;
        }

        if (player.getLocation().distance(location) > this.getAllowedDistance()
                && alphaPlayerPvP.getActualArena().getName().equalsIgnoreCase(this.getName()))
        {
            alphaPlayerPvP.clearPlayer();
            addDefaultItems(player);

            alphaPlayerPvP.setActualArena(new Domain.Arenas.Defaults.Fps());
            player.sendMessage("Você perdeu sua proteção!");
        }
    }

    @Override
    public void addDefaultItems (Player player)
    {
        ItemStack swordItemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
        swordItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 1);

        ItemStack soupItemStack = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta soupItemMeta = soupItemStack.getItemMeta();
        soupItemStack.setItemMeta(soupItemMeta);

        ItemStack bowlItemStack = new ItemStack(Material.BOWL, 64);
        ItemStack redItemStack = new ItemStack(Material.RED_MUSHROOM, 64);
        ItemStack brownItemStack = new ItemStack(Material.BROWN_MUSHROOM, 64);

        for (int i = 1; i < 37; i++)
        {
            player.getInventory().addItem(soupItemStack);
        }

        player.getInventory().setItem(0, swordItemStack);
        player.getInventory().setItem(13, bowlItemStack);
        player.getInventory().setItem(14, redItemStack);
        player.getInventory().setItem(15, brownItemStack);
        player.updateInventory();
    }
}
