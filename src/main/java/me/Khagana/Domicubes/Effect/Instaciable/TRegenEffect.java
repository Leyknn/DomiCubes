package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TRegenEffect extends TemporaryDomicubesEffect {
    private final double regen;
    public TRegenEffect(int time, double regen) {
        super(time);
        this.regen=regen;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setRegenPerSecond(affectedPlayer.getPlayerStats().getRegenPerSecond()+regen);
    }
}
