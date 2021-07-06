package Domain;

import Domain.KitsDefault.*;
import Utils.ItemStackManager;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.LinkedList;


public abstract class Kit implements Listener
{
    private String Name;
    private String Description;
    private Material Icon;
    private Material Item;
    private Integer Price;
    private final double Cooldown;

    public Kit(String name, String description, Material icon, Material item, int price, double cooldown)
    {
        this.Name = name;
        this.Description = description;
        this.Item = item;
        this.Icon = icon;
        this.Price = price;
        this.Cooldown = cooldown;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (obj == this)
        {
            return true;
        }

        if (!(obj instanceof Kit))
        {
            return false;
        }

        Kit other = (Kit) obj;

        return Name.equalsIgnoreCase(other.Name);
    }

    public abstract void RemoveAbility();
    public abstract boolean HasItem();

    public static LinkedList<Kit> GetAllKits()
    {
        LinkedList<Kit> kits = new LinkedList<Kit>(Arrays.asList
                (
                        new None(),
                        new Monk(),
                        new Ninja(),
                        new Magma(),
                        new Boxer(),
                        new Thor(),
                        new Fisherman(),
                        new Snail(),
                        new Fireman(),
                        new Avatar(),
                        new Kangaroo()
                )
        );

        return kits;
    }

    public double GetCooldown()
    {
        return this.Cooldown;
    }

    public ItemStack GetIcon()
    {
        return ItemStackManager.Create("§a" + this.Name, Arrays.asList("§7" + this.Description, "", "§eClique para selecionar"),
                this.Icon, 1 );
    }

    public ItemStack GetIconShop(Integer playerCoins)
    {
        return ItemStackManager.Create("§a" + this.Name, Arrays.asList("§7" + this.Description, "", "§ePreço: " + this.Price,
                playerCoins.compareTo(this.Price) > 0 ? "§aPode comprar"  : "§7Faltam coins."),
                this.Icon, 1 );
    }

    public ItemStack GetItemStack()
    {
        ItemStack localItemStack = new ItemStack(this.Icon);
        ItemMeta localItemMeta = localItemStack.getItemMeta();
        localItemMeta.setDisplayName("§a" + this.Name);
        localItemStack.setItemMeta(localItemMeta);

        return localItemStack;
    }

    public Material GetItem()
    {
        return this.Item;
    }
    public Material GetItemMenu()
    {
        return this.Icon;
    }

    public String GetName()
    {
        return this.Name;
    }

    public String GetDescription()
    {
        return this.Description;
    }

    public Integer GetPrice()
    {
        return this.Price;
    }



}
