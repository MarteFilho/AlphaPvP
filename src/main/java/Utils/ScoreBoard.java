package Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoard
{

    public static void Create(Player player, String rank, Integer kills, Integer deaths, Integer killStreak, int coins)
    {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        final Scoreboard board = scoreboardManager.getNewScoreboard();
        final Objective objective = board.registerNewObjective("AlphaPvP", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "      AlphaPvP      ");

        Score score = objective.getScore(" ");
        score.setScore(10);

        Score score1 = objective.getScore(!rank.equalsIgnoreCase("") ? "Rank: " + rank : "Rank: " + "Membro");
        score1.setScore(9);

        Team playerKills = board.registerNewTeam("playerKills");
        playerKills.addEntry(ChatColor.BLACK + "" + ChatColor.AQUA);
        playerKills.setPrefix("Kills: " + kills);
        objective.getScore(ChatColor.BLACK + "" + ChatColor.AQUA).setScore(8);

        Team playerDeaths = board.registerNewTeam("playerDeaths");
        playerDeaths.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        playerDeaths.setPrefix("Deaths: " + deaths);
        objective.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(7);

        Team playerKillStreak = board.registerNewTeam("playerKillStreak");
        playerKillStreak.addEntry(ChatColor.YELLOW + "" + ChatColor.WHITE);
        playerKillStreak.setPrefix("KillStreak: " + killStreak);
        objective.getScore(ChatColor.YELLOW + "" + ChatColor.WHITE).setScore(6);

        Team playerCoins = board.registerNewTeam("playerCoins");
        playerCoins.addEntry(ChatColor.WHITE + "" + ChatColor.AQUA);
        playerCoins.setPrefix("Coins: " + coins);
        objective.getScore(ChatColor.WHITE + "" + ChatColor.AQUA).setScore(5);

        Score score6 = objective.getScore("§ewww.alphamc.com.br");
        score6.setScore(3);

        Team team = board.getTeam("team");
        if (team == null)
            team = board.registerNewTeam("team");

        team.setPrefix("Teste ");
        team.setSuffix(" Ola");

        team.addEntry(player.getName());

        player.setScoreboard(board);
    }

    public static void updateScoreBoard(Player player, String teamName, String prefix)
    {
        Scoreboard board = player.getScoreboard();
        board.getTeam(teamName).setPrefix(prefix);
    }
}

