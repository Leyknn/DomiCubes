package me.khagana.domicubes.menu;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.SimpleMenu;
import fr.dwightstudio.dsmapi.pages.PageType;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.ItemBuilder;
import me.khagana.domicubes.Main;
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

import java.util.ArrayList;
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
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }
                    if(validTeamName(stateSnapshot.getText())) {
                        DyeColor color = ((Wool)itemStack.getData()).getColor();
                        ItemStack newTeam = new ItemStack(Material.BANNER);
                        BannerMeta meta = (BannerMeta) newTeam.getItemMeta();
                        meta.setBaseColor(color);
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(stateSnapshot.getText());
                        meta.setLore(lore);
                        meta.setDisplayName("Place at "+stateSnapshot.getText() + " base location");
                        newTeam.setItemMeta(meta);


                        DisplayTeamMenu.setCreatingTeam(true);
                        view.getPlayer().getInventory().addItem(newTeam);
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("Invalid name"));
                    }
                })
                .text("Choose a team name")
                .plugin(Main.getInstance())
                .open(view.getPlayer());
    }

    private static boolean validTeamName(String teamName){
        if (teamName.contains("§") || teamName.contains("&") || teamName.contains("\\")) {
            return false;
        } else if (GameManager.getInstance().getTempsTeams().stream().map(
                TempTeam::getName
        ).collect(Collectors.toList()).contains(teamName)) {
            return false;
        }
        return true;
    }

    private static boolean validColor(Color color){
        if (color==Color.NEUTRAL){
            return false;
        } else if (GameManager.getInstance().getTempsTeams().stream().map(
                TempTeam::getColor
        ).collect(Collectors.toList()).contains(color)) {
            return false;
        }
        return true;
    }
}