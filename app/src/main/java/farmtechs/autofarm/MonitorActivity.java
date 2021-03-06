package farmtechs.autofarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(MonitorActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
