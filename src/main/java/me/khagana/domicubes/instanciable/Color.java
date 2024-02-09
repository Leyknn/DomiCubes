package me.khagana.domicubes.instanciable;

import lombok.Getter;
import me.khagana.domicubes.particuleseffect.ParticleEffect;
import org.bukkit.ChatColor;

public enum Color {
    BLUE(0,0,255, ChatColor.BLUE, ChatColor.DARK_BLUE),
    RED(255,0,0, ChatColor.RED, ChatColor.DARK_RED),
    YELLOW(255, 255, 0, ChatColor.YELLOW, ChatColor.GOLD),
    GREEN(0, 128, 0, ChatColor.GREEN, ChatColor.DARK_GREEN),
    NEUTRAL(64,64,64, ChatColor.GRAY, ChatColor.DARK_GRAY);

    private final int red;
    private final int green;
    private final int blue;

    @Getter private final ChatColor chatColor;
    @Getter private final ChatColor darkChatColor;

    @Getter private final ParticleEffect.OrdinaryColor ordinaryColor;

    Color(int red, int green, int blue, ChatColor chatColor, ChatColor darkChatColor){
        this.red=red;
        this.green=green;
        this.blue=blue;
        this.chatColor=chatColor;
        this.darkChatColor = darkChatColor;
        this.ordinaryColor = new ParticleEffect.OrdinaryColor(red, green, blue);
    }


}
