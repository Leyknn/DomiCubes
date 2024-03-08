package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import me.khagana.domicubes.instanciable.Color;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class DisplayTeamMenu extends SimpleMenu {

    @Getter @Setter private static boolean isCreatingTeam=false;
    private static final ItemStack previous = new ItemBuilder(Material.ARROW, "Previous").build();
    private static final ItemStack newTeam = getNewTeamBanner();

    private final static int OFFSET = 10;

    @Override
    public String getName() {
        return "Choose a team";
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        int i = 1;
        for (TempTeam tempTeam : GameManager.getInstance().getTempsTeams()){
            content[1][i++] = tempTeam.getBanner();
        }
        content[1][7] = newTeam;
        content[2][8] = previous;
        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHEST;
    }

    @Override
    public void onClick(MenuView menuView, ClickType clickType, int slot, ItemStack itemStack) {
        if (itemStack.getType()!= Material.AIR) {
            if (slot >= OFFSET && slot <OFFSET+6) {
                new TeamMenu(GameManager.getInstance().getTempsTeams().get(slot - OFFSET)).open(menuView.getPlayer(), 0);
            } else if (slot==OFFSET+6){
                if (isCreatingTeam()){
                    menuView.getPlayer().sendMessage(ChatColor.RED + "Only one team can be created at once");
                } else {
                    new NewTeamMenu().open(menuView.getPlayer(), 0);
                }
            } else if (slot == 26){
                new LobbyMenu().open(menuView.getPlayer(), 0);
            }
        }
    }

    public static ItemStack getNewTeamBanner(){
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.setBaseColor(DyeColor.WHITE);
        meta.setDisplayName("New Team");
        banner.setItemMeta(meta);
        return banner;
    }


}
