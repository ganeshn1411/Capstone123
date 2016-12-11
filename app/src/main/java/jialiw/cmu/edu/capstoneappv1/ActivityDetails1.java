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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class ActivityDetails1 extends AppCompatActivity {
    int total = 0;
    int progress = 1;
    String currActivityID = "";
    String currActivityName = "";
    String mainActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final HashMap<String, ArrayList<String>> activityNameIDMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityDetails");
        Log.v("activityNameIDMap",activityNameIDMap.toString());
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
                String startButtonText = (String) startButton.getText();
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
                }
                else if (startButtonText.equals("Stop")) {
                    System.out.println(currActivityID);
                    System.out.println(currActivityName);
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setText(progress + "/" + total);
                    progressText.setVisibility(View.VISIBLE);
                    frontButton.setVisibility(View.VISIBLE);
                    backButton.setVisibility(View.VISIBLE);
                    progress = progress + 1;

                    if (mainActivity.equals("Pre-Catheterization")) {
                    }
                    else if(mainActivity.equals("Surgery")){
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
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mainActivity = (String)processText.getText();
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
                            System.out.println("here");
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
                        }
                    }
                }
                else if(clearButtonText.equals("Clear")){
                    clearButton.setVisibility(View.INVISIBLE);
                    startButton.setText("Start");
                    //activityStartTimes.remove(currActivityID);
                }
            }
        });

        frontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(currActivityID);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println(currActivityID);
            }
        });
    }
}
