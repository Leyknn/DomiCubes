package me.Khagana.Domicubes.Effect;

import me.Khagana.Domicubes.GameManager;
import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateEffectsScript extends BukkitRunnable {
    @Override
    public void run() {
        for (DomicubesPlayer p : GameManager.getInstance().getPlayersMap().values()){
            p.updateEffect();
        }
    }
}
