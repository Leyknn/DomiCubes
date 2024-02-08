package me.khagana.domicubes.effect;

import me.khagana.domicubes.instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    private DomicubesPlayer affectedPlayer;

    public abstract void onEnable();
}
