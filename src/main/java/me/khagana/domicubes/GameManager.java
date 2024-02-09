package me.khagana.domicubes;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import me.khagana.domicubes.instanciable.Team;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class GameManager {

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static GameManager instance;

    @Getter private Map<Player, DomicubesPlayer> playersMap;
    @Getter private Map<Chunk, Set<ControlPoint>> chunkControlPointMap;

    @Getter private Set<ControlPoint> controlPointList;
    @Getter private Set<Team> teamSet;

    @Getter @Setter
    private Plugin plugin;


    private GameManager (){
        this.playersMap =new HashMap<Player, DomicubesPlayer>();
        this.controlPointList = new HashSet<>();
        this.chunkControlPointMap = new HashMap<>();
        this.teamSet = new HashSet<>();
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
}
