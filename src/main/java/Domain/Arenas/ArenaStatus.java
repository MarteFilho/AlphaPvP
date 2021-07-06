package Domain.Arenas;

import Domain.Player.AlphaPlayerPvP;
import org.bukkit.Bukkit;

public class ArenaStatus
{
    private Arena Arena;
    private AlphaPlayerPvP AlphaPlayerPvP;
    private Integer PlayerKills;
    private Integer PlayerKillStreak;
    private Integer PlayerDeaths;


    public ArenaStatus(AlphaPlayerPvP alphaPlayerPvP, Arena arena, Integer playerKills, Integer playerKillStreak, Integer playerDeaths)
    {
        this.AlphaPlayerPvP = alphaPlayerPvP;
        this.Arena = arena;
        this.PlayerKills = playerKills;
        this.PlayerKillStreak = playerKillStreak;
        this.PlayerDeaths = playerDeaths;
    }

    public AlphaPlayerPvP getPlayerModel(){return this.AlphaPlayerPvP;}
    public void setPlayer(AlphaPlayerPvP playerModel) {this.AlphaPlayerPvP = playerModel;}

    public Arena getArena(){return this.Arena;}
    public void setArena(Arena arena) {this.Arena = arena;}


    public int getKills() {
        return this.PlayerKills;
    }
    public void setKills(int newKills) {
        this.PlayerKills = newKills;
    }

    public int getKillStreak() {
        return this.PlayerKillStreak;
    }
    public void setKillStreak(int newKillStreak) {
        this.PlayerKillStreak = newKillStreak;
    }


    public int getDeaths() {
        return this.PlayerDeaths;
    }
    public void setDeaths(int newDeaths) { this.PlayerDeaths = newDeaths; }

    public void AddKill()
    {
        this.PlayerKills += 1;
        this.PlayerKillStreak += 1;
    }

    public void AddDeath() {
        this.PlayerDeaths += 1;
        this.PlayerKillStreak = 0;
    }

    public void CheckLostKillStreak(String playerKillerName) {
        if (this.PlayerKillStreak >= 10)
            Bukkit.broadcastMessage("§3" + this.AlphaPlayerPvP.getPlayer().getName() + " §eperdeu um killstreak de " + "§6"
                    + this.PlayerKillStreak + " para " + playerKillerName + "§e!");
    }

    public void CheckKillStreak()
    {
        String killStreak = String.valueOf(this.PlayerKillStreak);
        if (killStreak.endsWith("0") || killStreak.endsWith("5") && this.PlayerKillStreak >= 10)
            Bukkit.broadcastMessage("§3" + this.getPlayerModel().getName() + "§e atingiu um killstreak de " + "§6" + this.PlayerKillStreak + "§e!");
    }

}
