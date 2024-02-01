package me.Khagana.Domicubes;

import me.Khagana.Domicubes.ControlPoint.ControlPoint;
import me.Khagana.Domicubes.Instanciable.Color;
import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;
import me.Khagana.Domicubes.Instanciable.PlayerStats;
import me.Khagana.Domicubes.Instanciable.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        GameManager.getInstance().setPlugin(this);
        Team t = new Team(Color.BLUE, "t1");
        for (Player p : Bukkit.getOnlinePlayers()){
            DomicubesPlayer dp = new DomicubesPlayer(p, PlayerStats.basePlayerStats());
            dp.setTeam(t);
            GameManager.getInstance().getPlayersMap().put(p, dp);
        }
        this.getCommand("cp").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                ControlPoint cp = new ControlPoint(((Player) sender).getLocation(), 5, false, 20, 5);
                cp.getTeamPresence().put(t, 0);
                sender.sendMessage("control point created");
                return true;
            }
        });
        this.getCommand("vp").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                sender.sendMessage("" + GameManager.getInstance().getPlayersMap().get((Player) sender).getTeam().getVP());
                return true;
            }
        });
        new ControlPointScript().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
    }
}
