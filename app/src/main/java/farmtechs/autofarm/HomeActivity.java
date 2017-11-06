package farmtechs.autofarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button monitor_btn = (Button) findViewById(R.id.monitor_button);
        final Button water_btn = (Button) findViewById(R.id.water_button);
        final Button settings_btn = (Button) findViewById(R.id.settings_button);
        final Button about_btn = (Button) findViewById(R.id.about_button);

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
                Intent water = new Intent(HomeActivity.this, WaterActivity.class);
                startActivity(water);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent settings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(settings);
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
