package me.Khagana.Domincubes;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private static GameManager instance;
    private Map<Player, DomincubesPlayer> domincubesPlayersMap;

    private GameManager (){
        this.domincubesPlayersMap=new HashMap<Player, DomincubesPlayer>();
    }
}
