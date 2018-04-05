package farmtechs.autofarm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.squareup.picasso.Picasso;

/*import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;*/

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeActivity extends AppCompatActivity {

    int pressed1 = 0; //1 if true, 0 if false
    int pressed2 = 0;
    int pressed3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button monitor_btn = (Button) findViewById(R.id.monitor_button);
        final Button water_btn = (Button) findViewById(R.id.water_button);
        final Button settings_btn = (Button) findViewById(R.id.settings_button);
        final Button about_btn = (Button) findViewById(R.id.about_button);

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        if(settings.getBoolean("FIRST_TIME",true)){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.putBoolean("FIRST_BLOCK", false);
            editor.putBoolean("SECOND_BLOCK", false);
            editor.putBoolean("THIRD_BLOCK", false);
            //editor.putInt("SENSOR_ONE", 0);
            //editor.putInt("SENSOR_TWO", 0);
            //editor.putInt("SENSOR_THREE", 0);
            editor.apply();
        }

        monitor_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                //Creates Client and post header with Pi's IP
                /*HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.0.35/cgi-bin/camera.sh");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("camera", "1"));//?

                //encode post data
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //execute post request
                try {
                    httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                /////////////////working///////////////////////////
                new Background_get().execute("cgi-bin/camera.sh", " ");

                Intent monitor = new Intent(HomeActivity.this, MonitorActivity.class);
                startActivity(monitor);
            }
        });

        water_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.activity_water);

                final Button block_one = (Button) dialog.findViewById(R.id.block1_image);
                final Button block_two = (Button) dialog.findViewById(R.id.block2_image);
                final Button block_three = (Button) dialog.findViewById(R.id.block3_image);

                block_one.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed1==0) {
                            if(!settings.getBoolean("FIRST_BLOCK",true)){
                                Toast.makeText(HomeActivity.this, "Block 1 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                block_one.setBackgroundResource(R.drawable.green_square);
                                pressed1=1;
                            }
                        }
                        else{
                            block_one.setBackgroundResource(R.drawable.black_square);
                            pressed1=0;
                        }
                    }
                });

                block_two.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed2==0) {
                            if(!settings.getBoolean("SECOND_BLOCK",true)){
                                Toast.makeText(HomeActivity.this, "Block 2 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                block_two.setBackgroundResource(R.drawable.green_square);
                                pressed2=1;
                            }

                        }
                        else{
                            block_two.setBackgroundResource(R.drawable.black_square);
                            pressed2=0;
                        }
                    }
                });

                block_three.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed3==0) {
                            if(!settings.getBoolean("THIRD_BLOCK",true)){
                                Toast.makeText(HomeActivity.this, "Block 3 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                block_three.setBackgroundResource(R.drawable.green_square);
                                pressed3=1;
                            }

                        }
                        else{
                            block_three.setBackgroundResource(R.drawable.black_square);
                            pressed3=0;
                        }
                    }
                });

                Button water = (Button) dialog.findViewById(R.id.water_btn);
                // if button is clicked, close the custom dialog
                water.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (pressed1 == 0 && pressed2 == 0 && pressed3 == 0) {                  // 0 0 0
                            Toast.makeText(HomeActivity.this, "Select at least one block",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (pressed1 == 1 && pressed2 == 1 && pressed3 == 1) {             // 1 1 1
                                new Background_get().execute("cgi-bin/water.php", "water=1");
                            } else if (pressed1 == 1 && pressed2 == 1 && pressed3 == 0) {      // 1 1 0
                                new Background_get().execute("cgi-bin/water.php", "water=2");
                            } else if (pressed1 == 1 && pressed2 == 0 && pressed3 == 1) {      // 1 0 1
                                new Background_get().execute("cgi-bin/water.php", "water=3");
                            } else if (pressed1 == 1 && pressed2 == 0 && pressed3 == 0) {      // 1 0 0
                                new Background_get().execute("cgi-bin/water.php", "water=4");
                            } else if (pressed1 == 0 && pressed2 == 1 && pressed3 == 1) {      // 0 1 1
                                new Background_get().execute("cgi-bin/water.php", "water=5");
                            } else if (pressed1 == 0 && pressed2 == 1 && pressed3 == 0) {      // 0 1 0
                                new Background_get().execute("cgi-bin/water.php", "water=6");
                            } else if (pressed1 == 0 && pressed2 == 0 && pressed3 == 1) {      // 0 0 1
                                new Background_get().execute("cgi-bin/water.php", "water=7");
                            }
                            Toast.makeText(HomeActivity.this, "Watering plant(s)",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //log.d("argument[0]", argument[0]);
                        /**call different functions based on pressed1, pressed2, pressed3 values (Raspberry Pi)**/

                        block_one.setBackgroundResource(R.drawable.black_square);
                        block_two.setBackgroundResource(R.drawable.black_square);
                        block_three.setBackgroundResource(R.drawable.black_square);
                        pressed1 = 0;
                        pressed2 = 0;
                        pressed3 = 0;

                        dialog.dismiss();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                //window.setLayout(1000,1150);
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.activity_enable);

                final Button block_one = (Button) dialog.findViewById(R.id.block1_image);
                final Button block_two = (Button) dialog.findViewById(R.id.block2_image);
                final Button block_three = (Button) dialog.findViewById(R.id.block3_image);


                final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                if(settings.getBoolean("FIRST_BLOCK",false)){
                    block_one.setBackgroundResource(R.drawable.green_square);
                    pressed1=1;
                } else{
                    block_one.setBackgroundResource(R.drawable.black_square);
                }
                if(settings.getBoolean("SECOND_BLOCK",false)){
                    block_two.setBackgroundResource(R.drawable.green_square);
                    pressed2=1;
                } else{
                    block_two.setBackgroundResource(R.drawable.black_square);
                }
                if(settings.getBoolean("THIRD_BLOCK",false)){
                    block_three.setBackgroundResource(R.drawable.green_square);
                    pressed3=1;
                } else{
                    block_three.setBackgroundResource(R.drawable.black_square);
                }

                block_one.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed1==0) {
                            block_one.setBackgroundResource(R.drawable.green_square);
                            pressed1=1;
                        }
                        else{
                            block_one.setBackgroundResource(R.drawable.black_square);
                            pressed1=0;
                        }
                    }
                });

                block_two.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed2==0) {
                            block_two.setBackgroundResource(R.drawable.green_square);
                            pressed2=1;
                        }
                        else{
                            block_two.setBackgroundResource(R.drawable.black_square);
                            pressed2=0;
                        }
                    }
                });

                block_three.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {
                        if(pressed3==0) {
                            block_three.setBackgroundResource(R.drawable.green_square);
                            pressed3=1;
                        }
                        else{
                            block_three.setBackgroundResource(R.drawable.black_square);
                            pressed3=0;
                        }
                    }
                });

                Button enable = (Button) dialog.findViewById(R.id.enable_btn);
                // if button is clicked, close the custom dialog
                enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            SharedPreferences.Editor editor = settings.edit();
                            if(pressed1 == 1){
                                editor.putBoolean("FIRST_BLOCK",true);
                            }else if(pressed1 == 0){
                                editor.putBoolean("FIRST_BLOCK",false);
                            }
                            if(pressed2 == 1){
                                editor.putBoolean("SECOND_BLOCK",true);
                            }else if(pressed2 == 0){
                                editor.putBoolean("SECOND_BLOCK",false);
                            }
                            if(pressed3 == 1){
                                editor.putBoolean("THIRD_BLOCK",true);
                            }else if(pressed3 == 0){
                                editor.putBoolean("THIRD_BLOCK",false);
                            }
                            editor.apply();

                            if(pressed1==1 && pressed2==1 && pressed3==1) {             // 1 1 1
                                new Background_get().execute("cgi-bin/pumps.php", "pump=1");
                            } else if(pressed1==1 && pressed2==1 && pressed3==0) {      // 1 1 0
                                new Background_get().execute("cgi-bin/pumps.php", "pump=2");
                            } else if(pressed1==1 && pressed2==0 && pressed3==1) {      // 1 0 1
                                new Background_get().execute("cgi-bin/pumps.php", "pump=3");
                            } else if(pressed1==1 && pressed2==0 && pressed3==0) {      // 1 0 0
                                new Background_get().execute("cgi-bin/pumps.php", "pump=4");
                            } else if(pressed1==0 && pressed2==1 && pressed3==1) {      // 0 1 1
                                new Background_get().execute("cgi-bin/pumps.php", "pump=5");
                            } else if(pressed1==0 && pressed2==1 && pressed3==0) {      // 0 1 0
                                new Background_get().execute("cgi-bin/pumps.php", "pump=6");
                            } else if(pressed1==0 && pressed2==0 && pressed3==1) {      // 0 0 1
                                new Background_get().execute("cgi-bin/pumps.php", "pump=7");
                            } else if(pressed1==0 && pressed2==0 && pressed3==0) {      // 0 0 0
                                new Background_get().execute("cgi-bin/pumps.php", "pump=8");
                            }

                            pressed1 = 0;
                            pressed2 = 0;
                            pressed3 = 0;

                            Toast.makeText(HomeActivity.this, "Changes applied",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                //window.setLayout(1000,1150);
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                new Background_get().execute("sensors.txt", " ");

                Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(about);
            }
        });

    }

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http:/129.107.116.224/"+params[0]+"?"+params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                int i=0;
                while ((inputLine = in.readLine()) != null)
                    if (params[0].equals("sensors.txt")) {
                        if (i == 0) {
                            Log.d("adding m1: ", inputLine);
                            AutoFarm.mSensor1.setMoisture(inputLine);
                            Log.d("added m1: ", String.valueOf(AutoFarm.getmSensor1().getMoisture()));
                        }else if (i == 1) {
                            Log.d("adding m2: ", inputLine);
                            AutoFarm.mSensor2.setMoisture(inputLine);
                            Log.d("added m2: ", String.valueOf(AutoFarm.getmSensor2().getMoisture()));
                        }else if (i == 2) {
                            Log.d("adding m3: ", inputLine);
                            AutoFarm.mSensor3.setMoisture(inputLine);
                            Log.d("added m3: ", String.valueOf(AutoFarm.getmSensor3().getMoisture()));
                        }else {
                            Log.d("out of bounds. Index: ", String.valueOf(i));
                        }
                        i++;
                    }
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                //Log.d("returned:", result.toString());
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }

}
