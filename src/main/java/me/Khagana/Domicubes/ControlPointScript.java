package me.Khagana.Domicubes;

import me.Khagana.Domicubes.ControlPoint.ControlPoint;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ControlPointScript extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : GameManager.getInstance().getPlayersMap().keySet()){
            if (GameManager.getInstance().getChunkControlPointMap().containsKey(p.getLocation().getChunk())){
                for (ControlPoint cp : GameManager.getInstance().getChunkControlPointMap().get(p.getLocation().getChunk())) {
                    if(cp.isPresent(p)){
                        cp.addTeamPresence(GameManager.getInstance().getPlayersMap().get(p).getTeam());
                    }
                }
            }
        }
        for (ControlPoint cp : GameManager.getInstance().getControlPointList()){
            cp.doSmthg();
            cp.resetTeamPresence();
        }
    }
}
