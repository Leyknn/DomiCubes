package me.khagana.domicubes;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;

public class HeadManger implements Listener {
    @Getter static private Map<Player, ItemStack> headMap = new HashMap<>();

    @EventHandler
    public void getHeadOnJoin(PlayerJoinEvent e){
        putHead(e.getPlayer());
    }

    @EventHandler
    public void getHeadOnPluginEnableEvent(PluginEnableEvent e){
        for (Player player : Bukkit.getOnlinePlayers()){
            putHead(player);
        }
    }

    private void putHead(Player player){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        String name = player.getDisplayName();
        meta.setOwner(name);
        meta.setDisplayName(name);
        head.setItemMeta(meta);
        headMap.put(player, head);
    }
}
