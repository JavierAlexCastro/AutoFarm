package farmtechs.autofarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(WaterActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
