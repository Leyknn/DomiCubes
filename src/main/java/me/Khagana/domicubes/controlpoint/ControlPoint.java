package me.Khagana.domicubes.ControlPoint;

import me.Khagana.domicubes.ChunkManager;
import me.Khagana.domicubes.GameManager;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class ControlPoint {
    private final Location centre;
    private final int radius;

    public ControlPoint(Location loc, int radius, boolean testing){
        this.radius = radius;
        this.centre = loc;
        if (!testing){
            GameManager.getInstance().addControlPoint(this, this.getOverlappedChunk());
        }
    }

    public Chunk getChunk(){
        return this.centre.getChunk();
    }

    /**
     * test if a chunk contains a part of the control point
     * calculate squared distance between control point centre and closest side or corner of the chunk
     * @param chunk : tested chunk
     * @return true if the chunk contains a part of the control point
     */
    public boolean isInChunk(Chunk chunk){
        int halfLength = 8;
        // half of a chunk's length
        int cx = chunk.getX()*16 + 8;
        int cz = chunk.getZ()*16 + 8;
        // cx, cz : coordinate of middle of the chunk
        int squaredDist = 0, t;
        // squaredDist : squared distance between cp centre and side of the chunk
        int dx = centre.getBlockX() - cx;
        int dz = centre.getBlockZ() - cz;

        //on x-axis
        t = dx + halfLength;
        if (t<0){
            squaredDist = t*t;
        } else {
            t = dx - halfLength;
            if (t>0){
                squaredDist = t*t;
            }
        }

        //on y-axis
        t = dz + halfLength;
        if (t<0){
            squaredDist += t*t;
        } else {
            t = dz-halfLength;
            if (t>0){
                squaredDist+=t*t;
            }
        }

        return squaredDist<=radius*radius;
    }

    /**
     * Search chunks overlapping the controlPoint
     * @return Set of overlapped Chunk
     */
    public Set<Chunk> getOverlappedChunk(){
        HashSet<Chunk> testedChunks = new HashSet<>(), overlappedChunks = new HashSet<>();
        LinkedList<Chunk> queue = new LinkedList<>();
        Chunk chunk;
        queue.add(centre.getChunk()); // comment for tests
        //queue.add(centre.getWorld().getChunkAt(centre.getBlockX()/16, centre.getBlockZ()/16)); //uncomment for tests
        while(!queue.isEmpty()){
            chunk=queue.removeFirst();
            if (this.isInChunk(chunk)){
                overlappedChunks.add(chunk);
                for (Chunk c : ChunkManager.getAroundChunk(chunk)){
                    if (!testedChunks.contains(c)){
                        queue.addLast(c);
                        testedChunks.add(c);
                    }
                }
            }
        }
        return overlappedChunks;
    }
}
