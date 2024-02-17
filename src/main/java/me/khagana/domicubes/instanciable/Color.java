package me.khagana.domicubes.instanciable;

import lombok.Getter;
import me.khagana.domicubes.particuleseffect.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum Color {
    BLUE(0,0,255, DyeColor.BLUE),
    RED(255,0,0, DyeColor.RED),
    YELLOW(255, 255, 0, DyeColor.YELLOW),
    GREEN(0, 128, 0, DyeColor.GREEN),
    NEUTRAL(64,64,64, DyeColor.GRAY);

    private final int red;
    private final int green;
    private final int blue;

    @Getter private final DyeColor dyeColor;

    @Getter private final ParticleEffect.OrdinaryColor ordinaryColor;

    Color(int red, int green, int blue, DyeColor dyeColor){
        this.red=red;
        this.green=green;
        this.blue=blue;
        this.ordinaryColor = new ParticleEffect.OrdinaryColor(red, green, blue);
        this.dyeColor=dyeColor;
    }

    public static Color getColorByDyeColor(DyeColor dyeColor){
        switch (dyeColor) {
            case BLUE:
                return BLUE;
            case RED:
                return RED;
            case YELLOW:
                return YELLOW;
            case GREEN:
                return GREEN;
            default:
                return NEUTRAL;
        }
    }

    public static ChatColor getChatColorByDyeColor(DyeColor dyeColor) {
        switch (dyeColor){
            case BLUE:
                return ChatColor.BLUE;
            case RED:
                return ChatColor.RED;
            case YELLOW:
                return ChatColor.YELLOW;
            case GREEN:
                return ChatColor.GREEN;
            default:
                return ChatColor.GRAY;
        }
    }
}
