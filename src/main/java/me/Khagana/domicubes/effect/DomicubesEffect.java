package me.Khagana.domicubes.Effect;

import me.Khagana.domicubes.Instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    private DomicubesPlayer affectedPlayer;
    private short time; // null for Permanent Effect

    public abstract void onEnable();
}
