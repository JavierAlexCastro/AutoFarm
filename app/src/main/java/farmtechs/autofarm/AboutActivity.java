package farmtechs.autofarm;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //get resources from layout file
        final ImageView block1 = (ImageView) findViewById(R.id.block1_box);
        final ImageView block2 = (ImageView) findViewById(R.id.block2_box);
        final ImageView block3 = (ImageView) findViewById(R.id.block3_box);
        final Button change_btn = (Button) findViewById(R.id.edit_btn);

        //if the user clicks the edit_my_info button take him to the edit activity
        change_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent reg = new Intent(AboutActivity.this, EditActivity.class);
                startActivity(reg); //launch register activity
            }
        });

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        if(!settings.getBoolean("FIRST_BLOCK",false)){ //if the first block is disabled
            ColorMatrix matrix1 = new ColorMatrix();
            matrix1.setSaturation(0); //grayscale the block
            ColorMatrixColorFilter filter1 = new ColorMatrixColorFilter(matrix1);
            block1.setColorFilter(filter1);
            block1.setImageAlpha(128); //128=0.5
        }else{ //block is enabled
            block1.setColorFilter(null);
            block1.setImageAlpha(255);
        }
        if(!settings.getBoolean("SECOND_BLOCK",false)){ //if the second block is disabled
            ColorMatrix matrix2 = new ColorMatrix();
            matrix2.setSaturation(0); //grayscale
            ColorMatrixColorFilter filter2 = new ColorMatrixColorFilter(matrix2);
            block2.setColorFilter(filter2);
            block2.setImageAlpha(128); //128=0.5
        }else{ //block is enabled
            block2.setColorFilter(null);
            block2.setImageAlpha(255);
        }
        if(!settings.getBoolean("THIRD_BLOCK",false)){ //if the third block is disabled
            ColorMatrix matrix3 = new ColorMatrix();
            matrix3.setSaturation(0); //grayscale
            ColorMatrixColorFilter filter3 = new ColorMatrixColorFilter(matrix3);
            block3.setColorFilter(filter3);
            block3.setImageAlpha(128); //128=0.5
        }else{ //block is enabled
            block3.setColorFilter(null);
            block3.setImageAlpha(255);
        }


        TextView m1text = (TextView)findViewById(R.id.m1text);
        if(!settings.getBoolean("FIRST_BLOCK",true)) { //if block is disabled show "disabled" as sensor reading
            m1text.setText("disabled");
        }else {
            m1text.setText(settings.getString("SENSOR_ONE","Null")); //retrieve sensor info previously saved
        }

        TextView m2text = (TextView)findViewById(R.id.m2text);
        if(!settings.getBoolean("SECOND_BLOCK",true)) { //if block is disabled show "disabled" as sensor reading
            m2text.setText("disabled");
        }else {
            m2text.setText(settings.getString("SENSOR_TWO","Null")); //retrieve sensor info previously saved
        }

        TextView m3text = (TextView)findViewById(R.id.m3text);
        if(!settings.getBoolean("THIRD_BLOCK",true)) { //if block is disabled show "disabled" as sensor reading
            m3text.setText("disabled");
        }else {
            m3text.setText(settings.getString("SENSOR_THREE","Null")); //retrieve sensor info previously saved
        }

        TextView wtext = (TextView)findViewById(R.id.wtext);
        if(settings.getBoolean("HAS_WATER",false)) //retrieve sensor info previously saved
            wtext.setText("Yes");
        else
            wtext.setText("No");
    }

    //if the user presses the back button while on the info section go home
    @Override
    public void onBackPressed() {
        Intent home = new Intent(AboutActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
