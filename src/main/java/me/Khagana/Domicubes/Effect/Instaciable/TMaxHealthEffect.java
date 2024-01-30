package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TMaxHealthEffect extends TemporaryDomicubesEffect {
    private final double health;
    public TMaxHealthEffect(int time, double health) {
        super(time);
        this.health=health;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setMaxHP(affectedPlayer.getPlayerStats().getMaxHP() + health);
    }
}
