package me.khagana.domicubes;

import me.khagana.domicubes.controlpoint.ControlPoint;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Stream;

public class ControlPointScript extends BukkitRunnable {
    @Override
    public void run() {
        GameManager instance = GameManager.getInstance();
        instance.getPlayersMap().keySet().parallelStream().forEach(Player -> {
            if (instance.getChunkControlPointMap().containsKey(Player.getLocation().getChunk())){
                Stream<ControlPoint> stream = instance.getChunkControlPointMap().get(Player.getLocation().getChunk()).stream();
                stream.forEach(ControlPoint -> {
                    if(ControlPoint.isPresent(Player)){
                        ControlPoint.addTeamPresence(instance.getPlayersMap().get(Player).getTeam());
                    }
                });
            }
        });
        instance.getControlPointList().parallelStream().forEach(ControlPoint -> {
            ControlPoint.updatePresence();
            ControlPoint.resetTeamPresence();
        });
        for (Player player : GameManager.getInstance().getPlayersMap().keySet()){
            DomicubesScoreboard.createScoreboard(player);
        }
    }
}
