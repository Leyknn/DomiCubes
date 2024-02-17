package me.khagana.domicubes;

import fr.dwightstudio.dsmapi.MenuView;
import me.khagana.domicubes.menu.TeamMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamMenuListener implements Listener {

    @EventHandler
    public void TeamMenuEvent(PlayerInteractEvent e){
        if (e.getItem().getType() == Material.CHEST){
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                MenuView view = new TeamMenu().open(e.getPlayer(), 0);
            }
        }
    }
}
