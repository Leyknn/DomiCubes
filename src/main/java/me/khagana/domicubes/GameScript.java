package me.khagana.domicubes;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.controlpoint.ControlPoint;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Stream;

public class GameScript extends BukkitRunnable {
    private static GameScript instance;
    @Getter @Setter
    private boolean hasGameEnded;

    public static GameScript getInstance() {
        if (instance == null) {
            instance = new GameScript();
        }
        return instance;
    }

    private GameScript(){
        hasGameEnded = false;
    }
    @Override
    public void run() {
        GameManager gameManager = GameManager.getInstance();
        gameManager.getPlayersMap().keySet().parallelStream().forEach(Player -> {
            if (gameManager.getChunkControlPointMap().containsKey(Player.getLocation().getChunk())){
                Stream<ControlPoint> stream = gameManager.getChunkControlPointMap().get(Player.getLocation().getChunk()).stream();
                stream.forEach(ControlPoint -> {
                    if(ControlPoint.isPresent(Player)){
                        ControlPoint.addTeamPresence(gameManager.getPlayersMap().get(Player).getTeam());
                    }
                });
            }
        });
        gameManager.getControlPointList().parallelStream().forEach(ControlPoint -> {
            ControlPoint.updatePresence();
            ControlPoint.resetTeamPresence();
        });
        if (hasGameEnded){
            GameManager.getInstance().endGame();
        }
        for (Player player : GameManager.getInstance().getPlayersMap().keySet()){
            DomicubesScoreboard.createScoreboard(player);
        }
    }
}
