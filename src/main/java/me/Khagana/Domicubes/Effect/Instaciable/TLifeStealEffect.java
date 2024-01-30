package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TLifeStealEffect extends TemporaryDomicubesEffect {
    private final double lifeSteal;
    public TLifeStealEffect(int time, double lifeSteal) {
        super(time);
        this.lifeSteal = lifeSteal;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setLifeSteal(affectedPlayer.getPlayerStats().getLifeSteal()+lifeSteal);
    }
}
