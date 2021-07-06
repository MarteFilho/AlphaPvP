package Domain.Player;

import Domain.Arenas.ArenaStatus;
import Domain.Arenas.Defaults.Arena;
import Domain.Arenas.Defaults.Fps;
import Domain.Kit;
import Domain.KitsDefault.None;
import Utils.Document;
import Utils.Inventory;
import alphanetwork.core.Domain.AlphaPlayer;
import alphanetwork.core.Domain.ScoreBoard.Team;
import alphanetwork.core.Utils.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class AlphaPlayerPvP extends AlphaPlayer {
    public AlphaPlayerPvP(Player player) {

        this.Create(player);
        this.Deaths = 0;
        this.Kills = 0;
        this.KillStreak = 0;
        this.FirstKit = new None();
        this.SecondKit = new None();
        this.Coins = 0;
        this.InSpawn = true;

        LinkedList<Domain.Kit> kitsPurchased = new LinkedList<>();
        kitsPurchased.add(new None());
        this.KitsPurchased = kitsPurchased;

        LinkedList<ArenaStatus> arenaStatus = new LinkedList<>();
        arenaStatus.add(new ArenaStatus(this, new Arena(), 0, 0, 0));
        arenaStatus.add(new ArenaStatus(this, new Fps(), 0, 0, 0));
        this.ArenaStatus = arenaStatus;
    }

    private int Deaths;
    private int Kills;
    private int KillStreak;
    private Kit FirstKit;
    private Kit SecondKit;
    private LinkedList<Kit> KitsPurchased;
    private LinkedList<ArenaStatus> ArenaStatus;
    private Domain.Arenas.Arena ActualArena;
    private Boolean InSpawn;
    private Integer Coins;

    public void setCoins(int coins) {
        this.Coins = coins;
    }

    public Integer getCoins() {
        return this.Coins;
    }

    public int getDeaths() {
        return this.Deaths;
    }

    public void setDeaths(int newDeaths) {
        this.Deaths = newDeaths;
    }

    public int getKills() {
        return this.Kills;
    }

    public void setKills(int newKills) {
        this.Kills = newKills;
    }

    public int getKillStreak() {
        return this.KillStreak;
    }

    public void setKillStreak(int newKillStreak) {
        this.KillStreak = newKillStreak;
    }


    public Kit getFirstKit() {
        return this.FirstKit;
    }

    public void setFirstKit(Kit newValueOfKit) {
        this.FirstKit = newValueOfKit;
    }

    public boolean verifyKit(Kit kit)
    {
        return FirstKit.getClass() == kit.getClass() || SecondKit.getClass() == kit.getClass();
    }

    public boolean inSpawn()
    {
        return this.InSpawn;
    }

    public Kit getSecondKit() {
        return this.SecondKit;
    }

    public void setSecondKit(Kit newValueOfKit) {
        this.SecondKit = newValueOfKit;
    }

    public LinkedList<Kit> getKitsPurchased() {
        return this.KitsPurchased;
    }

    public void setKitsPurchased(LinkedList<Kit> newKitsPurchased) {
        this.KitsPurchased = newKitsPurchased;
    }

    public Domain.Arenas.Arena getActualArena() {
        return this.ActualArena;
    }

    public void setActualArena(Domain.Arenas.Arena actualArena) {
        this.ActualArena = actualArena;
    }

    public LinkedList<ArenaStatus> getArenaStatus() {
        return this.ArenaStatus;
    }

    public void setArenaStatus(LinkedList<ArenaStatus> newArenaStatus) {
        this.ArenaStatus = newArenaStatus;
    }
    public void setInSpawn(boolean inSpawn){this.InSpawn = inSpawn;}

    public void RemoveCoins() {
        this.Coins -= Math.min(this.Coins, 3);
    }

    public void AddCoins() {
        this.Coins += 3;
    }

    public void CreateDefaultItems() {
        List<Material> items = new ArrayList<Material>() {
        };
        items.add(Material.CHEST);
        items.add(Material.CHEST);
        items.add(Material.SKULL_ITEM);
        items.add(Material.EMERALD);
        List<String> displayNames = new ArrayList<String>() {
        };
        displayNames.add("§aSelecionar Kit");
        displayNames.add("§aSelecionar Kit 2");
        displayNames.add("§aPerfil");
        displayNames.add("§aLoja de Kits");
        Inventory.Create(this.Player, items, displayNames);
    }

    public void CheckKillStreak() {
        String killStreak = String.valueOf(this.KillStreak);
        if (killStreak.endsWith("0") || killStreak.endsWith("5") && this.KillStreak >= 10)
            Bukkit.broadcastMessage("§3" + this.Name + "§e atingiu um killstreak de " + "§6" + this.KillStreak + "§e!");
    }

    public void CheckLostKillStreak(String playerKillerName) {
        if (this.KillStreak >= 10)
            Bukkit.broadcastMessage("§3" + this.Name + " §eperdeu um killstreak de" + "§6" + this.KillStreak + " para" + playerKillerName + "§e!");
    }

    public void CreateScoreBoard() {
        ArenaStatus arena = this.ArenaStatus.stream().filter(arenaStatus ->
                arenaStatus.getArena().getName().equalsIgnoreCase("Arena")).collect(Document.toSingleton());

        LinkedList<Team> teams = new LinkedList<>();
        teams.add(new Team("Rank", "Rank:", this.Rank.getName(), 10));
        teams.add(new Team("Kills", "Kills:", Integer.toString(arena.getKills()), 8));
        teams.add(new Team("Deaths", "Deaths:", Integer.toString(arena.getDeaths()), 7));
        teams.add(new Team("KillStreak", "KillStreak:", Integer.toString(arena.getKillStreak()), 6));
        teams.add(new Team("Coins", "Coins:", Integer.toString(this.Coins), 5));

        ScoreBoard.Create(this, "AlphaPvP", teams);
    }

    public void UpdateScoreBoard() {
        ArenaStatus actualArenaStatus = this.ArenaStatus.stream().filter(arenaStatus ->
                arenaStatus.getArena().getName().equalsIgnoreCase(this.ActualArena.getName().equalsIgnoreCase ("Spawn")
                        ? "Arena" : this.ActualArena.getName())).collect(Document.toSingleton());

        ScoreBoard.Update(this, "Coins", "Coins: " + this.Coins);
        ScoreBoard.Update(this, "Kills", "Kills: " + actualArenaStatus.getKills());
        ScoreBoard.Update(this, "Deaths", "Deaths: " + actualArenaStatus.getDeaths());
        ScoreBoard.Update(this, "KillStreak", "KillStreak: " + actualArenaStatus.getKillStreak());
    }
}
