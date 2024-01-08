package me.Khagana.Domicubes.Instanciable;

import me.Khagana.Domicubes.Effect.DomicubesEffect;
import me.Khagana.Domicubes.Effect.ImpossibleEffectException;
import me.Khagana.Domicubes.Effect.Permanant;
import me.Khagana.Domicubes.Effect.Temporary;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import java.util.List;

public class DomicubesPlayer {
    private Player player;
    private PlayerStats basePlayerStats, playerStats;

    private List<DomicubesEffect> permanantEffect;
    private List<DomicubesEffect> temporaryEffect;

    public DomicubesPlayer(Player player, PlayerStats playerStats){
        this.player=player;
        this.basePlayerStats = new PlayerStats(playerStats);
        this.playerStats=new PlayerStats(playerStats);
    }

    public void addEffect(DomicubesEffect e) throws ImpossibleEffectException {
        if(e instanceof Permanant){
            permanantEffect.add(e);
        } else if (e instanceof Temporary) {
            temporaryEffect.add(e);
        } else {
            throw new ImpossibleEffectException("An effect can't be neither temporary neither permanent");
        }
        e.onEnable();
    }

    public void removeEffect(DomicubesEffect e) throws ImpossibleEffectException{
        if(e instanceof Permanant){
            permanantEffect.remove(e);
        } else if (e instanceof Temporary) {
            temporaryEffect.remove(e);
        } else {
            throw new ImpossibleEffectException("An effect can't be neither temporary neither permanent");
        }
        this.playerStats = new PlayerStats(basePlayerStats);
        for (DomicubesEffect domincubesEffect : permanantEffect){
            domincubesEffect.onEnable();
        }
        for (DomicubesEffect domicubesEffect : temporaryEffect){
            domicubesEffect.onEnable();
        }
    }
}
