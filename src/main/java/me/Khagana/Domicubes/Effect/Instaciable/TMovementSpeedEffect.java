package me.Khagana.Domicubes.Effect.Instaciable;

import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;

public class TMovementSpeedEffect extends TemporaryDomicubesEffect {
    private final double movementSpeedMul;
    public TMovementSpeedEffect(int time, double movementSpeedMul) {
        super(time);
        this.movementSpeedMul=movementSpeedMul;
    }

    @Override
    public void onEnable() {
        affectedPlayer.getPlayerStats().setMovementSpeed(affectedPlayer.getPlayerStats().getMovementSpeed()*movementSpeedMul);
    }
}
