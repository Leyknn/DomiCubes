package me.Khagana.DomincubesTests;

import me.khagana.domicubes.effect.DomicubesEffect;
import me.khagana.domicubes.effect.Temporary;
import me.khagana.domicubes.instanciable.DomicubesPlayer;
import me.khagana.domicubes.instanciable.PlayerStats;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
abstract class DomicubesEffectTester extends DomicubesEffect implements Temporary{
    @Override
    public void onEnable() {

    }

    public abstract DomicubesPlayer getDomicubesPlayer();
}
public class DomicubesEffectTests {
    DomicubesEffectTester e1,e2;
    DomicubesPlayer player;
    PlayerStats pS, pS1, pS2, pS3;
    @Before
    public void init(){
        pS = new PlayerStats(null, 20, 1, 1, 1, 0, 0);
        pS1 = new PlayerStats(null, 20, 1.2, 1, 1, 0, 0);
        pS2 = new PlayerStats(null, 20, 1.2, 0.8, 1, 0, 0);
        pS3 = new PlayerStats(null, 20, 1, 0.8, 1, 0, 0);
        player = new DomicubesPlayer(null, pS);
        e1 = Mockito.mock(DomicubesEffectTester.class);
        Mockito.when(e1.getDomicubesPlayer()).thenReturn(player);
        Mockito.doAnswer(invocation ->{
            e1.getDomicubesPlayer().getPlayerStats().setDamageDealt(e2.getDomicubesPlayer().getPlayerStats().getDamageDealt()*1.2);
            return null;
        }).when(e1).onEnable();
        e2 = Mockito.mock(DomicubesEffectTester.class);
        Mockito.when(e2.getDomicubesPlayer()).thenReturn(player);
        Mockito.doAnswer(invocation ->{
            e2.getDomicubesPlayer().getPlayerStats().setDamageTaken(e2.getDomicubesPlayer().getPlayerStats().getDamageTaken()*0.8);
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
