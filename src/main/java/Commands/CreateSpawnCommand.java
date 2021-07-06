package Commands;

import Domain.Player.AlphaPlayerPvP;
import Domain.Spawn.SpawnManager;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateSpawnCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

            if (alphaPlayerPvP.hasPermission())
            {
                SpawnManager spawnManager = new SpawnManager();
                spawnManager.Create(player, args[0]);
                player.sendMessage("Você criou o spawn da arena " + args[0]);
                return true;
            }
            else{
                alphaPlayerPvP.getPlayer().sendMessage("Você não pussui permisão para executar esse comando!");
                return false;
            }
        }
        return false;
    }
}
