package jialiw.cmu.edu.capstoneappv1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 11/2/2016.
 */

public class ActivityTestRequest1 extends StringRequest {
    private static final String ACTIVITY_REQUEST_URL = "https://calm-citadel-79946.herokuapp.com/";
    private Map<String, String> params;

    public ActivityTestRequest1 (String username, String procedureID, int processID, String processTime, Response.Listener<String>Listener) {
        super(Method.POST, ACTIVITY_REQUEST_URL, Listener, null);
        Log.i("in ActivityTestRequest", "line 20");
        params = new HashMap<>();
        params.put("username", username);
        params.put("procedure_id", procedureID);
        params.put("process_id", processID + "");
        params.put("process_time", processTime);
        Log.i("in ATRequest2", "paras all set");
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
