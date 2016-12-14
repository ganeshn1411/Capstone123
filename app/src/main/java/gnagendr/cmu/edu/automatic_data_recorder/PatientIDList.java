package gnagendr.cmu.edu.automatic_data_recorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class PatientIDList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_idlist);
        Intent intent = getIntent();
        final HashMap<String,ArrayList<String>> patientActivityMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("patientActivityMap");
        final HashMap<String,ArrayList<String>> activityDetails = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("activityDetails");

        System.out.println(patientActivityMap);
        System.out.println(activityDetails);

        String message = patientActivityMap.size() + " patients have procedures today.";
        TextView patientCountText = (TextView)findViewById(R.id.patientCountButton);
        patientCountText.setText(message);

        Set keys = patientActivityMap.keySet();
        Iterator iterator = keys.iterator();
        int base = 600;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_patient_idlist);
        while (iterator.hasNext()) {
            final String key = (String) iterator.next();
            Button btn = new Button(this);
            btn.setText(key);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 120);
            layoutParams.setMargins(0,base,0,0);
            base = base + 150;
            btn.setLayoutParams(layoutParams);
            btn.setTextSize(20);
            btn.setBackgroundColor(getResources().getColor(R.color.white));
            rl.addView(btn);
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PatientIDList.this, recordTime.class);
                    intent.putExtra("patientId", key);
                    intent.putExtra("activityDetails",activityDetails);
                    intent.putExtra("paMap",patientActivityMap);
                    PatientIDList.this.startActivity(intent);
                }
            });
        }
    }
}
