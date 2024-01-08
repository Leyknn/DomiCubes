package me.Khagana.Domicubes.Instanciable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerStats {
    private DomicubesPlayer domicubesPlayer;
    private int maxHP;
    private double damageDealt;
    private double damageTaken;
    private double movementSpeed;
    private double RegenPerSecond;
    private double lifeSteal;

    public PlayerStats(DomicubesPlayer domicubesPlayer, int maxHP, double damageDealt, double damageTaken, double movementSpeed, double regenPerSecond, double lifeSteal){
        this.domicubesPlayer = domicubesPlayer;
        this.maxHP = maxHP;
        this.damageDealt = damageDealt;
        this.damageTaken = damageTaken;
        this.movementSpeed = movementSpeed;
        this.RegenPerSecond = regenPerSecond;
        this.lifeSteal = lifeSteal;
    }

    public PlayerStats(PlayerStats playerStats){
        this.domicubesPlayer = playerStats.getDomicubesPlayer();
        this.maxHP = playerStats.getMaxHP();
        this.damageDealt = playerStats.getDamageDealt();
        this.damageTaken = playerStats.getDamageTaken();
        this.movementSpeed = playerStats.getMovementSpeed();
        this.RegenPerSecond = playerStats.getRegenPerSecond();
        this.lifeSteal = playerStats.getLifeSteal();
    }
}
