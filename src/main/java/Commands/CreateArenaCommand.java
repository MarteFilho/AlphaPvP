package Commands;

import Domain.Arenas.ArenaManager;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateArenaCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());

            if (alphaPlayerPvP.hasPermission())
            {
                ArenaManager arena = new ArenaManager();
                arena.Create(player, args[0]);
                player.sendMessage("Você criou a arena " + args[0]);
                return true;
            }
            else{
                alphaPlayerPvP.getPlayer().sendMessage("Você não pussi permisão para executar esse comando!");
                return false;
            }
        }
        return false;
    }
}
