package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DisplayTeamMenu extends SimpleMenu {

    @Getter private static List<TempTeam> teams = new ArrayList<>();

    private final static int OFFSET = 10;

    @Override
    public String getName() {
        return "Choose a team";
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        int i = 1;
        for (TempTeam tempTeam : teams){
            content[1][i++] = tempTeam.getBanner();
        }
        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHEST;
    }

    @Override
    public void onClick(MenuView menuView, ClickType clickType, int slot, ItemStack itemStack) {
        if (itemStack.getType()!= Material.AIR) {
            new TeamMenu(teams.get(slot - OFFSET)).open(menuView.getPlayer(), 0);
        }
    }
}
