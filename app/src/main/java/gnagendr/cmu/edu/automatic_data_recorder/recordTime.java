package gnagendr.cmu.edu.automatic_data_recorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class recordTime extends AppCompatActivity {
    int total = 0;
    int progress = 1;
    String currActivityID = "";
    String currActivityName = "";
    String mainActivity = "";
    String startButtonText = "";
    String clearButtonText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_time);
        Intent intent = getIntent();
        final HashMap<String, ArrayList<String>> activityNameIDMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityDetails");
        Log.v("activityNameIDMap",activityNameIDMap.toString());
        final HashMap<String, ArrayList<String>> patientActivityMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("paMap");
        final HashMap<String, ArrayList<String>> activityDetails = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityDetails");
        final HashMap<String,ArrayList<String>> activityStartTimes = new HashMap<>();
        final HashMap<String,ArrayList<String>> activityEndTimes = new HashMap<>();
        Log.v("map size", patientActivityMap.size()+"");
        final String pid = intent.getStringExtra("patientId").trim()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ;
        Log.d("pid is =====", pid);
        Stack<String> activityStack = new Stack<>();
        String key = "";
        Set<String> keys = patientActivityMap.keySet();
        Iterator iterator = keys.iterator();
        while(iterator.hasNext()){
            key = (String)iterator.next();
        }
        Iterator listIterator = patientActivityMap.get(key).iterator();

        while(listIterator.hasNext()){
            String val = (String)listIterator.next();
            val = val.replaceAll("[^a-zA-Z0-9,-]", "");
            activityStack.push(val);
        }
        total = activityStack.size();
        Log.d("total",String.valueOf(total));

        final Stack<String> preCathProcessStack = new Stack<>();
        final Stack<String> preCathProcessStack1 = new Stack<>();
        final Stack<String> patientPrepStack = new Stack<>();
        final Stack<String> patientPrepStack1 = new Stack<>();
        final Stack<String> prepRoomStack = new Stack<>();
        final Stack<String> prepRoomStack1 = new Stack<>();
        final Stack<String> surgeryStack = new Stack<>();
        final Stack<String> surgeryStack1 = new Stack<>();
        final Stack<String> postCathStack = new Stack<>();
        final Stack<String> postCathStack1 = new Stack<>();
        final Stack<String> postCathAssesmentStack = new Stack<>();
        final Stack<String> postCathAssesmentStack1 = new Stack<>();

        while(!activityStack.isEmpty()) {
            String activityID = activityStack.peek();
            activityStack.pop();
            String activityCategory = activityNameIDMap.get(activityID).get(1);
            if(activityCategory.equals("Pre-Catheterization")){
                preCathProcessStack.push(activityID);
            }
            else if(activityCategory.equals("Post-Catheterization")){
                postCathStack.push(activityID);
            }
            else if(activityCategory.equals("Room Prep")){
                prepRoomStack.push(activityID);
            }
            else if(activityCategory.equals("Surgery")){
                surgeryStack.push(activityID);
            }
            else if(activityCategory.equals("Patient Prep")){
                patientPrepStack.push(activityID);
            }
            else if(activityCategory.equals("Post-Surgery Assessment")){
                postCathAssesmentStack.push(activityID);
            }
        }

        final TextView activityName = (TextView) findViewById(R.id.activity_Name);
        activityName.setVisibility(View.INVISIBLE);

        final TextView processText = (TextView) findViewById(R.id.activityPage);
        processText.setText("Pre-Catheterization");

        final TextView progressText = (TextView)findViewById(R.id.progress);
        progressText.setText(progress+"/"+total);
        progressText.setVisibility(View.INVISIBLE);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setMax(total);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(progress);

        final Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setText("Begin");

        final Button startButton = (Button)findViewById(R.id.startButton);
        startButton.setVisibility(View.INVISIBLE);

        final Button frontButton = (Button)findViewById(R.id.frontButton);
        frontButton.setVisibility(View.INVISIBLE);

        final Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        startButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mainActivity = (String)processText.getText();
                startButtonText = (String) startButton.getText();
                if (startButtonText.equals("Start")) {
                    System.out.println(currActivityID);
                    System.out.println(currActivityName);
                    startButton.setText("Stop");
                    clearButton.setText("Clear");
                    clearButton.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setText(progress + "/" + total);
                    progressText.setVisibility(View.VISIBLE);
                    frontButton.setVisibility(View.VISIBLE);
                    backButton.setVisibility(View.VISIBLE);
                    if(mainActivity.equals("Surgery")){
                        if(surgeryStack1.size()==1){
                            backButton.setVisibility(View.INVISIBLE);
                        }
                    }

                    Date currentDate = new Date(System.currentTimeMillis());
                    CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                    Log.e("current time", s.toString());
                    ArrayList<String> existingtimes = activityStartTimes.get(currActivityID);
                    if(existingtimes == null){
                        ArrayList<String> startTimeList = new ArrayList<String>();
                        startTimeList.add(s.toString());
                        activityStartTimes.put(currActivityID,startTimeList);
                    }
                    else{
                        existingtimes.add(s.toString());
                        activityStartTimes.put(currActivityID,existingtimes);
                    }
                }
                else if (startButtonText.equals("Stop")) {
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setText(progress + "/" + total);
                    progressText.setVisibility(View.VISIBLE);
                    frontButton.setVisibility(View.VISIBLE);
                    backButton.setVisibility(View.VISIBLE);

                    if (mainActivity.equals("Pre-Catheterization")) {
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());

                        ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                        if(existingtimes == null){
                            ArrayList<String> startTimeList = new ArrayList<String>();
                            startTimeList.add(s.toString());
                            activityEndTimes.put(currActivityID,startTimeList);
                        }
                        else{
                            existingtimes.add(s.toString());
                            activityEndTimes.put(currActivityID,existingtimes);
                        }

                        if(preCathProcessStack.size()==1){
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            frontButton.setVisibility(View.INVISIBLE);
                            backButton.setVisibility(View.INVISIBLE);

                            processText.setText("Patient Prep");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);

                            preCathProcessStack1.push(preCathProcessStack.peek());
                            preCathProcessStack.pop();
                        }
                        else{
                            preCathProcessStack.pop();
                            preCathProcessStack1.push(currActivityID);
                            String newActivityID = preCathProcessStack.peek();
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);

                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Patient Prep")){
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());

                        ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                        if(existingtimes == null){
                            ArrayList<String> startTimeList = new ArrayList<String>();
                            startTimeList.add(s.toString());
                            activityEndTimes.put(currActivityID,startTimeList);
                        }
                        else{
                            existingtimes.add(s.toString());
                            activityEndTimes.put(currActivityID,existingtimes);
                        }

                        if(patientPrepStack.size()==1){
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            frontButton.setVisibility(View.INVISIBLE);
                            backButton.setVisibility(View.INVISIBLE);

                            processText.setText("Room Prep");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);

                            patientPrepStack1.push(patientPrepStack.peek());
                            patientPrepStack.pop();
                        }
                        else{
                            patientPrepStack.pop();
                            patientPrepStack1.push(currActivityID);
                            String newActivityID = patientPrepStack.peek();
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);

                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Room Prep")){
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());

                        ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                        if(existingtimes == null){
                            ArrayList<String> startTimeList = new ArrayList<String>();
                            startTimeList.add(s.toString());
                            activityEndTimes.put(currActivityID,startTimeList);
                        }
                        else{
                            existingtimes.add(s.toString());
                            activityEndTimes.put(currActivityID,existingtimes);
                        }

                        if(prepRoomStack.size()==1){
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            frontButton.setVisibility(View.INVISIBLE);
                            backButton.setVisibility(View.INVISIBLE);

                            processText.setText("Surgery");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);

                            prepRoomStack1.push(prepRoomStack.peek());
                            prepRoomStack.pop();
                        }
                        else{
                            prepRoomStack.pop();
                            prepRoomStack1.push(currActivityID);
                            String newActivityID = prepRoomStack.peek();
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);

                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Surgery")){
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());

                        ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                        if(existingtimes == null){
                            ArrayList<String> startTimeList = new ArrayList<String>();
                            startTimeList.add(s.toString());
                            activityEndTimes.put(currActivityID,startTimeList);
                        }
                        else{
                            existingtimes.add(s.toString());
                            activityEndTimes.put(currActivityID,existingtimes);
                        }

                        if(surgeryStack.size()==1){
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            frontButton.setVisibility(View.INVISIBLE);
                            backButton.setVisibility(View.INVISIBLE);

                            processText.setText("Post-Catheterization");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);

                            surgeryStack1.push(surgeryStack.peek());
                            surgeryStack.pop();
                        }
                        else{
                            surgeryStack.pop();
                            surgeryStack1.push(currActivityID);
                            String newActivityID = surgeryStack.peek();
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);

                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Post-Surgery Assessment")){
                        Date currentDate = new Date(System.currentTimeMillis());
                        CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                        Log.e("current time", s.toString());

                        ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                        if(existingtimes == null){
                            ArrayList<String> startTimeList = new ArrayList<String>();
                            startTimeList.add(s.toString());
                            activityEndTimes.put(currActivityID,startTimeList);
                        }
                        else{
                            existingtimes.add(s.toString());
                            activityEndTimes.put(currActivityID,existingtimes);
                        }

                        if(postCathStack.size()==1){
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            frontButton.setVisibility(View.INVISIBLE);
                            backButton.setVisibility(View.INVISIBLE);

                            processText.setText("Post-Catheterization");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);

                            postCathStack1.push(postCathStack.peek());
                            postCathStack.pop();
                        }
                        else {
                            postCathStack.pop();
                            postCathStack1.push(currActivityID);
                            String newActivityID = postCathStack.peek();
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);

                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Post-Surgery Assessment")){
                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(currActivityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(currActivityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(currActivityID,existingtimes);
                            }

                            if(postCathAssesmentStack.size()==1){
                                progressText.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                frontButton.setVisibility(View.INVISIBLE);
                                backButton.setVisibility(View.INVISIBLE);

                                processText.setText("Post-Catheterization");
                                processText.setVisibility(View.VISIBLE);
                                activityName.setVisibility(View.INVISIBLE);
                                startButton.setText("Start");
                                startButton.setVisibility(View.INVISIBLE);
                                clearButton.setText("Begin");
                                clearButton.setVisibility(View.VISIBLE);

                                postCathAssesmentStack1.push(postCathAssesmentStack.peek());
                                postCathAssesmentStack.pop();
                            }
                            else {
                                postCathAssesmentStack.pop();
                                postCathAssesmentStack1.push(currActivityID);
                                String newActivityID = postCathAssesmentStack.peek();
                                currActivityID = newActivityID;
                                currActivityName = activityNameIDMap.get(currActivityID).get(0);
                                activityName.setText(currActivityName);

                                startButton.setText("Start");
                                clearButton.setVisibility(View.INVISIBLE);
                            }
                    }
                }
                else if(startButtonText.equals("Finish")){
                    System.out.println("Start Times : " + activityStartTimes);
                    System.out.println("End Times : " + activityEndTimes);
                    System.out.println("pid : " + pid);

                    Date currentDate = new Date(System.currentTimeMillis());
                    CharSequence mid = DateFormat.format("MMddyyyy", currentDate.getTime());
                    System.out.println("mid : " + mid);

                    Intent intent = new Intent(recordTime.this, PushData.class);
                    intent.putExtra("patientId", pid);
                    intent.putExtra("mid", mid);
                    intent.putExtra("activityStartTimes", activityStartTimes);
                    intent.putExtra("activityEndTimes", activityEndTimes);
                    intent.putExtra("paMap",patientActivityMap);
                    intent.putExtra("activityDetails",activityDetails);
                    recordTime.this.startActivity(intent);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mainActivity = (String)processText.getText();
                clearButtonText = (String)clearButton.getText();
                if(clearButtonText.equals("Begin")){
                    processText.setVisibility(View.INVISIBLE);
                    clearButton.setVisibility(View.INVISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                    activityName.setVisibility(View.VISIBLE);
                    if(mainActivity.equals("Pre-Catheterization")){
                        if(preCathProcessStack.isEmpty()){
                            processText.setText("Patient Prep");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            String newActivityID = preCathProcessStack.peek();
                            preCathProcessStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                        }
                    }
                    else if(mainActivity.equals("Patient Prep")){
                        if(patientPrepStack.isEmpty()){
                            processText.setText("Room Prep");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else {
                            String newActivityID = patientPrepStack.peek();
                            patientPrepStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                        }
                    }
                    else if(mainActivity.equals("Room Prep")){
                        if(prepRoomStack.isEmpty()){
                            processText.setText("Surgery");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            String newActivityID = prepRoomStack.peek();
                            prepRoomStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                        }
                    }
                    else if(mainActivity.equals("Surgery")){
                        if(surgeryStack.isEmpty()) {
                            processText.setText("Post-Catheterization");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            String newActivityID = surgeryStack.peek();
                            surgeryStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                        }
                    }
                    else if(mainActivity.equals("Post-Catheterization")){
                        if(postCathStack.isEmpty()){
                            processText.setText("Post-Surgery Assessment");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            String newActivityID = postCathStack.peek();
                            postCathStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                        }
                    }
                    else if(mainActivity.equals("Post-Surgery Assessment")){
                        if(postCathAssesmentStack.isEmpty()){
                            processText.setVisibility(View.INVISIBLE);
                            startButton.setText("Finish");
                            clearButton.setVisibility(View.INVISIBLE);
                            startButton.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            String newActivityID = postCathAssesmentStack.peek();
                            postCathAssesmentStack1.push(newActivityID);
                            currActivityID = newActivityID;
                            currActivityName = activityNameIDMap.get(currActivityID).get(0);
                            activityName.setText(currActivityName);
                            System.out.println(startButtonText);
                            if(startButtonText.equals("Start")){
                                activityName.setText(currActivityName);
                            }
                            else if(startButtonText.equals("Stop")){
                                startButton.setText("Start");
                                clearButton.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
                else if(clearButtonText.equals("Clear")){
                    clearButton.setVisibility(View.INVISIBLE);
                    startButton.setText("Start");
                    activityStartTimes.remove(currActivityID);
                }
            }
        });

        frontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivity.equals("Pre-Catheterization")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(preCathProcessStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Patient Prep");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        preCathProcessStack1.push(preCathProcessStack.peek());
                        preCathProcessStack.pop();
                    }
                    else{
                        preCathProcessStack.pop();
                        preCathProcessStack1.push(currActivityID);
                        String newActivityID = preCathProcessStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Patient Prep")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(patientPrepStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Room Prep");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        patientPrepStack1.push(patientPrepStack.peek());
                        patientPrepStack.pop();
                    }
                    else{
                        patientPrepStack.pop();
                        patientPrepStack1.push(currActivityID);
                        String newActivityID = patientPrepStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Room Prep")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(prepRoomStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Surgery");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        prepRoomStack1.push(prepRoomStack.peek());
                        prepRoomStack.pop();
                    }
                    else{
                        prepRoomStack.pop();
                        prepRoomStack1.push(currActivityID);
                        String newActivityID = prepRoomStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Surgery")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(surgeryStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Post-Catheterization");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        surgeryStack1.push(surgeryStack.peek());
                        surgeryStack.pop();
                    }
                    else{
                        surgeryStack.pop();
                        surgeryStack1.push(currActivityID);
                        String newActivityID = surgeryStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Post-Catheterization")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(postCathStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Post-Surgery Assessment");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        postCathStack1.push(postCathStack.peek());
                        postCathStack.pop();
                    }
                    else{
                        postCathStack.pop();
                        postCathStack1.push(currActivityID);
                        String newActivityID = surgeryStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Post-Surgery Assessment")){
                    progress = progress + 1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    if(postCathAssesmentStack.size()==1){
                        progressText.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        frontButton.setVisibility(View.INVISIBLE);
                        backButton.setVisibility(View.INVISIBLE);

                        processText.setText("Post-Surgery Assessment");
                        processText.setVisibility(View.VISIBLE);
                        activityName.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                        startButton.setVisibility(View.INVISIBLE);
                        clearButton.setText("Begin");
                        clearButton.setVisibility(View.VISIBLE);

                        postCathAssesmentStack1.push(postCathAssesmentStack.peek());
                        postCathAssesmentStack.pop();
                    }
                    else{
                        postCathAssesmentStack.pop();
                        postCathAssesmentStack1.push(currActivityID);
                        String newActivityID = surgeryStack.peek();
                        currActivityID = newActivityID;
                        currActivityName = activityNameIDMap.get(currActivityID).get(0);
                        activityName.setText(currActivityName);

                        startButton.setText("Start");
                        clearButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mainActivity.equals("Pre-Catheterization")){
                    String previousActivity = preCathProcessStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    preCathProcessStack.push(previousActivity);
                    preCathProcessStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(preCathProcessStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Patient Prep")){
                    String previousActivity = patientPrepStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    patientPrepStack.push(previousActivity);
                    patientPrepStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(patientPrepStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Room Prep")){
                    String previousActivity = prepRoomStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    prepRoomStack.push(previousActivity);
                    prepRoomStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(prepRoomStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Surgery")){
                    String previousActivity = surgeryStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    surgeryStack.push(previousActivity);
                    surgeryStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(surgeryStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Post-Catheterization")){
                    String previousActivity = postCathStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    postCathStack.push(previousActivity);
                    postCathStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(postCathStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
                else if(mainActivity.equals("Post-Surgery Assessment")){
                    String previousActivity = postCathAssesmentStack1.peek();
                    currActivityID = previousActivity;
                    currActivityName = activityNameIDMap.get(currActivityID).get(0);
                    postCathAssesmentStack.push(previousActivity);
                    postCathAssesmentStack1.pop();
                    activityName.setText(currActivityName);
                    progress = progress -  1;
                    progressBar.setProgress(progress);
                    progressText.setText(progress+"/"+total);
                    System.out.println(startButtonText);
                    String currStartButton = (String)startButton.getText();
                    if(currStartButton.equals("Stop")){
                        clearButton.setVisibility(View.INVISIBLE);
                        startButton.setText("Start");
                    }
                    if(postCathAssesmentStack1.size()==1){
                        backButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }
}
