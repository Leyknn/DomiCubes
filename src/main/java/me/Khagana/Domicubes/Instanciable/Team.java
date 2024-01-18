package me.Khagana.Domicubes.Instanciable;

import org.bukkit.entity.Player;

import java.util.Set;

public class Team {
    private Color color;
    private String name;
    private Set<Player> players;
    private int VP;

    public Team(Color color, String name){
        this.color=color;
        this.name=name;
        this.VP=0;
    }

    public void addVictoryPoint(int vp){
        this.VP+=vp;
    }

}
