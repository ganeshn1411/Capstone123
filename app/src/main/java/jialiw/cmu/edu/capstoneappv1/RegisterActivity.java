package jialiw.cmu.edu.capstoneappv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("", "Line 25");
        final EditText user_fname = (EditText) findViewById(R.id.registerFName);
        final EditText user_lname = (EditText) findViewById(R.id.registerLName);
        final EditText user_username = (EditText) findViewById(R.id.registerUsername);
        final EditText user_email = (EditText) findViewById(R.id.register_email);
        final EditText user_password = (EditText) findViewById(R.id.registerPassword);

        final Button bRegister = (Button) findViewById(R.id.registerButton);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = user_fname.getText().toString();
                final String lname = user_lname.getText().toString();
                final String username = user_username.getText().toString();
                final String email = user_email.getText().toString();
                final String password = user_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.d("", "Line 48");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                Log.i("haha", "success");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                Log.i("fail","fail");
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Log.i("line70", "outside try/catch");
                RegisterRequest registerRequest = new RegisterRequest(username, fname, lname, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                Log.i("line74", "end of the code?");
            }
        });
    }
}
