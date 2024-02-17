package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import me.khagana.domicubes.instanciable.Color;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.material.Banner;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class NewTeamMenu extends SimpleMenu {
    @Override
    public String getName() {
        return "Create new team";
    }

    @Override
    public ItemStack[] getContent() {
        ItemStack[][] content = getPageType().getBlank2DArray();
        int i = 1;
        for (Color color : Color.values()) {
            if (validColor(color)) {
                ItemStack teamColor = new Wool(color.getDyeColor()).toItemStack();
                teamColor.setAmount(1);
                content[1][i++] = teamColor;
            }
        }
        return getPageType().flatten(content);
    }

    @Override
    public PageType getPageType() {
        return PageType.CHEST;
    }

    @Override
    public void onClick(MenuView view, ClickType clickType, int i, ItemStack itemStack) {
        new AnvilGUI.Builder()
                .onClose(stateSnapshot -> {})
                .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }
                    if(validTeamName(stateSnapshot.getText())) {
                        DyeColor color = ((Wool)itemStack.getData()).getColor();
                        ItemStack newTeam = new ItemStack(Material.BANNER);
                        BannerMeta meta = (BannerMeta) newTeam.getItemMeta();
                        meta.setBaseColor(color);
                        meta.setDisplayName(Color.getChatColorByDyeColor(color) + stateSnapshot.getText());
                        newTeam.setItemMeta(meta);
                        TeamMenu.getTeams().add(newTeam);
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("Invalid name"));
                    }
                })                                                //prevents the inventory from being closed
                .text("Choose a team name")                              //sets the text the GUI should start with
                .plugin(GameManager.getInstance().getPlugin())                                          //set the plugin instance
                .open(view.getPlayer());
    }

    private static boolean validTeamName(String teamName){
        if (teamName.contains("§") || teamName.contains("&") || teamName.contains("\\")) {
            return false;
        }
        return true;
    }

    private static boolean validColor(Color color){
        if (color==Color.NEUTRAL){
            return false;
        } else if (TeamMenu.getTeams().stream().map(
                itemStack -> Color.getColorByDyeColor(((BannerMeta) itemStack.getItemMeta()).getBaseColor())
                ).collect(Collectors.toList()).contains(color)) {
            return false;
        }
        return true;
    }
}
