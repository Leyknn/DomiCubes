package me.Khagana.Domincubes;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class ChunkManager {

    public static Set<Chunk> getAroundChunk(Chunk chunk){
        HashSet<Chunk> aroundChunks = new HashSet<Chunk>();
        World world = chunk.getWorld();
        int baseX = chunk.getX();
        int baseZ = chunk.getZ();
        for (int i = -1; i<=1; i++){
            for (int j = -1; j<=1; j++){
                if (i!=0 || j!=0){
                    aroundChunks.add(world.getChunkAt(baseX + i, baseZ + j));
                }
            }
        }
        return aroundChunks;
    }
}
