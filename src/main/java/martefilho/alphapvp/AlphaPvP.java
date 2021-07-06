package martefilho.alphapvp;

import Commands.CreateSpawnCommand;
import DataContext.AlphaPlayerMongo;
import Domain.Spawn.ArenaSpawn;
import Events.Default.*;
import Events.Kit.BuyKits.BuyKits;
import Events.Kit.FirstKit.OpenFirstInventoryKit;
import Events.Kit.BuyKits.OpenInventoryShop;
import Events.Kit.SecondKit.OpenSecondInventoryKit;
import Events.Kit.SelectKit;
import Commands.CreateArenaCommand;
import Commands.Kit;
import Domain.KitsDefault.*;
import Events.Player.*;
import Utils.Kit.CombatLog;
import Utils.Soup;
import alphanetwork.core.Events.Default.BlockBreakHandler;
import alphanetwork.core.Events.Default.BlockPlaceHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AlphaPvP extends JavaPlugin {
    @Override
    public void onEnable() {
        RegisterEvents();
        this.getCommand("setarena").setExecutor(new CreateArenaCommand());
        this.getCommand("setarenaspawn").setExecutor(new CreateSpawnCommand());
        this.getCommand("kit").setExecutor(new Kit());
        AlphaPlayerMongo.DisableLogs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void RegisterEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new RepairItemsHandler(), this);
        pm.registerEvents(new CreatureSpawnEvent(), this);
        pm.registerEvents(new CreatureSpawnEvent(), this);
        pm.registerEvents(new PlayerJoinEvent(), this);
        pm.registerEvents(new PlayerDeathEvent(), this);
        pm.registerEvents(new PlayerDropItemEvent(), this);
        pm.registerEvents(new PlayerLoginEvent(), this);
        pm.registerEvents(new PlayerQuitEvent(), this);
        pm.registerEvents(new PlayerDamageEvent(), this);
        pm.registerEvents(new Monk(), this);
        pm.registerEvents(new Ninja(), this);
        pm.registerEvents(new Monk(), this);
        pm.registerEvents(new Magma(), this);
        pm.registerEvents(new Boxer(), this);
        pm.registerEvents(new Thor(), this);
        pm.registerEvents(new Fisherman(), this);
        pm.registerEvents(new Snail(), this);
        pm.registerEvents(new Fireman(), this);
        pm.registerEvents(new Avatar(), this);
        pm.registerEvents(new Kangaroo(), this);
        pm.registerEvents(new OpenFirstInventoryKit(), this);
        pm.registerEvents(new OpenSecondInventoryKit(), this);
        pm.registerEvents(new SelectKit(), this);
        pm.registerEvents(new OpenInventoryShop(),this);
        pm.registerEvents(new BuyKits(), this);
        pm.registerEvents(new DropHandler(), this);
        pm.registerEvents(new Soup(), this);
        pm.registerEvents(new CombatLog(), this);
        pm.registerEvents(new EntityDamageByEntityEvent(), this);
        pm.registerEvents(new ArenaSpawn(), this);

        pm.registerEvents(new BlockPlaceHandler(), this);
        pm.registerEvents(new BlockBreakHandler(), this);
    }
    public static AlphaPvP getPlugin()
    {
        return AlphaPvP.getPlugin(AlphaPvP.class);
    }
}
