package me.khagana.domicubes.menu;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.instanciable.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
public class TempTeam{
    private final Location loc;
    @Setter
    private String name;
    @Setter private Color color;
    private final Set<Player> players;

    public TempTeam(Location loc, String name, Color color){
        this.loc = loc;
        this.name = name;
        this.color = color;
        this.players = new HashSet<>();
    }

    public ItemStack getBanner(){
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.setBaseColor(color.getDyeColor());
        meta.setDisplayName(color.getChatColor() + name);
        banner.setItemMeta(meta);
        return banner;
    }

    public ItemStack getCompass(){
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("Base coordinates");
        ArrayList<String> lore  = new ArrayList<>();
        lore.add(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
        meta.setLore(lore);
        compass.setItemMeta(meta);
        return compass;
    }

    public boolean isValid(){
        return (loc!=null && !players.isEmpty());
    }
}