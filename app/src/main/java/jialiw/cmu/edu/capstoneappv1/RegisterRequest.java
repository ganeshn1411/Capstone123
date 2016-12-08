package jialiw.cmu.edu.capstoneappv1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import android.view.Menu;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 10/24/2016.
 * make request to the register.php file on the server
 * and get response as a string
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://jialiw.webutu.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String fname, String lname, String email_addr,String password,  Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        Log.i("","Enter into RegisterRequest");
        params = new HashMap<>();
        params.put("username", username);
        params.put("fname", fname);
        params.put("lname", lname);
        params.put("email", email_addr);
        params.put("password", password);
        Log.i("", "Pass RegisterRequest");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
