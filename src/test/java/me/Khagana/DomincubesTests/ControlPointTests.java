package me.Khagana.DomincubesTests;

import me.Khagana.Domincubes.ControlPoint.ControlPoint;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

class ControlPointTester extends ControlPoint{

    public ControlPointTester(Location loc, int radius, boolean testing) {
        super(loc, radius, testing);
    }
}
public class ControlPointTests {
    private World world;
    private ControlPoint cp1, cp2, cp3, cp4;
    @Before
    public void init(){
        world = Mockito.mock(World.class);
        for (int i=-2; i<=2; i++){
            for (int j = -2; j<=2; j++){
                Mockito.when(world.getChunkAt(i,j)).thenReturn(Mockito.mock(Chunk.class));
                Mockito.when(world.getChunkAt(i,j).getX()).thenReturn(i);
                Mockito.when(world.getChunkAt(i,j).getZ()).thenReturn(j);
                Mockito.when(world.getChunkAt(i,j).getWorld()).thenReturn(world);
            }
        }
        cp1 =new ControlPointTester(new Location(world, 8, 64, 8), 3, true);
        cp2 =new ControlPointTester(new Location(world, 8, 64, 8), 10, true);
        cp3 =new ControlPointTester(new Location(world, 8, 64, 8), 16, true);
        cp4 =new ControlPointTester(new Location(world, 15, 64, 15), 5, true);
    }

    @Test
    public void cp1Test(){
        assertTrue(cp1.isInChunk(world.getChunkAt(0,0)));
        assertFalse(cp1.isInChunk(world.getChunkAt(-2,-2)));
        assertTrue(cp1.getOverlappedChunk().contains(world.getChunkAt(0,0)));
        assertEquals(cp1.getOverlappedChunk().size(), 1);
    }

    @Test
    public void cp2Test(){
        assertTrue(cp2.isInChunk(world.getChunkAt(0,0)));
        assertFalse(cp2.isInChunk(world.getChunkAt(-2,-2)));
        assertTrue(cp2.getOverlappedChunk().contains(world.getChunkAt(0,0)));
        assertTrue(cp2.getOverlappedChunk().contains(world.getChunkAt(-1,0)));
        assertTrue(cp2.getOverlappedChunk().contains(world.getChunkAt(1,0)));
        assertTrue(cp2.getOverlappedChunk().contains(world.getChunkAt(0,-1)));
        assertTrue(cp2.getOverlappedChunk().contains(world.getChunkAt(0,1)));
        assertEquals(cp2.getOverlappedChunk().size(), 5);
    }

    @Test
    public void cp3Test(){
        assertTrue(cp3.isInChunk(world.getChunkAt(0,0)));
        assertFalse(cp3.isInChunk(world.getChunkAt(-2,-2)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(-1,-1)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(-1,0)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(-1,1)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(0,-1)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(0,0)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(0,1)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(1,-1)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(1,0)));
        assertTrue(cp3.getOverlappedChunk().contains(world.getChunkAt(1,1)));
        assertEquals(cp3.getOverlappedChunk().size(), 9);
    }

    @Test
    public void cp4Test(){
        assertTrue(cp4.isInChunk(world.getChunkAt(0,0)));
        assertFalse(cp4.isInChunk(world.getChunkAt(-2,-2)));
        assertTrue(cp4.getOverlappedChunk().contains(world.getChunkAt(0,0)));
        assertTrue(cp4.getOverlappedChunk().contains(world.getChunkAt(0,1)));
        assertTrue(cp4.getOverlappedChunk().contains(world.getChunkAt(1,0)));
        assertTrue(cp4.getOverlappedChunk().contains(world.getChunkAt(1,1)));
        assertEquals(cp4.getOverlappedChunk().size(), 4);
    }
}
