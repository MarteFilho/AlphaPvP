package Domain.Spawn;

import Domain.Arenas.ArenaManager;
import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaSpawn extends Spawn
{
    public ArenaSpawn()
    {
        super("ArenaSpawn", 15.0D);
    }

    @EventHandler
    public void onLeave(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        Location location;

        if (SpawnManager.Exists(this.getName()))
        {
            location = SpawnManager.GetLocation(this.getName());
        }
        else
        {
            location = player.getWorld().getSpawnLocation();
        }

        if (player.getLocation().distance(location) > this.getAllowedDistance()
                && (alphaPlayerPvP.getActualArena().getName() + "Spawn").equalsIgnoreCase(this.getName())
                    && alphaPlayerPvP.inSpawn())
        {
            alphaPlayerPvP.clearPlayer();

            if (alphaPlayerPvP.getFirstKit().GetName().equalsIgnoreCase("Nenhum"))
            {
                ItemStack swordItemStack = new ItemStack(Material.STONE_SWORD, 1);
                swordItemStack.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                player.getInventory().setItem(0, swordItemStack);
            }
            else
            {
                ItemStack swordItemStack = new ItemStack(Material.STONE_SWORD, 1);
                player.getInventory().setItem(0, swordItemStack);
            }

            if (alphaPlayerPvP.getFirstKit().HasItem())
            {
                Kit firstKit = alphaPlayerPvP.getFirstKit();
                player.getInventory().setItem(1, firstKit.GetItemStack());
            }
            if (alphaPlayerPvP.getSecondKit().HasItem())
            {
                Kit secondKit = alphaPlayerPvP.getSecondKit();
                player.getInventory().setItem(2, secondKit.GetItemStack());
            }
            addDefaultItems(player);

            player.sendMessage("Você perdeu sua proteção!");
            alphaPlayerPvP.setInSpawn(false);
        }
    }

    public void addDefaultItems(Player player)
    {
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

        player.getInventory().setItem(13, bowlItemStack);
        player.getInventory().setItem(14, redItemStack);
        player.getInventory().setItem(15, brownItemStack);
        player.updateInventory();
    }
}
