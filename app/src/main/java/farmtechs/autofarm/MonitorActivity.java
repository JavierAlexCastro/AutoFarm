package farmtechs.autofarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        // textView for timestamp
        TextView timestamp = (TextView)findViewById(R.id.time);
        String text = getString(R.string.subtitle, currentDateTimeString);
        timestamp.setText(text);

        //dialog to launch a progress bar while the app waits to retrieve image from server
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Fetching image from server..",
                true);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 2000); //display progress dialog for 2 seconds

        handler.postDelayed(new Runnable() {
            public void run() {
                getImage();
            }
        }, 2000);   //wait 2 seconds, then attempt to get image from server


    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(MonitorActivity.this, HomeActivity.class);
        startActivity(home);
    }

    public void getImage() { //retrieve image from server
        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        ImageView picture=(ImageView)findViewById(R.id.camera_image);
        Picasso.with(picture.getContext())
                //.load("http://129.107.117.136/image.jpg")
                .load("http://"+settings.getString("IP",null)+"/image.jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(300,300).into(picture);
    }
}
