package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.Menu;
import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import lombok.Getter;
import me.khagana.domicubes.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamMenu extends SimpleMenu {

    @Getter private static List<ItemStack> teams;
    private static ItemStack newTeamBanner;

    private final static int OFFSET = 10;

    public TeamMenu(){
        super();
        if (teams == null){
            teams = new ArrayList<>();
        }
        if (newTeamBanner == null){
            newTeamBanner = new ItemStack(Material.BANNER);
            BannerMeta meta = (BannerMeta) newTeamBanner.getItemMeta();
            meta.setBaseColor(DyeColor.WHITE);
            meta.setDisplayName(ChatColor.AQUA + "new team");
            newTeamBanner.setItemMeta(meta);
        }
        // for testing purpose
        if (teams.isEmpty()) {
            ItemStack testBanner = new ItemStack(Material.BANNER);
            BannerMeta meta = (BannerMeta) testBanner.getItemMeta();
            meta.setBaseColor(DyeColor.BLUE);
            meta.setDisplayName(ChatColor.BLUE + "T1");
            testBanner.setItemMeta(meta);
            teams.add(testBanner);
            ItemStack testBanner2 = new ItemStack(Material.BANNER);
            BannerMeta meta2 = (BannerMeta) testBanner2.getItemMeta();
            meta2.setBaseColor(DyeColor.RED);
            meta2.setDisplayName(ChatColor.RED + "T2");
            testBanner2.setItemMeta(meta2);
            teams.add(testBanner2);
        }
    }

    @Override
    public String getName() {
        return "Choose a team";
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        int i = 1;
        for (ItemStack banner : teams){
            content[1][i++] = banner;
        }
        content[1][i] = newTeamBanner;
        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHEST;
    }

    @Override
    public void onClick(MenuView menuView, ClickType clickType, int i, ItemStack itemStack) {
        if (itemStack.getItemMeta().getDisplayName().equals(newTeamBanner.getItemMeta().getDisplayName()) && ((BannerMeta) itemStack.getItemMeta()).getBaseColor()==DyeColor.WHITE) {
            MenuView view = new NewTeamMenu().open(menuView.getPlayer(), 0);
        } else {
            addPlayer(i-OFFSET, menuView.getPlayer());
            menuView.close();
        }
    }

    private void addPlayer(int i, Player player){
        for (ItemStack itemStack : teams) {
            ItemMeta meta =  itemStack.getItemMeta();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                lore.removeIf(x -> x.equals(player.getDisplayName()));
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }
        }
        ItemStack itemStack = teams.get(i);
        if (itemStack.getItemMeta().hasLore()) {
            itemStack.getItemMeta().getLore().add(player.getDisplayName());
        } else {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(player.getDisplayName());
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
    }
}
