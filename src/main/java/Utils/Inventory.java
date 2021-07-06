package Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Inventory {

    public static void Create(Player player, List<Material> items, List<String> displayName)
    {
        int index = 0;

        try
        {
            for (Material item : items)
            {
                if (item.equals(Material.SKULL_ITEM))
                {
                    ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                    playerHeadMeta.setOwner(player.getName());
                    playerHeadMeta.setDisplayName(displayName.get(index));

                    playerHead.setItemMeta(playerHeadMeta);
                    player.getInventory().addItem(playerHead);
                    index++;
                    continue;
                }

                ItemStack itemStack = new ItemStack(item);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(displayName.get(index));
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);

                index++;
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }
}
