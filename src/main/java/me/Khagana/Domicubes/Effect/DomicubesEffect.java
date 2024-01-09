package me.Khagana.Domicubes.Effect;

import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    private DomicubesPlayer affectedPlayer;
    private short time; // null for Permanent Effect

    public abstract void onEnable();
}
