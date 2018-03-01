package farmtechs.autofarm;

/**
 * Created by Javier on 11/10/2017.
 */

public class WaterSensor {
    private int water_level; //current level
    private int low_level; //warn user
    private int min_level; //halt operations

    public WaterSensor(){
        water_level = getSensorReading(); //temporal. Later changes to sensor reading
        low_level = 20; //percent
        min_level = 5; //percent
    }

    public int getLevel(){
        return this.getSensorReading();
    }

    /*encapsulates*/
    private int getSensorReading(){
        int level = 0; //temporal level
        //procedure to get water level from sensor
        //must return an int
        //probably will be need to be a float
        return level;
    }
}
