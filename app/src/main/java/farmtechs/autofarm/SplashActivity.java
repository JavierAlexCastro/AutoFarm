package farmtechs.autofarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MoistureSensor ms1 = new MoistureSensor();
        AutoFarm.setMSensor1(ms1);

        MoistureSensor ms2 = new MoistureSensor();
        AutoFarm.setMSensor2(ms2);

        MoistureSensor ms3 = new MoistureSensor();
        AutoFarm.setMSensor3(ms3);

        WaterSensor ws = new WaterSensor();
        AutoFarm.setWSensor(ws);

        Intent home = new Intent(this, HomeActivity.class);
        startActivity(home);
        finish();
    }
}
