package me.khagana.domicubes.instanciable;

public class NeutralTeam extends Team{

    private static String NAME = "Neutral";

    private static NeutralTeam instance;
    public NeutralTeam(Color color, String name) {
        super(color, name);
    }

    @Override
    public void addVictoryPoint(int vp){}

    public static NeutralTeam getInstance(){
        if (instance == null){
            instance = new NeutralTeam(Color.NEUTRAL, NAME);
        }
        return instance;
    }
}
