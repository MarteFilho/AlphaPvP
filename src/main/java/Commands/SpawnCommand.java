package Commands;

import Domain.Player.AlphaPlayerPvP;
import Utils.Kit.CombatLog;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

            if (CombatLog.onCombat(player))
                player.sendMessage("Você não pode voltar para o spawn pois você está em combate!");

            alphaPlayerPvP.getActualArena().teleportPlayer(player);
        }
        return true;
    }
}
