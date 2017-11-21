package farmtechs.autofarm;

/**
 * Created by Javier on 11/10/2017.
 */

public class MoistureSensor {
    private int min_moisture; //percentage
    private int moisture; //current percentage
    private int max_moisture; //percentage
    private int check_interval; //hours
    private int water_interval; //seconds

    /*constructor that sets every value passed*/
    public MoistureSensor(int min_moisture, int max_moisture, int check_interval, int water_interval){ //maybe not needed
        this.min_moisture = min_moisture;
        this.max_moisture = max_moisture;
        this.check_interval = check_interval;
        this.water_interval = water_interval;
    }

    /*overridden constructor that's only given a
    * sensor number(from 1-3) and sets default values*/
    public MoistureSensor(){
        this.min_moisture = 50;
        this.moisture = getSensorReading(this);
        this.max_moisture = 90;
        this.check_interval = 5;
        this.water_interval = 20;
    }

    public int getMinMoisture() {
        return min_moisture;
    }

    public int getMaxMoisture() {
        return max_moisture;
    }

    public int getCheckInterval() {
        return check_interval;
    }

    public int getWaterInterval() {
        return water_interval;
    }

    public void setCheckInterval(int new_interval){
        check_interval = new_interval;
    }

    public int getMoisture(){
        return this.getSensorReading(this);
    }

    private int getSensorReading(MoistureSensor sensor){
        int moisture = 0; //might need to be a float for precision
        //procedure to get moisture reading from the sensor
        return moisture;
    }

}