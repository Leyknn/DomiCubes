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
        this.getServer().getPluginManager().registerEvents(new NewTeamListener(), this);
        this.getServer().getPluginManager().registerEvents(new HeadManger(), this);
        GameManager.getInstance().setPlugin(this);
    }

    @Override
    public void onDisable() {
    }
}
