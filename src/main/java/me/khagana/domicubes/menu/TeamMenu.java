package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.HeadManger;
import me.khagana.domicubes.ItemBuilder;
import me.khagana.domicubes.instanciable.Team;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class TeamMenu extends SimpleMenu {
    private TempTeam team;

    public TeamMenu(TempTeam team){
        super();
        this.team = team;
    }

    private static final ItemStack deleteTeam = new ItemBuilder(Material.LAVA_BUCKET, ChatColor.RED + "Delete Team").build();
    private static final ItemStack back = new ItemBuilder(Material.ARROW, ChatColor.BOLD + "" + ChatColor.WHITE + "Back").build();
    private static final ItemStack join = new ItemBuilder(Material.BED, ChatColor.BOLD + "" + ChatColor.WHITE + "Join Team").build();

    private static final int COMPASS_OFFSET = 0;
    private static final int BANNER_OFFSET = 2;
    private static final int JOIN_OFFSET = 4;
    private static final int BACK_OFFSET = 6;
    private static final int DELETE_OFFSET = 8;

    @Override
    public String getName() {
        return "Team : " + team.getName();
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        content[0][COMPASS_OFFSET] = team.getCompass();
        content[0][BANNER_OFFSET] = team.getBanner();
        content[0][JOIN_OFFSET] = join;
        content[0][BACK_OFFSET] = back;
        content[0][DELETE_OFFSET] = deleteTeam;

        int i = 1;
        int j = 2;
        for (Player p : team.getPlayers()){
            if (i%9 == 8){
                i=1;
                j+=1;
            }
            content[j][i++] = HeadManger.getHeadMap().get(p);
        }

        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        int playerNb = team.getPlayers().size();
        if (playerNb <8){
            return PageType.CHEST_PLUS;
        } else if (playerNb < 15) {
            return PageType.CHEST_PLUS_PLUS;
        } else {
            return PageType.DOUBLE_CHEST;
        }
    }

    @Override
    public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
        if (itemStack.getType() != Material.AIR) {
            switch (slot) {
                case COMPASS_OFFSET:
                    view.close();
                    view.getPlayer().teleport(team.getLoc());
                    break;
                case JOIN_OFFSET:
                    if (team.getPlayers().size() < Team.getMAX_PLAYER()) {
                        if (!team.getPlayers().contains(view.getPlayer())) {
                            for (TempTeam tempTeam : GameManager.getInstance().getTempsTeams()) {
                                tempTeam.getPlayers().remove(view.getPlayer());
                            }
                            team.getPlayers().add(view.getPlayer());
                        }
                    } else {
                        view.getPlayer().sendMessage(ChatColor.RED + "This team is already full");
                    }
                    this.open(view.getPlayer(), 0);
                    break;
                case BACK_OFFSET:
                    new DisplayTeamMenu().open(view.getPlayer(), 0);
                    break;
                case DELETE_OFFSET:
                    new DeleteTeamMenu(team).open(view.getPlayer(), 0);
                    break;
            }
        }
    }
}
