package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TDamageDealtEffect extends TemporaryDomicubesEffect {
    private final double damageDealt;
    public TDamageDealtEffect(int time, double damageDealt) {
        super(time);
        this.damageDealt=damageDealt;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setDamageDealt(affectedPlayer.getPlayerStats().getDamageDealt()*damageDealt);
    }
}
