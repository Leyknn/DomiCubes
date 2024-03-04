package me.khagana.domicubes;

import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameManager {

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static GameManager instance;
    private Map<Player, DomicubesPlayer> PlayersMap;
    private Map<Chunk, Set<ControlPoint>> ControlPointMap;

    private GameManager (){
        this.PlayersMap=new HashMap<Player, DomicubesPlayer>();
    }

    public Map<Player, DomicubesPlayer> getPlayersMap(){
        return this.PlayersMap;
    }

    public Map<Chunk, Set<ControlPoint>> getControlPointMap(){
        return this.ControlPointMap;
    }

    public boolean addControlPoint(ControlPoint cp, Set<Chunk> overlappedChunk){
        for (Chunk c : overlappedChunk){
            if (!ControlPointMap.containsKey(c)){
                ControlPointMap.put(c, new HashSet<>());
            }
            ControlPointMap.get(c).add(cp);
        }
        return true;
    }
}
