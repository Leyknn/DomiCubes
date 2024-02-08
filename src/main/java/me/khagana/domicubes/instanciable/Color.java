package me.khagana.domicubes.instanciable;

import lombok.Getter;
import me.khagana.domicubes.particuleseffect.ParticleEffect;

public enum Color {
    BLUE(0,0,255),
    RED(255,0,0),
    YELLOW(255, 255, 0),
    GREEN(0, 128, 0),
    NEUTRAL(64,64,64);

    private final int red;
    private final int green;
    private final int blue;

    @Getter private final ParticleEffect.OrdinaryColor ordinaryColor;

    Color(int red, int green, int blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
        this.ordinaryColor = new ParticleEffect.OrdinaryColor(red, green, blue);
    }
}
