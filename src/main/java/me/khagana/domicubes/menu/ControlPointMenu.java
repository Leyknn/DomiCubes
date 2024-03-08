package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.Menu;
import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.pages.Page;
import fr.dwightstudio.dsmapi.pages.PageType;
import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import me.khagana.domicubes.controlpoint.ControlPoint;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ControlPointMenu extends Menu {

    @Getter @Setter
    private static boolean isCreatingControlPoint = false;
    private static final ItemStack previous = new ItemBuilder(Material.ARROW, "Previous").build();
    private static final int OFFSET = 9;

    private static int radius=5;

    @Override
    public String getName() {
        return "Control Point Menu";
    }

    @Override
    public Page[] getPages() {
        Page[] pages = new Page[3];

        pages[0] = new Page() {
            final ItemStack newControlPoint = new ItemBuilder(Material.BEACON, "New Control Point").build();
            @Override
            public String getName() {
                return "Control Point Menu";
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][2] = new ItemBuilder(Material.BEACON, "View Current Control Points").lore(GameManager.getInstance().getControlPointList().size()+"").build();
                content[1][6] = newControlPoint;
                content[2][8] = previous;
                return getPageType().flatten(content);
            }


            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack != null && itemStack.getType()!=Material.AIR){
                    switch (slot){
                        case OFFSET + 2:
                            view.setPage(1);
                            break;
                        case OFFSET + 6:
                            if(!isCreatingControlPoint){
                                radius=5;
                                view.setPage(2);
                            } else {
                                view.getPlayer().sendMessage(ChatColor.RED + "Only one control point can be created at once");
                            }
                            break;
                        case 26:
                            new LobbyMenu().open(view.getPlayer(), 0);
                    }
                }
            }
        };

        pages[1] = new Page() {
            @Override
            public String getName() {
                return "Current Control Points";
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                int i=1, j=1, k=1;
                for (ControlPoint cp : GameManager.getInstance().getControlPointList()){
                    if (j==8){
                        i+=1;
                        j=1;
                    }
                    Location center = cp.getCenter();
                    content[i][j++] = new ItemBuilder(Material.BEACON, "" + k++).lore("Center: " + center.getBlockX() + ";" + center.getBlockY() + ";" + center.getBlockZ()).lore("Radius: " + cp.getRadius()).build();
                }
                content[i+1][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                int size = GameManager.getInstance().getControlPointList().size();
                if (size <= 7){
                    return PageType.CHEST;
                } else if (size <= 14){
                    return PageType.CHEST_PLUS;
                } else if (size <= 21){
                    return PageType.CHEST_PLUS_PLUS;
                } else {
                    return PageType.DOUBLE_CHEST;
                }
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack!=null && itemStack.getType()!=Material.AIR){
                    if (itemStack.getType()==Material.ARROW){
                        view.setPage(0);
                    } else {
                        view.getPlayer().teleport(GameManager.getInstance().getControlPointList().get(Integer.parseInt(itemStack.getItemMeta().getDisplayName())-1).getCenter());
                    }
                }
            }
        };

        pages[2] = new Page() {
            final ItemStack minus = new ItemBuilder(Material.PAPER, "-").build();
            final ItemBuilder newControlPoint = new ItemBuilder(Material.BEACON, "New Control Point Radius");
            final ItemStack plus = new ItemBuilder(Material.PAPER, "+").build();

            @Override
            public String getName() {
                return "New Control Point";
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();
                content[1][3] = minus;
                content[1][4] = newControlPoint.amount(radius).build();
                content[1][5] = plus;
                content[2][8] = previous;
                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack!=null && itemStack.getType()!=Material.AIR){
                    switch (slot){
                        case OFFSET + 3 :
                            if (radius>1){
                                radius-=1;
                                view.setPage(2);
                            } else {
                                view.getPlayer().sendMessage(ChatColor.RED + "A control point must have a positive radius");
                            }
                            break;
                        case OFFSET + 4:
                            ItemStack beaconToBePlaced = new ItemBuilder(Material.BEACON, "Place at new control point location").lore("Radius: " + radius).build();
                            view.getPlayer().getInventory().addItem(beaconToBePlaced);
                            isCreatingControlPoint=true;
                            view.close();
                            break;
                        case OFFSET + 5:
                            if (radius<30){
                                radius+=1;
                                view.setPage(2);
                            } else {
                                view.getPlayer().sendMessage(ChatColor.RED + "A control point can't have a radius larger than 30 blocks");
                            }
                            break;
                        case 26:
                            view.setPage(0);
                    }
                }
            }
        };

        return pages;
    }

    @Override
    public int getPageCount() {
        return 3;
    }
}
