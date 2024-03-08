package me.khagana.domicubes;

import me.khagana.domicubes.instanciable.Color;
import me.khagana.domicubes.menu.DisplayTeamMenu;
import me.khagana.domicubes.menu.LobbyMenu;
import me.khagana.domicubes.menu.TempTeam;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamMenuListener implements Listener {

    @EventHandler
    public void TeamMenuEvent(PlayerInteractEvent e){
        if (e.getItem().getType() == Material.CHEST){
            if (GameManager.getInstance().getTempsTeams().isEmpty()){
                GameManager.getInstance().getTempsTeams().add(new TempTeam(e.getPlayer().getLocation(), "T1", Color.BLUE));
                GameManager.getInstance().getTempsTeams().add(new TempTeam(e.getPlayer().getLocation().add(10, 5, 10), "T2", Color.RED));
            }
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                new LobbyMenu().open(e.getPlayer(), 0);
            }
        }
    }
}
