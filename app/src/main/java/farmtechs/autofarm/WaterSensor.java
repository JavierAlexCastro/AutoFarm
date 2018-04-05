package farmtechs.autofarm;

/**
 * Created by Javier on 11/10/2017.
 */

public class WaterSensor {
    private boolean water;

    public WaterSensor(){
        water = true; //constructor (true=has water)
    }

    public boolean getWater(){
        return this.water;
    }
}
