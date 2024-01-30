package me.Khagana.Domicubes;

import lombok.Getter;
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

    @Getter private Map<Player, DomicubesPlayer> playersMap;
    @Getter private Map<Chunk, Set<ControlPoint>> chunkControlPointMap;

    @Getter private Set<ControlPoint> ControlPointList;


    private GameManager (){
        this.playersMap =new HashMap<Player, DomicubesPlayer>();
    }

    public boolean addControlPoint(ControlPoint cp, Set<Chunk> overlappedChunk){
        for (Chunk c : overlappedChunk){
            if (!chunkControlPointMap.containsKey(c)){
                chunkControlPointMap.put(c, new HashSet<>());
            }
            chunkControlPointMap.get(c).add(cp);
        }
        return true;
    }
}
