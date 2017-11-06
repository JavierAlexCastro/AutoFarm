package farmtechs.autofarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(MonitorActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
