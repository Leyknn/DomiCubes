package me.khagana.domicubes;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import me.khagana.domicubes.instanciable.Team;
import me.khagana.domicubes.menu.DisplayTeamMenu;
import me.khagana.domicubes.menu.TempTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class GameManager {

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static GameManager instance;

    private Map<Player, DomicubesPlayer> playersMap;
    private Map<Chunk, Set<ControlPoint>> chunkControlPointMap;
    private List<ControlPoint> controlPointList;
    private List<Team> teamList;
    private List<TempTeam> tempsTeams;
    private Status status;
    private GameConfig config;


    private GameManager (){
        this.playersMap =new HashMap<>();
        this.controlPointList = new ArrayList<>();
        this.chunkControlPointMap = new HashMap<>();
        this.teamList = new ArrayList<>();
        this.tempsTeams = new ArrayList<>();
        status = Status.lobby;
        this.config = new GameConfig();
    }

    public void addControlPoint(ControlPoint cp, Set<Chunk> overlappedChunk){
        for (Chunk c : overlappedChunk){
            if (!chunkControlPointMap.containsKey(c)){
                chunkControlPointMap.put(c, new HashSet<>());
            }
            chunkControlPointMap.get(c).add(cp);
        }
        controlPointList.add(cp);
    }

    public boolean startGame(CommandSender sender){
        if (status==Status.lobby) {
            if (validTeam(sender)) {
                createTeam(sender);
            } else {
                return false;
            }
            for (ControlPoint cp : controlPointList){
                cp.deleteBeacon();
            }
            for (Team t : teamList){
                t.deleteBanner();
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.teleport(playersMap.get(p).getTeam().getBase());
            }
            ControlPoint.setCaptureRate(config.getCaptureRate().getCurrent().getNumber());
            ControlPoint.setVPPerSecond(config.getVPperSec().getCurrent().getNumber());
            GameScript.getInstance().runTaskTimer(Main.getInstance(), 0, 20);
            status=Status.in_game;
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You can't start the game now");
            return true;
        }
    }

    public void endGame(){
        List<Team> winners = getWinners();
        if (winners.size()==1) {
            Team winningTeam = winners.get(0);
            GameScript.getInstance().cancel();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(Main.getInstance().getServer().getWorlds().get(0).getSpawnLocation());
                p.sendTitle(getWinTitle(winningTeam), getWinSubTitle(winningTeam));
            }
            new GameManager();
            Bukkit.getScheduler().cancelAllTasks();
        }
    }

    public String getWinTitle(Team winners){
        return ChatColor.RED + winners.getName() + " wins the game !";
    }

    public String getWinSubTitle(Team winners){
        StringBuilder playersList = new StringBuilder();
        String separator = "";
        for (Player p : winners.getPlayers()) {
            playersList.append(separator).append(p.getDisplayName());
            separator = ",";
        }
        return ChatColor.RED + "" + ChatColor.ITALIC + "Congrats to " + playersList + "!";
    }

    public List<Team> getWinners(){
        return teamList.stream()
                .collect(groupingBy(Team::getVP))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .get()
                .getValue();
    }

    public boolean validTeam(CommandSender sender){
        if (tempsTeams.stream().map(tempTeam -> tempTeam.getPlayers().stream().map(Player::getDisplayName).collect(Collectors.toSet())).flatMap(Set::stream).collect(Collectors.toSet()).equals(Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toSet())))
        {
            return true;
        }
        sender.sendMessage(ChatColor.RED + "All player must be in a team");
        return false;
    }

    public void createTeam(CommandSender sender){

        for (TempTeam tTeam : tempsTeams){
            if (tTeam.isValid()) {
                GameManager.getInstance().teamList.add(new Team(tTeam));
            }
        }
    }

    enum Status {
        in_game,
        lobby,
        paused,
    }
}
