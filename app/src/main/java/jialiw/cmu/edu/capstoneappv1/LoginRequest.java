package jialiw.cmu.edu.capstoneappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "http://jialiw.webutu.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password,  Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        Log.e("In LoginRequest file","Enter into RegisterRequest");

        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        Log.i("In LoginRequest file", "Pass RegisterRequest");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
