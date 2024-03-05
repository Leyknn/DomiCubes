package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameConfig;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class LobbyMenu extends SimpleMenu {
    private static final ItemStack teams = new ItemBuilder(Material.BANNER, "Teams").build();
    private static final ItemStack config = new ItemBuilder(Material.PAPER, "Change config").build();
    private static final ItemStack start = new ItemBuilder(Material.BEACON, "Start the game").build();
    private static final int OFFSET = 9;
    @Override
    public String getName() {
        return "Menu";
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        content[1][1] = teams;
        content[1][4] = start;
        content[1][7] = config;
        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHEST;
    }

    @Override
    public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
        if (itemStack.getType() != Material.AIR){
            switch (slot){
                case OFFSET+1:
                    new DisplayTeamMenu().open(view.getPlayer(), 0);
                    break;
                case OFFSET+4:
                    if (GameManager.getInstance().startGame(view.getPlayer())){
                        view.close();
                    }
                    break;
                case OFFSET+7:
                    new ConfigMenu().open(view.getPlayer(),0);
                    break;
            }
        }
    }
}
