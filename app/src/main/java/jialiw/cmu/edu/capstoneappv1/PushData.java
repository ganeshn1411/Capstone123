package jialiw.cmu.edu.capstoneappv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PushData extends AppCompatActivity {


    ArrayList<TimeObjectNecessaries> timeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_data);

        Intent intent = getIntent();
        final HashMap<String,ArrayList<String>> activityStartTimes = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityStartTimes");
        final HashMap<String,ArrayList<String>> activityEndTimes = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityEndTimes");
        final String pid = intent.getStringExtra("patientId").trim()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ;
        final String mid = intent.getStringExtra("mid").trim()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ;

        System.out.println("Start Times : " + activityStartTimes);
        System.out.println("End Times : " + activityEndTimes);
        System.out.println("pid : " + pid);


        Set<String> aidKeys = activityStartTimes.keySet();
        Iterator<String> aidKeysIterator = aidKeys.iterator();
        while(aidKeysIterator.hasNext()){
            String aid = aidKeysIterator.next();
            try{
                String startTime = (String)activityStartTimes.get(aid).get(0);
                String endTime = (String)activityEndTimes.get(aid).get(0);

                System.out.println(startTime);
                System.out.println(endTime);

                TimeObjectNecessaries timeObjectNecessaries = new TimeObjectNecessaries(aid,pid,mid,startTime,endTime);
                System.out.println(timeObjectNecessaries.getAid());

                timeList.add(timeObjectNecessaries);
                new sendTimeObject().execute();
                System.out.println("updated");
            }
            catch(Exception e){
                System.out.println("here");
            }

        }



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
