package farmtechs.autofarm;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.BuildConfig;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    int pressed1 = 0;
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
            editor.apply();
        }

        monitor_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {




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


                        if (pressed1 == 0 && pressed2 == 0 && pressed3 == 0) {
                            Toast.makeText(HomeActivity.this, "Select at least one block",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            /**call different functions based on pressed1, pressed2, pressed3 values (Raspberry Pi)**/

                            block_one.setBackgroundResource(R.drawable.black_square);
                            block_two.setBackgroundResource(R.drawable.black_square);
                            block_three.setBackgroundResource(R.drawable.black_square);
                            pressed1 = 0;
                            pressed2 = 0;
                            pressed3 = 0;

                            Toast.makeText(HomeActivity.this, "Watering plant(s)",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
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

                            //block_one.setBackgroundResource(R.drawable.black_square);
                            //block_two.setBackgroundResource(R.drawable.black_square);
                            //block_three.setBackgroundResource(R.drawable.black_square);
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










                //Intent settings = new Intent(HomeActivity.this, SettingsActivity.class);
                //startActivity(settings);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(about);
            }
        });
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
