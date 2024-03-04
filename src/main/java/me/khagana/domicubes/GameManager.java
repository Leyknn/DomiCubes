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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Set<ControlPoint> controlPointList;
    private List<Team> teamList;
    @Setter private Plugin plugin;
    private Status status;
    private GameConfig config;


    private GameManager (){
        this.playersMap =new HashMap<>();
        this.controlPointList = new HashSet<>();
        this.chunkControlPointMap = new HashMap<>();
        this.teamList = new ArrayList<>();
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
                for (Team t : teamList) {
                    sender.sendMessage(t.getName());
                }
            } else {
                return false;
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.teleport(playersMap.get(p).getTeam().getBase());
            }
            ControlPoint.setCaptureRate(config.getCaptureRate().getCurrent().getNumber());
            ControlPoint.setVPPerSecond(config.getVPperSec().getCurrent().getNumber());
            status=Status.in_game;
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You can't start the game now");
            return true;
        }
    }

    public void endGame(){}

    public boolean validTeam(CommandSender sender){
        if (DisplayTeamMenu.getTeams().stream().map(tempTeam -> tempTeam.getPlayers().stream().map(Player::getDisplayName).collect(Collectors.toSet())).flatMap(Set::stream).collect(Collectors.toSet()).equals(Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toSet())))
        {
            return true;
        }
        sender.sendMessage(ChatColor.RED + "All player must be in a team");
        return false;
    }

    public void createTeam(CommandSender sender){

        for (TempTeam tTeam : DisplayTeamMenu.getTeams()){
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
