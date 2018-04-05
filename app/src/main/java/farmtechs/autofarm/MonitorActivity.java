package farmtechs.autofarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        //dialog to launch a progress bar while the app waits to retrieve image from server
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Fetching image from server..",
                true);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 7000);

        //////////////////button set to reset all pumps to disabled
        /*final Button clear_btn = (Button) findViewById(R.id.clear_button);

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
        });*/

        //Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getImage();
            }
        }, 6000);   //wait 6 seconds, then attempt to get image from server (why? PiCamera takes 6 secs to execute)


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
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(300,300).into(picture);
    }
}
