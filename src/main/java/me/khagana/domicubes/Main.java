package me.khagana.domicubes;

import me.khagana.domicubes.controlpoint.ControlPoint;
import me.khagana.domicubes.instanciable.Color;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import me.khagana.domicubes.instanciable.PlayerStats;
import me.khagana.domicubes.instanciable.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new TeamMenuListener(), this);
        GameManager.getInstance().setPlugin(this);
        Team t = new Team(Color.BLUE, "t1");
        GameManager.getInstance().getTeamList().add(t);
        for (Player p : Bukkit.getOnlinePlayers()){
            DomicubesPlayer dp = new DomicubesPlayer(p, PlayerStats.basePlayerStats());
            dp.setTeam(t);
            GameManager.getInstance().getPlayersMap().put(p, dp);
        }
        this.getCommand("cp").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                ControlPoint cp = new ControlPoint(((Player) sender).getLocation(), 5);
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

        this.getCommand("sb").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                DomicubesScoreboard.createScoreboard((Player) sender);
                return true;
            }
        });
        new ControlPointScript().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
    }
}
