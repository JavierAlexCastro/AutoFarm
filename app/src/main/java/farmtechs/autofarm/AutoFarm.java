package farmtechs.autofarm;

import android.app.Application;

/**
 * Created by Javier on 11/14/2017.
 */

class AutoFarm extends Application {
    static MoistureSensor mSensor1;
    static MoistureSensor mSensor2;
    static MoistureSensor mSensor3;
    static WaterSensor wSensor;

    public static void setMSensor1(MoistureSensor mSensor){
        mSensor1 = mSensor;
    }
    public static MoistureSensor getmSensor1(){
        return mSensor1;
    }

    public static void setMSensor2(MoistureSensor mSensor){
        mSensor2 = mSensor;
    }
    public static MoistureSensor getmSensor2(){
        return mSensor2;
    }

    public static void setMSensor3(MoistureSensor mSensor){
        mSensor3 = mSensor;
    }
    public static MoistureSensor getmSensor3(){
        return mSensor3;
    }

    public static void setWSensor(WaterSensor w_Sensor){
        wSensor = w_Sensor;
    }
    public static WaterSensor getWSensor(){
        return wSensor;
    }


}
