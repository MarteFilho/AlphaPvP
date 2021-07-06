package Events.Default;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageByEntityEvent implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEvent(org.bukkit.event.entity.EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player))
            return;
        Player p = (Player)e.getDamager();
        ItemStack sword = p.getItemInHand();
        double damage = e.getDamage();
        double swordDamage = getDamage(sword.getType());
        boolean isMore = false;
        if (damage > 1.0D)
            isMore = true;
        if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            for (PotionEffect effect : p.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    double minus;
                    if (isCrital(p)) {
                        minus = (swordDamage + swordDamage / 2.0D) * 1.3D * (effect.getAmplifier() + 1);
                    } else {
                        minus = swordDamage * 1.3D * (effect.getAmplifier() + 1);
                    }
                    damage -= minus;
                    damage += (2 * (effect.getAmplifier() + 1));
                    break;
                }
            }
        if (!sword.getEnchantments().isEmpty()) {
            if (sword.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) && isArthropod(e.getEntityType())) {
                damage -= 1.5D * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
                damage += (1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS));
            }
            if (sword.containsEnchantment(Enchantment.DAMAGE_UNDEAD) && isUndead(e.getEntityType())) {
                damage -= 1.5D * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                damage += (1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD));
            }
            if (sword.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                damage -= 1.25D * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                damage += (1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL));
            }
        }
        if (isCrital(p)) {
            damage -= swordDamage / 2.0D;
            damage++;
        }
        if (isMore)
            damage -= 2.0D;
        e.setDamage(damage);
    }

    private boolean isCrital(Player p) {
        return (p.getFallDistance() > 0.0F && !p.isOnGround() && !p.hasPotionEffect(PotionEffectType.BLINDNESS));
    }

    private boolean isArthropod(EntityType type) {
        switch (type) {
            case CAVE_SPIDER:
                return true;
            case SPIDER:
                return true;
            case SILVERFISH:
                return true;
        }
        return false;
    }

    private boolean isUndead(EntityType type) {
        switch (type) {
            case SKELETON:
                return true;
            case ZOMBIE:
                return true;
            case WITHER_SKULL:
                return true;
            case PIG_ZOMBIE:
                return true;
        }
        return false;
    }

    private double getDamage(Material type) {
        double damage = 1.0D;
        if (type.toString().contains("DIAMOND_")) {
            damage = 8.0D;
        } else if (type.toString().contains("IRON_")) {
            damage = 7.0D;
        } else if (type.toString().contains("STONE_")) {
            damage = 6.0D;
        } else if (type.toString().contains("WOOD_")) {
            damage = 5.0D;
        } else if (type.toString().contains("GOLD_")) {
            damage = 5.0D;
        }
        if (!type.toString().contains("_SWORD")) {
            damage--;
            if (!type.toString().contains("_AXE")) {
                damage--;
                if (!type.toString().contains("_PICKAXE")) {
                    damage--;
                    if (!type.toString().contains("_SPADE"))
                        damage = 1.0D;
                }
            }
        }
        return damage;
    }
}
