package me.Khagana.DomincubesTests;

import me.Khagana.Domicubes.Effect.DomicubesEffect;
import me.Khagana.Domicubes.Effect.TemporaryDomicubesEffect;
import me.Khagana.Domicubes.Instanciable.DomicubesPlayer;
import me.Khagana.Domicubes.Instanciable.PlayerStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

public class DomicubesEffectTests {
    TemporaryDomicubesEffect e1,e2;
    DomicubesPlayer player;
    PlayerStats pS, pS1, pS2, pS3;
    @Before
    public void init(){
        pS = new PlayerStats(null, 20, 1, 1, 1, 0, 0);
        pS1 = new PlayerStats(null, 20, 1.2, 1, 1, 0, 0);
        pS2 = new PlayerStats(null, 20, 1.2, 0.8, 1, 0, 0);
        pS3 = new PlayerStats(null, 20, 1, 0.8, 1, 0, 0);
        player = new DomicubesPlayer(null, pS);
        e1 = Mockito.mock(TemporaryDomicubesEffect.class);
        Mockito.when(e1.getAffectedPlayer()).thenReturn(player);
        Mockito.doAnswer(invocation ->{
            e1.getAffectedPlayer().getPlayerStats().setDamageDealt(e2.getAffectedPlayer().getPlayerStats().getDamageDealt()*1.2);
            return null;
        }).when(e1).onEnable();
        e2 = Mockito.mock(TemporaryDomicubesEffect.class);
        Mockito.when(e2.getAffectedPlayer()).thenReturn(player);
        Mockito.doAnswer(invocation ->{
            e2.getAffectedPlayer().getPlayerStats().setDamageTaken(e2.getAffectedPlayer().getPlayerStats().getDamageTaken()*0.8);
            return null;
        }).when(e2).onEnable();
    }


    @Test
    public void DomicubesEffectTest(){
        try{
            player.addEffect(e1);
        } catch (Exception ignored){}
        assertEquals(player.getPlayerStats(), pS1);
        try{
            player.addEffect(e2);
        } catch (Exception ignored){}
        assertEquals(player.getPlayerStats(),pS2);
        try{
            player.removeEffect(e1);
        } catch (Exception ignored){}
        assertEquals(player.getPlayerStats(), pS3);
    }
}
