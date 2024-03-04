package me.khagana.domicubes.controlpoint;

import lombok.Getter;
import lombok.Setter;
import me.khagana.domicubes.ChunkManager;
import me.khagana.domicubes.GameManager;
import me.khagana.domicubes.instanciable.NeutralTeam;
import me.khagana.domicubes.instanciable.Team;
import me.khagana.domicubes.particuleseffect.ParticleEffect;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class ControlPoint {
    private Location centre;
    private int radius;

    @Getter
    private Map<Team, Integer> teamPresence;

    @Getter private Team controllingTeam;

    private Team capturingTeam;

    private int controlPercentage;

    @Setter private static int  captureRate;

    @Setter private static int VPPerSecond;

    public ControlPoint(Location loc, int radius, boolean testing, int captureRate, int VPRate){
        this.radius = radius;
        this.centre = loc;
        this.controllingTeam = NeutralTeam.getInstance();
        this.capturingTeam = NeutralTeam.getInstance();
        this.controlPercentage=0;
        if (!testing){
            GameManager.getInstance().addControlPoint(this, this.getOverlappedChunk());
        }
        this.teamPresence = new HashMap<>();
        new BukkitRunnable(){
            @Override
            public void run() {
                for (int angle=0; angle<360; angle +=5) {
                    double z = radius * Math.sin(angle * Math.PI / 180);
                    double x = radius * Math.cos(angle * Math.PI / 180);

                    ParticleEffect.REDSTONE.display(controllingTeam.getColor().getOrdinaryColor(), new Location(centre.getWorld(), centre.getBlockX() + x + 0.5, centre.getBlockY() + 0.25, centre.getBlockZ() + z + 0.5), 200);
                }
            }
        }.runTaskTimerAsynchronously(GameManager.getInstance().getPlugin(), 0, 10);
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
                squaredDist = t*t;            }
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
     * Search chunks that overlap the controlPoint
     * @return Set of overlapped Chunk
     */
    public Set<Chunk> getOverlappedChunk(){
        HashSet<Chunk> testedChunks = new HashSet<Chunk>(), overlappedChunks = new HashSet<Chunk>();
        LinkedList<Chunk> queue = new LinkedList<Chunk>();
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

    public boolean isPresent(Player p){
        return (square(this.centre.getBlockX() - p.getLocation().getBlockX()) + square(this.centre.getBlockZ() - p.getLocation().getBlockZ()) <= square(this.radius));
    }

    private int square(int a){
        return a*a;
    }

    public void resetTeamPresence(){
        this.teamPresence.replaceAll((key, value) -> value=0);
    }

    public void addTeamPresence(Team team){
        this.teamPresence.put(team, this.teamPresence.get(team)+1);
    }

    public void updatePresence(){
        int max = Collections.max(teamPresence.values());
        int sum = teamPresence.values().stream().mapToInt(Integer::intValue).sum();
        if (!(max == 0)) {
            List<Team> teams = teamPresence.entrySet().stream().filter(entry -> entry.getValue() == max).map(Map.Entry::getKey).collect(Collectors.toList());
            Team team0=teams.get(0);
            if (teams.size() == 1) {
                if (team0 != controllingTeam) {
                    if (team0 == capturingTeam) {
                        controlPercentage += captureRate * max / sum;
                        if (controlPercentage >= 100) {
                            controllingTeam = capturingTeam;
                            controlPercentage = 0;
                        }
                    } else {
                        controlPercentage = captureRate * max / sum;
                        capturingTeam = teams.get(0);
                    }
                } else if (controlPercentage!= 0){
                    controlPercentage = 0;
                }
            }
        } else if (controlPercentage!= 0){
            controlPercentage = 0;
        }
        controllingTeam.addVictoryPoint(VPPerSecond);
    }
}
