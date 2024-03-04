package me.khagana.domicubes;

import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.pages.Page;
import fr.dwightstudio.dsmapi.pages.PageType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Getter
public class GameConfig {

    private Parameter VPtoWin;
    private Parameter VPperSec;
    private Parameter captureRate;

    public void setVPperSec(Choice choice) {
        switch (choice){
            case low:
                VPperSec.setCurrent(VPperSec.getLow());
                break;
            case medium:
                VPperSec.setCurrent(VPperSec.getMedium());
                break;
            case high:
                VPperSec.setCurrent(VPperSec.getHigh());
                break;
        }
    }

    public void setVPtoWin(Choice choice) {
        switch (choice){
            case low:
                VPtoWin.setCurrent(VPtoWin.getLow());
                break;
            case medium:
                VPtoWin.setCurrent(VPtoWin.getMedium());
                break;
            case high:
                VPtoWin.setCurrent(VPtoWin.getHigh());
                break;
        }
    }

    public void setCaptureRate(Choice choice) {
        switch (choice){
            case low:
                captureRate.setCurrent(captureRate.getLow());
                break;
            case medium:
                captureRate.setCurrent(captureRate.getMedium());
                break;
            case high:
                captureRate.setCurrent(captureRate.getHigh());
                break;
        }
    }

    public GameConfig(){
        VPtoWin = new Parameter(500, 1000, 2000, "Victory Point number to win");
        VPperSec = new Parameter(1, 2, 5, "Victory point generated per sec");
        captureRate = new Parameter(5, 10, 20, "Control Point capture rate");
    }

    @Getter
    public static class Parameter{
        private final Possibility low;
        private final Possibility medium;
        private final Possibility high;
        @Setter
        private Possibility current;
        private String name;

        public Parameter(int low, int medium, int high, String name){
            this.low= new Possibility(low);
            this.medium= new Possibility(medium);
            this.high= new Possibility(high);
            this.current=this.medium;
            this.name=name;
        }
    }
    @Getter
    public static class Possibility{
        private final int number;

        public Possibility(int n){
            this.number = n;
        }
    }

    public enum Choice{
        low,
        medium,
        high;
    }


}
