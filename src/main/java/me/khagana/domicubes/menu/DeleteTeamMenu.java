package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class DeleteTeamMenu extends SimpleMenu {

    private final TempTeam team;

    private final static ItemStack delete = new ItemBuilder(Material.LAVA_BUCKET, ChatColor.RED + "Delete").build();
    private final static ItemStack cancel = new ItemBuilder(Material.WATER_BUCKET, ChatColor.GREEN + "Cancel").build();

    public DeleteTeamMenu(TempTeam team){
        super();
        this.team = team;
    }
    @Override
    public String getName() {
        return "Delete team : " + team.getName() + "?";
    }



    @Override
    public ItemStack[] getContent() {
        ItemStack[] content = new ItemStack[5];
        content[1] = delete;
        content[3] = cancel;
        return content;
    }

    @Override
    public PageType getPageType() {
        return PageType.HOPPER;
    }

    @Override
    public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
        if (itemStack != null) {
            switch (slot) {
                case 1:
                    GameManager.getInstance().getTempsTeams().remove(team);
                    new DisplayTeamMenu().open(view.getPlayer(), 0);
                    break;
                case 3:
                    new TeamMenu(team).open(view.getPlayer(), 0);
                    break;
            }
        }
    }
}
