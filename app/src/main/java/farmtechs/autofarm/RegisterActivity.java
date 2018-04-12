package farmtechs.autofarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        if(!settings.getBoolean("FIRST_TIME",false)) { //check if already launched before
            Intent home = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(home);
            finish();
        }

        final Button continue_btn = (Button) findViewById(R.id.apply_btn);
        final EditText email = (EditText)findViewById(R.id.email_input);
        final EditText ip = (EditText)findViewById(R.id.ip_input);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(view);
                }
            }
        });

        ip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(view);
                }
            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try{
                    String email_address = email.getText().toString();
                    String ip_address = ip.getText().toString();
                    if(!email_address.isEmpty() && !ip_address.isEmpty()){
                        new RegisterActivity.Background_get().execute("cgi-bin/email.php", "mail="+email_address);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("IP", ip_address);
                        editor.apply();
                        Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(home); //launch monitor activity
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }

                }catch (NumberFormatException e) {
                    Toast.makeText(getBaseContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                /* IP of raspberry pi, params [0] = path/filename.extension, params[1] = variable=value */
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
                            editor.putString("SENSOR_ONE",inputLine);
                        }else if (i == 1) {
                            editor.putString("SENSOR_TWO",inputLine);
                        }else if (i == 2) {
                            editor.putString("SENSOR_THREE", inputLine);
                        }else if (i==3) {
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

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
