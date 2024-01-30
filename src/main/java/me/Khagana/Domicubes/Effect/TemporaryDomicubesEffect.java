package me.Khagana.Domicubes.Effect;

import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;

public abstract class TemporaryDomicubesEffect extends DomicubesEffect{
    protected int time;

    public TemporaryDomicubesEffect(int time){
        this.time=time;
    }
    public int updateTime(){
        return --time;
    }
}
