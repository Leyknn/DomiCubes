package me.Khagana.domicubes.effect;

import me.Khagana.domicubes.instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    private DomicubesPlayer affectedPlayer;
    private short time; // null for Permanent Effect

    public abstract void onEnable();
}
