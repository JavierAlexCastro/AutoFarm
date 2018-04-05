package farmtechs.autofarm;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final ImageView block1 = (ImageView) findViewById(R.id.block1_box);
        final ImageView block2 = (ImageView) findViewById(R.id.block2_box);
        final ImageView block3 = (ImageView) findViewById(R.id.block3_box);

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        if(!settings.getBoolean("FIRST_BLOCK",false)){
            ColorMatrix matrix1 = new ColorMatrix();
            matrix1.setSaturation(0); //grayscale
            ColorMatrixColorFilter filter1 = new ColorMatrixColorFilter(matrix1);
            block1.setColorFilter(filter1);
            block1.setImageAlpha(128); //128=0.5
        }else{
            block1.setColorFilter(null);
            block1.setImageAlpha(255);
        }
        if(!settings.getBoolean("SECOND_BLOCK",false)){
            ColorMatrix matrix2 = new ColorMatrix();
            matrix2.setSaturation(0); //grayscale
            ColorMatrixColorFilter filter2 = new ColorMatrixColorFilter(matrix2);
            block2.setColorFilter(filter2);
            block2.setImageAlpha(128); //128=0.5
        }else{
            block2.setColorFilter(null);
            block2.setImageAlpha(255);
        }
        if(!settings.getBoolean("THIRD_BLOCK",false)){
            ColorMatrix matrix3 = new ColorMatrix();
            matrix3.setSaturation(0); //grayscale
            ColorMatrixColorFilter filter3 = new ColorMatrixColorFilter(matrix3);
            block3.setColorFilter(filter3);
            block3.setImageAlpha(128); //128=0.5
        }else{
            block3.setColorFilter(null);
            block3.setImageAlpha(255);
        }


        TextView m1text = (TextView)findViewById(R.id.m1text);
        if(!settings.getBoolean("FIRST_BLOCK",true)) {
            m1text.setText("disabled");
        }else {
            m1text.setText(String.valueOf(AutoFarm.getmSensor1().getMoisture()) + "");
        }

        TextView m2text = (TextView)findViewById(R.id.m2text);
        if(!settings.getBoolean("SECOND_BLOCK",true)) {
            m2text.setText("disabled");
        }else {
            m2text.setText(String.valueOf(AutoFarm.getmSensor2().getMoisture()) + "");
        }

        TextView m3text = (TextView)findViewById(R.id.m3text);
        if(!settings.getBoolean("THIRD_BLOCK",true)) {
            m3text.setText("disabled");
        }else {
            m3text.setText(String.valueOf(AutoFarm.getmSensor3().getMoisture()) + "");
        }

        TextView wtext = (TextView)findViewById(R.id.wtext);
        if(AutoFarm.wSensor.getWater())
            wtext.setText("Yes");
        else
            wtext.setText("No");
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(AboutActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
