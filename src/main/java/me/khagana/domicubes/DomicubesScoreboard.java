package me.khagana.domicubes;

import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import me.khagana.domicubes.instanciable.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.lang.management.ManagementFactory;

public class DomicubesScoreboard {
    public static void createScoreboard(Player player){
        int pos = 0;
        String cpScore = "";
        DomicubesPlayer domicubesPlayer = GameManager.getInstance().getPlayersMap().get(player);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective(player.getDisplayName(), "dummy");
        obj.setDisplayName(ChatColor.DARK_RED + " >> " + ChatColor.RED + "Domicubes" + ChatColor.DARK_RED + " <<");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Team team : GameManager.getInstance().getTeamList()){
            Score score = obj.getScore(team.getColor().getChatColor() + team.getName() +" : " + team.getColor().getDarkChatColor() + team.getVP());
            score.setScore(pos++);
        }

        Score score = obj.getScore(" ");
        score.setScore(pos++);

        for (ControlPoint cp : GameManager.getInstance().getControlPointList()){
            if (cp.getControllingTeam()==domicubesPlayer.getTeam()){
                cpScore+=ChatColor.GREEN + "✔";
            } else {
                cpScore+=ChatColor.RED + "✘";
            }
        }

        score = obj.getScore(ChatColor.GRAY + "Points : " + ChatColor.RESET + cpScore);
        score.setScore(pos++);

        score = obj.getScore("");
        score.setScore(pos++);

        player.setScoreboard(scoreboard);
    }
}
