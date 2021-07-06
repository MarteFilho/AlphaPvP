package Events.Kit.GUIs;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import Utils.ItemStackManager;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.LinkedList;

public class FirstKitGUI
{
    public static void create(AlphaPlayerPvP alphaPlayerPvP)
    {
        Inventory inventory = createInventory(alphaPlayerPvP, "Selecionar kit", 54);
        LinkedList<Integer> linkedListOfInteger = new LinkedList<>();

        int i;
        for (i = 10; i < 17; i++)
            linkedListOfInteger.add(i);
        for (i = 19; i < 26; i++)
            linkedListOfInteger.add(i);
        for (i = 28; i < 35; i++)
            linkedListOfInteger.add(i);
        for (i = 37; i < 44; i++)
            linkedListOfInteger.add(i);

        int integerOfMaxSize = 0;
        LinkedList<Kit> allKits = Kit.GetAllKits();


        for (Kit kit : allKits)
        {
            if (alphaPlayerPvP.getFirstKit().GetName().equalsIgnoreCase(kit.GetName()))
            {
                ItemStack item = kit.GetIcon();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                item.setItemMeta(itemMeta);

                inventory.setItem(linkedListOfInteger.get(integerOfMaxSize), item);
                integerOfMaxSize++;
                continue;
            }

            inventory.setItem(linkedListOfInteger.get(integerOfMaxSize), kit.GetIcon());
            integerOfMaxSize++;
        }

        inventory.setItem(49, ItemStackManager.Create("§eKit Selecionado: §a" + alphaPlayerPvP.getFirstKit().GetName(),
                Collections.singletonList(""), alphaPlayerPvP.getFirstKit().GetItemMenu(), 1));

        alphaPlayerPvP.getPlayer().openInventory(inventory);
        alphaPlayerPvP.getPlayer().updateInventory();
    }

    public static Inventory createInventory(AlphaPlayerPvP alphaPlayerPvP, String inventoryName, Integer size)
    {
        return Bukkit.createInventory(alphaPlayerPvP.getPlayer(), size, inventoryName);
    }
}
