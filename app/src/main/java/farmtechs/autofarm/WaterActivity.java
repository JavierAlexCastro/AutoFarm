package farmtechs.autofarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WaterActivity extends AppCompatActivity {

    int pressed1 = 0;
    int pressed2 = 0;
    int pressed3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);



        final Button block_one = (Button) findViewById(R.id.block1_image);
        final Button block_two = (Button) findViewById(R.id.block2_image);
        final Button block_three = (Button) findViewById(R.id.block3_image);
        final Button water = (Button) findViewById(R.id.water_btn);

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

        water.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                if (pressed1 == 0 && pressed2 == 0 && pressed3 == 0) {
                    Toast.makeText(WaterActivity.this, "Select at least one block",
                            Toast.LENGTH_SHORT).show();
                } else {

                    /**call different functions based on pressed1, pressed2, pressed3 values (Raspberry Pi)**/

                    block_one.setBackgroundResource(R.drawable.black_square);
                    block_two.setBackgroundResource(R.drawable.black_square);
                    block_three.setBackgroundResource(R.drawable.black_square);
                    pressed1 = 0;
                    pressed2 = 0;
                    pressed3 = 0;

                    Toast.makeText(WaterActivity.this, "Watering plants",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(WaterActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
