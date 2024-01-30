package me.Khagana.Domicubes.Instanciable;

import lombok.Getter;
import lombok.Setter;
import me.Khagana.Domicubes.Effect.DomicubesEffect;
import me.Khagana.Domicubes.Effect.PermanantDomicubesEffect;
import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class DomicubesPlayer {
    private Player player;
    private PlayerStats basePlayerStats, playerStats;

    private List<DomicubesEffect> permanentEffect;
    private List<TemporaryDomicubesEffect> temporaryEffect;

    public DomicubesPlayer(Player player){
        this.player=player;
        this.basePlayerStats = new PlayerStats(this);
        this.playerStats=new PlayerStats(this);
        this.permanentEffect = new ArrayList<>();
        this.temporaryEffect = new ArrayList<>();
    }

    public DomicubesPlayer(Player player, PlayerStats ps){
        this.player=player;
        this.basePlayerStats = new PlayerStats(ps);
        this.playerStats=new PlayerStats(ps);
        this.permanentEffect = new ArrayList<>();
        this.temporaryEffect = new ArrayList<>();
    }

    public void addEffect(TemporaryDomicubesEffect e){
        e.setAffectedPlayer(this);
        temporaryEffect.add(e);
        e.onEnable();
        updateStats();
    }

    public void addEffect(PermanantDomicubesEffect e){
        permanentEffect.add(e);
        e.onEnable();
        updateStats();
    }
    public void removeEffect(TemporaryDomicubesEffect e){
        temporaryEffect.remove(e);
        this.playerStats = new PlayerStats(basePlayerStats);
        for (DomicubesEffect domicubesEffect : permanentEffect){
            domicubesEffect.onEnable();
        }
        for (DomicubesEffect domicubesEffect : temporaryEffect){
            domicubesEffect.onEnable();
        }
        updateStats();
    }

    public void updateEffect(){
        for (TemporaryDomicubesEffect e :temporaryEffect){
            if (e.updateTime() == 0){
                removeEffect(e);
            }
        }
    }

    public void updateStats(){
        player.setWalkSpeed((float) Math.min(1, playerStats.getMovementSpeed()));
        player.setMaxHealth(playerStats.getMaxHP());
    }
}
