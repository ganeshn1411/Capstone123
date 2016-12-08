package jialiw.cmu.edu.capstoneappv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText user_username = (EditText) findViewById(R.id.loginUsername);
//        final EditText user_email = (EditText) findViewById(R.id.register_email);
        final EditText user_password = (EditText) findViewById(R.id.loginPassword);

        final Button bLogin = (Button) findViewById(R.id.loginButton);

        final TextView registerLink = (TextView) findViewById(R.id.register_link);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String username = user_username.getText().toString();
                final String password = user_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            //boolean success = jsonResponse.getBoolean("success");
                            boolean success = true;
                            System.out.println(success);
                            Log.i("json",jsonResponse.toString());
                            if (success){
                                //String fname = jsonResponse.getString("fname");
                              //  String lname = jsonResponse.getString("lname");
                                String uname = jsonResponse.getString("username");
                                Log.i("uname",uname);
                                Intent intent = new Intent(LoginActivity.this, MainProfileActivity.class);
                                intent.putExtra("username", uname);

                                LoginActivity.this.startActivity(intent);

                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                Log.e("Still in LoginActivity", "line 82");
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}
