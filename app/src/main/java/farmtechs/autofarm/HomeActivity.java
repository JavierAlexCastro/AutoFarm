package farmtechs.autofarm;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeActivity extends AppCompatActivity {

    int pressed1 = 0; //1 if true, 0 if false
    int pressed2 = 0;
    int pressed3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        final Button monitor_btn = (Button) findViewById(R.id.monitor_button);
        final Button water_btn = (Button) findViewById(R.id.water_button);
        final Button settings_btn = (Button) findViewById(R.id.settings_button);
        final Button about_btn = (Button) findViewById(R.id.about_button);

        monitor_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                new Background_get().execute("cgi-bin/camera.sh", " "); //call camera script on pi

                Intent monitor = new Intent(HomeActivity.this, MonitorActivity.class);
                startActivity(monitor); //launch monitor activity
            }
        });

        water_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                final Dialog dialog = new Dialog(HomeActivity.this); //create dialog
                dialog.setContentView(R.layout.activity_water); //sets layout to dialog

                final Button block_one = (Button) dialog.findViewById(R.id.block1_image); //get button context
                final Button block_two = (Button) dialog.findViewById(R.id.block2_image);
                final Button block_three = (Button) dialog.findViewById(R.id.block3_image);

                block_one.setOnClickListener(new View.OnClickListener() //block 1 clicked
                {
                    public void onClick(View v) {
                        if(pressed1==0) {
                            if(!settings.getBoolean("FIRST_BLOCK",true)){ //if block 1 disabled
                                Toast.makeText(HomeActivity.this, "Block 1 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{ //block 1 enabled
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

                block_two.setOnClickListener(new View.OnClickListener() //block 2 clicked
                {
                    public void onClick(View v) {
                        if(pressed2==0) {
                            if(!settings.getBoolean("SECOND_BLOCK",true)){ //if block 2 disabled
                                Toast.makeText(HomeActivity.this, "Block 2 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{ //block 2 enabled
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

                block_three.setOnClickListener(new View.OnClickListener() //block 3 clicked
                {
                    public void onClick(View v) {
                        if(pressed3==0) {
                            if(!settings.getBoolean("THIRD_BLOCK",true)){ //if block 3 disabled
                                Toast.makeText(HomeActivity.this, "Block 3 is not enabled",
                                        Toast.LENGTH_SHORT).show();
                            }else{ //block 3 enabled
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
                water.setOnClickListener(new View.OnClickListener() { //water button clicked
                    @Override
                    public void onClick(View v) {
                        /////checks for combination of blocks pressed and passes appropriate parameters
                        if (pressed1 == 0 && pressed2 == 0 && pressed3 == 0) {                 // 0 0 0
                            new Background_get().execute("cgi-bin/water.php", "water=0");
                            Toast.makeText(HomeActivity.this, "Select at least one block",
                                    Toast.LENGTH_SHORT).show();
                        } else if (pressed1 == 1 && pressed2 == 1 && pressed3 == 1) {          // 1 1 1
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

                        //set everything back to default before closing dialog
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
                final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                /* IP of raspberry pi, params [0] = path/filename.extension, params[1] = variable=value */
                //URL url = new URL("http:/129.107.117.136/"+params[0]+"?"+params[1]);
                URL url = new URL("http:/"+settings.getString("IP",null)+"/"+params[0]+"?"+params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //buffer for return string
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                int i=0;
                while ((inputLine = in.readLine()) != null)
                    if (params[0].equals("sensors.txt")) { //if retrieving sensor readings
                        SharedPreferences.Editor editor = settings.edit();
                        if (i == 0) {
                            Log.d("adding m1: ",inputLine);
                            editor.putString("SENSOR_ONE",inputLine);
                        }else if (i == 1) {
                            Log.d("adding m2: ",inputLine);
                            editor.putString("SENSOR_TWO",inputLine);
                        }else if (i == 2) {
                            Log.d("adding m3: ",inputLine);
                            editor.putString("SENSOR_THREE", inputLine);
                        }else if (i==3) {
                            Log.d("adding w: ",inputLine);
                            if(inputLine.equals("0")) {
                                editor.putBoolean("HAS_WATER", false);
                            }else{
                                editor.putBoolean("HAS_WATER",true);
                            }
                        }else {
                            Log.d("out of bounds. Index: ", String.valueOf(i));
                        }
                        editor.commit();
                        i++;
                    }
                    result.append(inputLine).append("\n");
                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() { //if back is pressed on home screen don't go back to previous activity. Exit app with confirmation dialog
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }

}
