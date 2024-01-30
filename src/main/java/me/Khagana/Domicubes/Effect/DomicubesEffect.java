package me.Khagana.Domicubes.Effect;

import lombok.Getter;
import lombok.Setter;
import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;

public abstract class DomicubesEffect {
    @Getter @Setter
    protected DomicubesPlayer affectedPlayer;

    public abstract void onEnable();
}
