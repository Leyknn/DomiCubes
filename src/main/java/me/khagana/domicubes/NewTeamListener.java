package me.khagana.domicubes;

import me.khagana.domicubes.instanciable.Color;
import me.khagana.domicubes.menu.DisplayTeamMenu;
import me.khagana.domicubes.menu.TempTeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BannerMeta;

public class NewTeamListener implements Listener {
    @EventHandler
    public void bannerPlacedEvent(BlockPlaceEvent e){
        if (!e.isCancelled() && DisplayTeamMenu.isCreatingTeam() && e.getBlockPlaced().getState() instanceof Banner){
            Location loc = e.getBlock().getLocation();
            BannerMeta meta = (BannerMeta) e.getItemInHand().getItemMeta();
            PlayerInventory inv = e.getPlayer().getInventory();
            inv.setItem(inv.getHeldItemSlot(),new ItemStack(Material.AIR));
            e.setCancelled(true);
            DisplayTeamMenu.getTeams().add(new TempTeam(loc, meta.getLore().get(0), Color.getColorByDyeColor(meta.getBaseColor())));
            DisplayTeamMenu.setCreatingTeam(false);
        }
    }
}
