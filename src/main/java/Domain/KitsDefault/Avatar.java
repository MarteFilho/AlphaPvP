package Domain.KitsDefault;

import Domain.Kit;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import martefilho.alphapvp.AlphaPvP;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Avatar extends Kit implements Listener {

    ArrayList<Player> cooldown = new ArrayList<>();
    long cd = 400L;


    public Avatar()
    {
        super("Avatar", "Dominador de elementos!", Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK, 20000, 15);
    }

    @Override
    public void RemoveAbility() {

    }

    @Override
    public boolean HasItem() {
        return true;
    }

    @EventHandler
    public void interactEvent(final PlayerInteractEvent e)
    {

        Player player = e.getPlayer();
        AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
        if (alphaPlayerPvP == null)
            return;

        if (alphaPlayerPvP.verifyKit(this) && !alphaPlayerPvP.getActualArena().getName().equalsIgnoreCase("Spawn"))
        {
            if (player.getItemInHand().getType() == Material.QUARTZ_BLOCK
                    || player.getItemInHand().getType() == Material.LAPIS_BLOCK
                    || player.getItemInHand().getType() == Material.REDSTONE_BLOCK
                    || player.getItemInHand().getType() == Material.GRASS
            )
            {
                if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
                    if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAvatar"))
                    {
                        ItemStack avatarAgua = new ItemStack(Material.LAPIS_BLOCK);
                        ItemMeta avatarAguaM = avatarAgua.getItemMeta();
                        avatarAguaM.setDisplayName("Avatar - Aguá");
                        avatarAgua.setItemMeta(avatarAguaM);
                        e.getPlayer().getInventory().setItem(1, avatarAgua);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Aguá")) {
                        ItemStack avatarFogo = new ItemStack(Material.REDSTONE_BLOCK);
                        ItemMeta avatarFogoM = avatarFogo.getItemMeta();
                        avatarFogoM.setDisplayName("Avatar - Fogo");
                        avatarFogo.setItemMeta(avatarFogoM);
                        e.getPlayer().getInventory().setItem(1, avatarFogo);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Fogo")) {
                        ItemStack avatarTerra = new ItemStack(Material.GRASS);
                        ItemMeta avatarTerraM = avatarTerra.getItemMeta();
                        avatarTerraM.setDisplayName("Avatar - Terra");
                        avatarTerra.setItemMeta(avatarTerraM);
                        e.getPlayer().getInventory().setItem(1, avatarTerra);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Terra")) {
                        ItemStack avatarQuartz = new ItemStack(
                                Material.QUARTZ_BLOCK);
                        ItemMeta avatarQuartzM = avatarQuartz.getItemMeta();
                        avatarQuartzM.setDisplayName("§aAvatar");
                        avatarQuartz.setItemMeta(avatarQuartzM);
                        e.getPlayer().getInventory().setItem(1, avatarQuartz);
                    }

                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)

                    if (this.cooldown.contains(e.getPlayer())) {
                        if (e.getItem().getType() == Material.GRASS ||
                                e.getItem().getType() == Material.QUARTZ_BLOCK ||
                                e.getItem().getType() == Material.REDSTONE_BLOCK ||
                                e.getItem().getType() == Material.LAPIS_BLOCK)
                            e.getPlayer().sendMessage("Você tem que esperar para usar a habilidade denovo");
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAvatar")) {
                        Vector velo1 = player.getLocation().getDirection().normalize().multiply(10);
                        Fireball boladenve = (Fireball) player.launchProjectile(Fireball.class);

                        boladenve.setIsIncendiary(false);
                        boladenve.setYield(0.0F);
                        boladenve.setVelocity(velo1);
                        player.setMetadata("ar", new FixedMetadataValue(AlphaPvP.getPlugin(), Boolean.TRUE));
                        Location location = player.getEyeLocation();
                        BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 30);

                        while (blocksToAdd.hasNext()) {
                            Location blockToAdd = blocksToAdd.next().getLocation();
                            Effect a = Effect.STEP_SOUND;
                            player.getWorld().playEffect(blockToAdd, a, 155);
                            player.playSound(player.getLocation(),
                                    Sound.ENDERDRAGON_WINGS, 10.0F, 9.0F);
                        }

                        this.cooldown.add(e.getPlayer());

                        Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), () -> {
                            Avatar.this.cooldown.remove(e.getPlayer());
                            e.getPlayer().sendMessage("Você pode usar sua habilidade novamente");
                        }, this.cd);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Aguá")) {
                        Vector velo1 = player.getLocation().getDirection()
                                .normalize().multiply(10);
                        Fireball boladenve = (Fireball) player
                                .launchProjectile(Fireball.class);
                        boladenve.setIsIncendiary(false);
                        boladenve.setYield(0.0F);
                        boladenve.setVelocity(velo1);
                        player.setMetadata("agua", new FixedMetadataValue(AlphaPvP.getPlugin(), Boolean.TRUE));
                        Location location = player.getEyeLocation();
                        BlockIterator blocksToAdd = new BlockIterator(
                                location, 0.0D, 30);
                        while (blocksToAdd.hasNext()) {
                            Location blockToAdd = blocksToAdd.next()
                                    .getLocation();
                            Effect a = Effect.STEP_SOUND;
                            player.getWorld().playEffect(blockToAdd, a, 8);
                            player.playSound(player.getLocation(),
                                    Sound.ENDERDRAGON_WINGS, 10.0F, 9.0F);
                        }
                        this.cooldown.add(e.getPlayer());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), () -> {
                            Avatar.this.cooldown.remove(e.getPlayer());
                            e.getPlayer().sendMessage("Você pode usar sua habilidade novamente");
                        }, this.cd);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Fogo")) {
                        Vector velo1 = player.getLocation().getDirection()
                                .normalize().multiply(10);
                        Fireball boladenve = player
                                .launchProjectile(Fireball.class);
                        boladenve.setIsIncendiary(false);
                        boladenve.setYield(0.0F);
                        boladenve.setVelocity(velo1);
                        player.setMetadata("fogo", new FixedMetadataValue(
                                AlphaPvP.getPlugin(), Boolean.TRUE));
                        Location location = player.getEyeLocation();
                        BlockIterator blocksToAdd = new BlockIterator(
                                location, 0.0D, 30);
                        while (blocksToAdd.hasNext()) {
                            Location blockToAdd = blocksToAdd.next()
                                    .getLocation();
                            Effect a = Effect.STEP_SOUND;
                            player.getWorld().playEffect(blockToAdd, a, 10);
                            player.playSound(player.getLocation(),
                                    Sound.ENDERDRAGON_WINGS, 10.0F, 9.0F);
                        }
                        this.cooldown.add(e.getPlayer());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), () -> {
                            Avatar.this.cooldown.remove(e.getPlayer());
                            e.getPlayer()
                                    .sendMessage("Você pode usar sua habilidade novamente");
                        }, this.cd);
                    } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Avatar - Terra")) {
                        Vector velo1 = player.getLocation().getDirection()
                                .normalize().multiply(10);
                        Fireball boladenve = (Fireball) player
                                .launchProjectile(Fireball.class);
                        boladenve.setIsIncendiary(false);
                        boladenve.setYield(0.0F);
                        boladenve.setVelocity(velo1);
                        player.setMetadata("terra", new FixedMetadataValue(
                                AlphaPvP.getPlugin(), Boolean.TRUE));
                        Location location = player.getEyeLocation();
                        BlockIterator blocksToAdd = new BlockIterator(
                                location, 0.0D, 30);
                        while (blocksToAdd.hasNext()) {
                            Location blockToAdd = blocksToAdd.next()
                                    .getLocation();
                            Effect a = Effect.STEP_SOUND;
                            player.getWorld().playEffect(blockToAdd, a, 2);
                            player.playSound(player.getLocation(),
                                    Sound.ENDERDRAGON_WINGS, 10.0F, 9.0F);
                        }
                        this.cooldown.add(e.getPlayer());

                        Bukkit.getScheduler().scheduleSyncDelayedTask(AlphaPvP.getPlugin(), () -> {
                            Avatar.this.cooldown.remove(e.getPlayer());
                            e.getPlayer()
                                    .sendMessage("Você pode usar sua habilidade novamente");
                        }, this.cd);
                    }
            }
        }
    }
}
