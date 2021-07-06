package Commands;

import Domain.KitsDefault.Thor;
import Domain.Player.AlphaPlayerPvP;
import alphanetwork.core.Domain.AlphaPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kit implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            AlphaPlayerPvP alphaPlayerPvP = (AlphaPlayerPvP) AlphaPlayerManager.getAlphaPlayer(player.getUniqueId());
            alphaPlayerPvP.setFirstKit(new Thor());

            player.sendMessage("Kit selecionado");
        }
        return true;
    }
}
