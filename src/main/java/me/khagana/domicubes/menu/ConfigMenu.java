package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.Menu;
import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.pages.Page;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameConfig;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class ConfigMenu extends Menu {
    private final GameConfig config = GameManager.getInstance().getConfig();
    private static final ItemStack previous = new ItemBuilder(Material.ARROW, "Previous").build();
    private final int OFFSET = 9;
    @Override
    public String getName() {
        return "Change the game config";
    }

    @Override
    public Page[] getPages() {
        Page[] pages =  new Page[getPageCount()];
        pages[0] = new Page() {
            @Override
            public String getName() {
                return "Config";
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][2] = new ItemBuilder(Material.PAPER, config.getVPperSec().getName()).lore(""+config.getVPperSec().getCurrent().getNumber()).build();
                content[1][4] = new ItemBuilder(Material.PAPER, config.getVPtoWin().getName()).lore(""+config.getVPtoWin().getCurrent().getNumber()).build();
                content[1][6] = new ItemBuilder(Material.PAPER, config.getCaptureRate().getName()).lore(""+config.getCaptureRate().getCurrent().getNumber()).build();
                content[2][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack!=null && itemStack.getType()!=Material.AIR) {
                    switch (slot){
                        case OFFSET + 2:
                            view.setPage(1);
                            break;
                        case OFFSET + 4:
                            view.setPage(2);
                            break;
                        case OFFSET + 6:
                            view.setPage(3);
                            break;
                        case 2*OFFSET + 8:
                            new LobbyMenu().open(view.getPlayer(), 0);
                            break;
                    }
                }
            }
        };

        pages[1] = new Page() {
            @Override
            public String getName() {
                return config.getVPperSec().getName();
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][2] = new ItemBuilder(Material.PAPER, ""+config.getVPperSec().getLow().getNumber()).build();
                content[1][4] = new ItemBuilder(Material.PAPER, ""+config.getVPperSec().getMedium().getNumber()).build();
                content[1][6] = new ItemBuilder(Material.PAPER, ""+config.getVPperSec().getHigh().getNumber()).build();
                content[2][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack != null && itemStack.getType()!= Material.AIR){
                    switch (slot){
                        case OFFSET + 2:
                            config.setVPperSec(GameConfig.Choice.low);
                            view.setPage(0);
                            break;
                        case OFFSET + 4:
                            config.setVPperSec(GameConfig.Choice.medium);
                            view.setPage(0);
                            break;
                        case OFFSET + 6:
                            config.setVPperSec(GameConfig.Choice.high);
                            view.setPage(0);
                            break;
                        case 2*OFFSET + 8:
                            view.setPage(0);
                            break;
                    }

                }
            }
        };

        pages[2] = new Page() {
            @Override
            public String getName() {
                return config.getVPtoWin().getName();
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][2] = new ItemBuilder(Material.PAPER, ""+config.getVPtoWin().getLow().getNumber()).build();
                content[1][4] = new ItemBuilder(Material.PAPER, ""+config.getVPtoWin().getMedium().getNumber()).build();
                content[1][6] = new ItemBuilder(Material.PAPER, ""+config.getVPtoWin().getHigh().getNumber()).build();
                content[2][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack != null && itemStack.getType()!= Material.AIR){
                    switch (slot){
                        case OFFSET + 2:
                            config.setVPtoWin(GameConfig.Choice.low);
                            view.setPage(0);
                            break;
                        case OFFSET + 4:
                            config.setVPtoWin(GameConfig.Choice.medium);
                            view.setPage(0);
                            break;
                        case OFFSET + 6:
                            config.setVPtoWin(GameConfig.Choice.high);
                            view.setPage(0);
                            break;
                        case 2*OFFSET + 8:
                            view.setPage(0);
                            break;
                    }

                }
            }
        };

        pages[3] = new Page() {
            @Override
            public String getName() {
                return config.getCaptureRate().getName();
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][2] = new ItemBuilder(Material.PAPER, ""+config.getCaptureRate().getLow().getNumber()).build();
                content[1][4] = new ItemBuilder(Material.PAPER, ""+config.getCaptureRate().getMedium().getNumber()).build();
                content[1][6] = new ItemBuilder(Material.PAPER, ""+config.getCaptureRate().getHigh().getNumber()).build();
                content[2][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack != null && itemStack.getType()!= Material.AIR){
                    switch (slot){
                        case OFFSET + 2:
                            config.setCaptureRate(GameConfig.Choice.low);
                            view.setPage(0);
                            break;
                        case OFFSET + 4:
                            config.setCaptureRate(GameConfig.Choice.medium);
                            view.setPage(0);
                            break;
                        case OFFSET + 6:
                            config.setCaptureRate(GameConfig.Choice.high);
                            view.setPage(0);
                            break;
                        case 2*OFFSET + 8:
                            view.setPage(0);
                            break;
                    }

                }
            }
        };


        return pages;
    }

    @Override
    public int getPageCount() {
        return 4;
    }
}
