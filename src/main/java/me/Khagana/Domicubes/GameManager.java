package me.Khagana.Domicubes;

import lombok.Getter;
import lombok.Setter;
import me.Khagana.Domicubes.ControlPoint.ControlPoint;
import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static GameManager instance;

    @Getter private Map<Player, DomicubesPlayer> PlayersMap;
    @Getter private Map<Chunk, Set<ControlPoint>> ChunkControlPointMap;

    @Getter private Set<ControlPoint> ControlPointList;


    private GameManager (){
        this.PlayersMap=new HashMap<Player, DomicubesPlayer>();
    }

    public boolean addControlPoint(ControlPoint cp, Set<Chunk> overlappedChunk){
        for (Chunk c : overlappedChunk){
            if (!ChunkControlPointMap.containsKey(c)){
                ChunkControlPointMap.put(c, new HashSet<>());
            }
            ChunkControlPointMap.get(c).add(cp);
        }
        return true;
    }
}
