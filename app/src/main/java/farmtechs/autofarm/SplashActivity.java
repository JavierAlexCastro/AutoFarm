package farmtechs.autofarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        if(settings.getBoolean("FIRST_TIME",true)){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.putBoolean("FIRST_BLOCK", false);
            editor.putBoolean("SECOND_BLOCK", false);
            editor.putBoolean("THIRD_BLOCK", false);
            editor.putString("IP","Null");
            editor.putString("SENSOR_ONE", "Null");
            editor.putString("SENSOR_TWO", "Null");
            editor.putString("SENSOR_THREE", "Null");
            editor.putBoolean("HAS_WATER",false);
            editor.apply();
        }


        Intent home = new Intent(this, RegisterActivity.class);
        startActivity(home);
        finish();
    }
}
