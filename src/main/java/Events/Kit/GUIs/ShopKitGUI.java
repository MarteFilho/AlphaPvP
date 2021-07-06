package Events.Kit.GUIs;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.LinkedList;

public class ShopKitGUI
{
    public static void create(AlphaPlayerPvP alphaPlayerPvP)
    {
        Inventory inventory = createInventory(alphaPlayerPvP, "Comprar kits", 54);
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
        LinkedList<Kit> alphaPlayerPvPKitsPurchased = alphaPlayerPvP.getKitsPurchased();

        for (Kit kit : allKits)
        {
            if (!alphaPlayerPvPKitsPurchased.contains(kit))
            {
                inventory.setItem(linkedListOfInteger.get(integerOfMaxSize), kit.GetIconShop(alphaPlayerPvP.getCoins()));
                integerOfMaxSize++;
            }
        }

        alphaPlayerPvP.getPlayer().openInventory(inventory);
        alphaPlayerPvP.getPlayer().updateInventory();
    }

    public static Inventory createInventory(AlphaPlayerPvP alphaPlayerPvP, String inventoryName, Integer size)
    {
        return Bukkit.createInventory(alphaPlayerPvP.getPlayer(), size, inventoryName);
    }
}
