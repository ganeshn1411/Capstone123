package jialiw.cmu.edu.capstoneappv1;

import android.content.ClipData;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;
import java.util.logging.SimpleFormatter;

public class ActivityDetailsTest extends AppCompatActivity {
    String result, pid, mid;
    ArrayList<String> activityIDList, duplicateAIDList;
    HashMap<String, ArrayList<String>> patientActivityMap;
    ArrayList<TimeObjectNecessaries> timeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_test);

        Intent intent = getIntent();
        activityIDList = intent.getStringArrayListExtra("activityList");
        patientActivityMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("paMap");
        pid = intent.getStringExtra("patientId").trim();
        mid = intent.getStringExtra("mid").trim();

        for (Map.Entry<String, ArrayList<String>>entry: patientActivityMap.entrySet()) {
            Log.w("check the f*k key", entry.getKey());
        }


        if (intent.hasExtra("duplicateAIDList")) {
            duplicateAIDList = intent.getStringArrayListExtra("duplicateAIDList");
        } else {
            duplicateAIDList = patientActivityMap.get(pid);
        }

        final Button bCount = (Button) findViewById(R.id.time_count_button);
        final TextView usernameText = (TextView) findViewById(R.id.test_username);
        final TextView resultText = (TextView) findViewById(R.id.test_show_result);

        Log.v("onCreate method", "81");

        String tem = duplicateAIDList.get(0);
        resultText.setText(tem);
        String username = intent.getStringExtra("username");
        Log.e("show usernmae:", username);
        usernameText.setText(username);

        result = intent.getStringExtra("databaseResult");
        Log.e("show result", result);
        bCount.setTag(1);
        bCount.setText("Start");
        timeList = new ArrayList<TimeObjectNecessaries>();
        bCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<String> iterator = duplicateAIDList.iterator();
                while (iterator.hasNext()){
                    Log.e("check aList",iterator.next());
                }

                Log.e("line 83", String.valueOf(duplicateAIDList.isEmpty()));
                String tem;
                TextView timeText = (TextView) findViewById(R.id.test_text_view);

                if (!duplicateAIDList.isEmpty()) {
                    tem = duplicateAIDList.get(0);
                    Log.e("String to delete",tem);
                    resultText.setText(tem);
                    Log.v("onClick method", "110");
                    final int status = (Integer) view.getTag();
                    if (status == 1) {
                        view.setTag(0);
                        bCount.setText("Stop");
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("MM/dd/yyyy hh:mm:ss", currentDate.getTime());
                        TimeObjectNecessaries timeObjectNecessaries = new TimeObjectNecessaries(tem,pid, mid, s.toString(), "");
                        Log.e("current time", s.toString());
                        timeText.setText(s.toString());
                        timeObjectNecessaries.setStart(s.toString());
                        timeList.add(0,timeObjectNecessaries);

                    } else {
                        duplicateAIDList.remove(0);
                        if (duplicateAIDList.isEmpty()) {
                            bCount.setText("Finish");
                            resultText.setText("DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        } else {
                            bCount.setText("Start");
                        }
                        Log.e("timeList size", timeList.size()+"");
                        TimeObjectNecessaries temObject = timeList.get(0);
                        Log.e("in line 132&& mid: ", temObject.getMid());
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("MM/dd/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());
                        timeText.setText(s.toString());
                        temObject.setEnd(s.toString());
                        view.setTag(1);
                    }

                    Log.e("after judgement" ," size: " + timeList.size());

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.i("test", "line 59");
                                Intent intent = new Intent(ActivityDetailsTest.this, ActivityDetailsTest.class);
                                intent.putStringArrayListExtra("duplicateAIDList",duplicateAIDList);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                } else {
                    new sendTimeObject().execute();
                    Intent intent1 = new Intent(ActivityDetailsTest.this, AnalysisActivity.class);
                    intent1.putExtra("result",result);
                    startActivity(intent1);
                }

                Log.e("leave main activity", "after activityTestRequest1 queue");
            }
        });
    }

    public class sendTimeObject extends AsyncTask<String, Void, String> {
        HttpURLConnection httpURLConnection;

        @Override
        protected String doInBackground(String... strings) {


            try{
                URL url = new URL("https://intense-mountain-41887.herokuapp.com/push");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                for(TimeObjectNecessaries timeObjectNecessaries: timeList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("aid", timeObjectNecessaries.getAid());
                    jsonObject.put("mid", timeObjectNecessaries.getMid());
                    jsonObject.put("pid", timeObjectNecessaries.getPid());
                    jsonObject.put("actual_start", timeObjectNecessaries.getStart());
                    jsonObject.put("actual_end",timeObjectNecessaries.getEnd());
                    String dataToSend = jsonObject.toString();

                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                    wr.writeBytes(dataToSend);
                    wr.flush();
                }
            } catch (Exception e) {
                Log.e("in line 232", e.getMessage()+ ": " + e.getLocalizedMessage());
            }
            return  null;
        }
    }

    public class TimeObjectNecessaries{
        private String aid, pid, mid, start, end;

        TimeObjectNecessaries() {
            this.aid = "";
            this.pid = "";
            this.mid = "";
            this.start = "";
            this.end = "";
        }

        TimeObjectNecessaries (String a, String p, String m, String s, String e) {
            this.aid = a;
            this.pid = p;
            this.mid = m;
            this.start = s;
            this.end = e;
        }

        public void setAid (String aid) {
            this.aid = aid;
        }

        public String getAid () {
            return this.aid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPid () {
            return this.pid;
        }

        public void setMid (String mid){
            this.mid = mid;
        }

        public String getMid () {
            return this.mid;
        }

        public void setStart (String start) {
            this.start = start;
        }

        public String getStart () {
            return this.start;
        }

        public void setEnd (String end) {
            this.end = end;
        }

        public String getEnd () {
            return this.end;
        }

    }


}
