package me.khagana.domicubes.instanciable;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.menu.DisplayTeamMenu;
import me.khagana.domicubes.menu.TempTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Team {
    @Getter private Color color;
    @Getter private String name;
    @Getter private Set<Player> players;
    @Getter private int VP;
    @Getter private Location base;
    @Getter @Setter private static int MAX_PLAYER=10;

    public Team(Color color, String name){
        this.color=color;
        this.name=name;
        this.players=new HashSet<>();
        this.VP=0;
    }

    public Team(TempTeam tTeam){
        this.color = tTeam.getColor();
        this.name = tTeam.getName();
        this.players = tTeam.getPlayers();
        for (Player p : players){
            GameManager.getInstance().getPlayersMap().put(p, new DomicubesPlayer(p,null));
        }
        this.VP=0;
        this.base = tTeam.getLoc();
        DisplayTeamMenu.getTeams().remove(tTeam);
    }

    public void addVictoryPoint(int vp){
        this.VP+=vp;
    }

}
