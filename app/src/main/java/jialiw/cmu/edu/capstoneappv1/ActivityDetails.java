package jialiw.cmu.edu.capstoneappv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class ActivityDetails extends AppCompatActivity {
    int total = 0;
    int progress = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        final HashMap<String, ArrayList<String>> activityNameIDMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityDetails");
        System.out.println(activityNameIDMap);
        System.out.println(activityNameIDMap.keySet());
        final HashMap<String, ArrayList<String>> patientActivityMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("paMap");
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
        Set<String> keys1 = activityNameIDMap.keySet();
        Iterator iterator1 = keys1.iterator();
        Iterator listIterator = patientActivityMap.get(key).iterator();
        while(listIterator.hasNext()){
            activityStack.push((String)listIterator.next());
        }
        total = activityStack.size();
        System.out.println(activityStack);
        final Stack<String> preCathProcessStack = new Stack<>();
        final Stack<String> patientPrepStack = new Stack<>();
        final Stack<String> prepRoomStack = new Stack<>();
        final Stack<String> surgeryStack = new Stack<>();
        final Stack<String> postCathStack = new Stack<>();
        final Stack<String> postCathAssesmentStack = new Stack<>();

        while(!activityStack.isEmpty()){
            String activityID = activityStack.peek();
            activityStack.pop();
            activityID = activityID.replaceAll("[^a-zA-Z0-9,-]", "");
            System.out.println(activityID);
            String activityCategory = activityNameIDMap.get(activityID).get(1);
            if(activityCategory.equals("pre surgery")){
                preCathProcessStack.push(activityID);
            }
            else if(activityCategory.equals("post cath")){
                postCathStack.push(activityID);
            }
            else if(activityCategory.equals("room prep")){
                prepRoomStack.push(activityID);
            }
            else if(activityCategory.equals("surgery")){
                surgeryStack.push(activityID);
            }
            else if(activityCategory.equals("pre cath")){
                patientPrepStack.push(activityID);
            }
            else if(activityCategory.equals("pre cath assesment")){
                postCathAssesmentStack.push(activityID);
            }

        }

        final TextView activityName = (TextView) findViewById(R.id.activity_Name);
        activityName.setVisibility(View.INVISIBLE);

        final TextView processText = (TextView) findViewById(R.id.activityPage);
        processText.setText("Pre-Catheterization");

        final TextView progressText = (TextView)findViewById(R.id.progress);
        System.out.println("progress " + progressText.getText());
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

        System.out.println("grev " + activityNameIDMap);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mainActivity = (String)processText.getText();
                String startButtonText = (String)startButton.getText();
                if(startButtonText.equals("Start")){
                    startButton.setText("Stop");
                    clearButton.setText("Clear");
                    clearButton.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setText(progress+"/"+total);
                    progressText.setVisibility(View.VISIBLE);

                    String activityID = (String)activityName.getText();
                    if(activityNameIDMap.containsKey(activityID)) {
                        String activityVal = activityNameIDMap.get(activityID).get(0);
                        activityName.setText(activityVal);
                    }
                    else{
                        Set<String> activityIDKeys = activityNameIDMap.keySet();
                        Iterator keyIter = activityIDKeys.iterator();
                        while(keyIter.hasNext()){
                            String key = (String)keyIter.next();
                            String name = activityNameIDMap.get(key).get(0);
                            name = name.replaceAll("[^a-zA-Z0-9,-]", "");
                            activityID = activityID.replaceAll("[^a-zA-Z0-9,-]", "");
                            if(name.equals(activityID)){
                                String ID = key;
                                Date currentDate = new Date(System.currentTimeMillis());
                                CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                                Log.e("current time", s.toString());
                                ArrayList<String> existingtimes = activityStartTimes.get(ID);
                                if(existingtimes == null){
                                    ArrayList<String> startTimeList = new ArrayList<String>();
                                    startTimeList.add(s.toString());
                                    activityStartTimes.put(ID,startTimeList);
                                }
                                else{
                                    existingtimes.add(s.toString());
                                    activityStartTimes.put(ID,existingtimes);
                                }
                            }
                        }
                    }
                }
                else if(startButtonText.equals("Stop")){
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setText(progress+"/"+total);
                    progressText.setVisibility(View.VISIBLE);
                    progress = progress + 1;

                    if(mainActivity.equals("Pre-Catheterization")){
                        if(preCathProcessStack.size()==1){
                            activityName.setText(preCathProcessStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);

                            }
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }



                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }

                            processText.setText("Patient Prep");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                        }
                        else{
                            String activityID = (String)activityName.getText();
                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());
                            ArrayList<String> existingtimes = activityEndTimes.get(activityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(activityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(activityID,existingtimes);
                            }

                            preCathProcessStack.pop();
                            activityName.setText(preCathProcessStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);



                        }
                    }
                    else if(mainActivity.equals("Patient Prep")){
                        if(patientPrepStack.size()==1){
                            activityName.setText(patientPrepStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }

                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);


                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(activityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(activityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(activityID,existingtimes);
                            }


                            processText.setText("Room Prep");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                        }
                        else{

                            String activityID = (String)activityName.getText();

                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(activityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(activityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(activityID,existingtimes);
                            }


                            patientPrepStack.pop();
                            activityName.setText(patientPrepStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }


                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Room Prep")){
                        if(prepRoomStack.size()==1){
                            activityName.setText(prepRoomStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(activityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(activityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(activityID,existingtimes);
                            }

                            processText.setText("Surgery");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                        }
                        else{
                            String activityID = (String)activityName.getText();

                            System.out.println(activityID);

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(activityID);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(activityID,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(activityID,existingtimes);
                            }



                            prepRoomStack.pop();
                            activityName.setText(prepRoomStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Surgery")){
                        if(surgeryStack.size()==1){
                            activityName.setText(surgeryStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }

                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }



                            processText.setText("Post-Catheterization");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                        }
                        else{
                            String activityID = (String)activityName.getText();

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }


                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }



                            surgeryStack.pop();
                            activityName.setText(surgeryStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Post-Catheterization")){
                        if(postCathStack.size()==1){
                            activityName.setText(postCathStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }

                            progressText.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }


                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }



                            processText.setText("Post-Surgery Assessment");
                            processText.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Start");
                            startButton.setVisibility(View.INVISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                        }
                        else{

                            String activityID = (String)activityName.getText();

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }


                            postCathStack.pop();
                            activityName.setText(postCathStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                            startButton.setText("Start");
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(mainActivity.equals("Post-Surgery Assessment")){
                        if(postCathAssesmentStack.size()==1){
                            activityName.setText(postCathAssesmentStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }

                            progressText.setVisibility(View.INVISIBLE);

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }


                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }


                            activityName.setVisibility(View.INVISIBLE);
                            startButton.setText("Finish");
                            startButton.setVisibility(View.VISIBLE);
                            clearButton.setVisibility(View.INVISIBLE);
                        }
                        else{

                            String activityID = (String)activityName.getText();

                            String IDnew = "";

                            Set<String> activityIDKeys = activityNameIDMap.keySet();
                            Iterator activityIDKeysiterator = activityIDKeys.iterator();
                            while(activityIDKeysiterator.hasNext()){
                                String currID = (String)activityIDKeysiterator.next();
                                String currName = activityNameIDMap.get(currID).get(0);
                                if(currName.equals(activityID)){
                                    IDnew = currID;
                                }
                            }

                            Date currentDate = new Date(System.currentTimeMillis());
                            CharSequence s = DateFormat.format("dd/MM/yyyy hh:mm:ss", currentDate.getTime());
                            Log.e("current time", s.toString());

                            ArrayList<String> existingtimes = activityEndTimes.get(IDnew);
                            if(existingtimes == null){
                                ArrayList<String> startTimeList = new ArrayList<String>();
                                startTimeList.add(s.toString());
                                activityEndTimes.put(IDnew,startTimeList);
                            }
                            else{
                                existingtimes.add(s.toString());
                                activityEndTimes.put(IDnew,existingtimes);
                            }


                            postCathAssesmentStack.pop();
                            activityName.setText(postCathAssesmentStack.peek());
                            activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
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
                    CharSequence mid = DateFormat.format("ddMMyyyy", currentDate.getTime());
                    System.out.println("mid : " + mid);


                    Intent intent = new Intent(ActivityDetails.this, PushData.class);
                    intent.putExtra("patientId", pid);
                    intent.putExtra("mid", mid);
                    intent.putExtra("activityStartTimes", activityStartTimes);
                    intent.putExtra("activityEndTimes", activityEndTimes);
                    ActivityDetails.this.startActivity(intent);


                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String mainActivity = (String)processText.getText();
                String clearButtonText = (String)clearButton.getText();
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
                            activityName.setText(preCathProcessStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
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
                        else{
                            activityName.setText(patientPrepStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
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
                            activityName.setText(prepRoomStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                        }
                    }
                    else if(mainActivity.equals("Surgery")){
                        if(surgeryStack.isEmpty()){
                            processText.setText("Post-Catheterization");
                            processText.setVisibility(View.VISIBLE);
                            clearButton.setText("Begin");
                            clearButton.setVisibility(View.VISIBLE);
                            startButton.setVisibility(View.INVISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            activityName.setText(surgeryStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
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
                            activityName.setText(postCathStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                        }
                    }
                    else if(mainActivity.equals("Post-Surgery Assessment")){
                        if(postCathAssesmentStack.isEmpty()){
                            //processText.setText("Post-Surgery Assessment");
                            processText.setVisibility(View.INVISIBLE);
                            startButton.setText("Finish");
                            clearButton.setVisibility(View.INVISIBLE);
                            startButton.setVisibility(View.VISIBLE);
                            activityName.setVisibility(View.INVISIBLE);
                        }
                        else{
                            activityName.setText(postCathAssesmentStack.peek());
                            String activityID = (String)activityName.getText();
                            if(activityNameIDMap.containsKey(activityID)) {
                                String activityVal = activityNameIDMap.get(activityID).get(0);
                                activityName.setText(activityVal);
                            }
                        }
                    }
                }
                else{
                    clearButton.setVisibility(View.INVISIBLE);
                    startButton.setText("Start");
                }
            }
        });
    }
}
