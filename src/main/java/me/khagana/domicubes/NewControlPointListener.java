package me.khagana.domicubes;

import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.Color;
import me.khagana.domicubes.menu.ControlPointMenu;
import me.khagana.domicubes.menu.DisplayTeamMenu;
import me.khagana.domicubes.menu.TempTeam;
import org.bukkit.Location;
import org.bukkit.block.Banner;
import org.bukkit.block.Beacon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class NewControlPointListener  implements Listener {
    @EventHandler
    public void beaconPlacedEvent(BlockPlaceEvent e){
        if (!e.isCancelled() && ControlPointMenu.isCreatingControlPoint() && e.getBlockPlaced().getState() instanceof Beacon) {
            Location loc = e.getBlock().getLocation();
            new ControlPoint(loc, Integer.parseInt(e.getItemInHand().getItemMeta().getLore().get(0).split(": ")[1]));
            ControlPointMenu.setCreatingControlPoint(false);
        }
    }
}