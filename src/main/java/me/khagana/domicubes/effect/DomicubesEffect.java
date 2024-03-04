package me.khagana.domicubes.effect;

import me.khagana.domicubes.instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    private DomicubesPlayer affectedPlayer;
    private short time; // null for Permanent Effect

    public abstract void onEnable();
}
