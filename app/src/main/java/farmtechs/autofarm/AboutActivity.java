package farmtechs.autofarm;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView m1text = (TextView)findViewById(R.id.m1text);
        m1text.setText(String.valueOf(AutoFarm.getmSensor1().getMoisture()) + "%");

        TextView ch1text = (TextView)findViewById(R.id.ch1text);
        ch1text.setText(String.valueOf(AutoFarm.getmSensor1().getCheckInterval()) + " hours");

        TextView m2text = (TextView)findViewById(R.id.m2text);
        m2text.setText(String.valueOf(AutoFarm.getmSensor2().getMoisture()) + "%");

        TextView ch2text = (TextView)findViewById(R.id.ch2text);
        ch2text.setText(String.valueOf(AutoFarm.getmSensor2().getCheckInterval()) + " hours");

        TextView m3text = (TextView)findViewById(R.id.m3text);
        m3text.setText(String.valueOf(AutoFarm.getmSensor3().getMoisture()) + "%");

        TextView ch3text = (TextView)findViewById(R.id.ch3text);
        ch3text.setText(String.valueOf(AutoFarm.getmSensor3().getCheckInterval()) + " hours");

        TextView wtext = (TextView)findViewById(R.id.wtext);
        wtext.setText(String.valueOf(AutoFarm.getWSensor().getLevel()) + "%");
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(AboutActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
