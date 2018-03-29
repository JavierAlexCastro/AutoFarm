package farmtechs.autofarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        final Button clear_btn = (Button) findViewById(R.id.clear_button);

        clear_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("FIRST_TIME", true);
                editor.putBoolean("FIRST_BLOCK", false);
                editor.putBoolean("SECOND_BLOCK", false);
                editor.putBoolean("THIRD_BLOCK", false);
                editor.apply();

                Intent home = new Intent(MonitorActivity.this, HomeActivity.class);
                startActivity(home);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getImage();
            }
        }, 6000);   //5 seconds


    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(MonitorActivity.this, HomeActivity.class);
        startActivity(home);
    }

    public void getImage() {

        ImageView picture=(ImageView)findViewById(R.id.camera_image);
        Picasso.with(picture.getContext())
                .load("http://129.107.116.224/image.jpg")
                .resize(250,250).into(picture);
    }
}
