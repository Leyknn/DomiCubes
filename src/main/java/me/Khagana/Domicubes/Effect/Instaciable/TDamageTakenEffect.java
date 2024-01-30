package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TDamageTakenEffect extends TemporaryDomicubesEffect {
    private final double damageTaken;
    public TDamageTakenEffect(int time, double damageTaken) {
        super(time);
        this.damageTaken=damageTaken;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setDamageTaken(affectedPlayer.getPlayerStats().getDamageTaken()*damageTaken);
    }
}
