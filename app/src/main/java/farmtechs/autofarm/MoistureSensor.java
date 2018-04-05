package farmtechs.autofarm;

import android.util.Log;

/**
 * Created by Javier on 11/10/2017.
 */

public class MoistureSensor {
    private int moisture; //current percentage

    /*constructor sets default values*/
    public MoistureSensor(){
        this.moisture = 0;
    }

    public void setMoisture(String m) {
        this.moisture = Integer.parseInt(m);
    }

    public int getMoisture(){
        return this.moisture;
    }

}