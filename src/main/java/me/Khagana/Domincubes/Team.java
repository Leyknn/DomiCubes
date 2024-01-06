package me.Khagana.Domincubes;

import org.bukkit.entity.Player;

import java.util.Set;

public class Team {
    private Color color;
    private String name;
    private Set<Player> players;
    private int HP;
    private int maxHP;

    public Team(Color color, String name, int maxHP){
        this.color=color;
        this.name=name;
        this.maxHP=maxHP;
        this.HP=maxHP;
    }

}
