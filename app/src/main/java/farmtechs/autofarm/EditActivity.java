package farmtechs.autofarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setupUI(findViewById(R.id.activity_edit));

        final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

        //get resources from layout
        final Button continue_btn = (Button) findViewById(R.id.apply_btn);
        final EditText email = (EditText)findViewById(R.id.email_input);
        final EditText ip = (EditText)findViewById(R.id.ip_input);
        ip.setText(settings.getString("IP",""), TextView.BufferType.EDITABLE); //load previous IP in case they just want to change their email


        //if they hit the continue button
        continue_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try{
                    String email_address = email.getText().toString(); //get the email they typed as a string
                    String ip_address = ip.getText().toString(); //get the ip they typed as a string
                    if(!email_address.isEmpty() && !ip_address.isEmpty()){ //check that they are not empty
                        new EditActivity.Background_get().execute("cgi-bin/email.php", "mail="+email_address); //save email in raspberry pi
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("IP", ip_address); //save ip inside app
                        editor.apply();
                        Intent home = new Intent(EditActivity.this, HomeActivity.class);
                        startActivity(home); //launch home activity
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

    //function that sends http requests to webserver hosted by raspberry pi
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                final SharedPreferences settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                /* IP of raspberry pi, params [0] = path/filename.extension, params[1] = variable=value */
                URL url = new URL("http:/"+settings.getString("IP",null)+"/"+params[0]+"?"+params[1]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //establish a connection

                //buffer for return string
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine; //string for each line read
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

                in.close(); //close buffered reader
                connection.disconnect(); //close connection
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //this method helps the hidesoftKeyboard method
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(EditActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    //hides the keyboard when the user clicks something else other than the edittexts
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
