package me.khagana.domicubes.instanciable;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.effect.DomicubesEffect;
import me.khagana.domicubes.effect.ImpossibleEffectException;
import me.khagana.domicubes.effect.Permanent;
import me.khagana.domicubes.effect.Temporary;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class DomicubesPlayer {
    private Player player;
    private PlayerStats basePlayerStats, playerStats;
    private Team team;

    private List<DomicubesEffect> permanentEffect;
    private List<DomicubesEffect> temporaryEffect;

    public DomicubesPlayer(Player player, PlayerStats playerStats){
        this.player=player;
        this.basePlayerStats = new PlayerStats(playerStats);
        this.playerStats=new PlayerStats(playerStats);
        this.permanentEffect = new ArrayList<>();
        this.temporaryEffect = new ArrayList<>();
    }

    public void addEffect(DomicubesEffect e) throws ImpossibleEffectException {
        if(e instanceof Permanent){
            permanentEffect.add(e);
        } else if (e instanceof Temporary) {
            temporaryEffect.add(e);
        } else {
            throw new ImpossibleEffectException("An effect can't be neither temporary neither permanent");
        }
        e.onEnable();
    }

    public void removeEffect(DomicubesEffect e) throws ImpossibleEffectException{
        if(e instanceof Permanent){
            permanentEffect.remove(e);
        } else if (e instanceof Temporary) {
            temporaryEffect.remove(e);
        } else {
            throw new ImpossibleEffectException("An effect can't be neither temporary neither permanent");
        }
        this.playerStats = new PlayerStats(basePlayerStats);
        for (DomicubesEffect domicubesEffect : permanentEffect){
            domicubesEffect.onEnable();
        }
        for (DomicubesEffect domicubesEffect : temporaryEffect){
            domicubesEffect.onEnable();
        }
    }
}
