package jialiw.cmu.edu.capstoneappv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        TextView analysis = (TextView) findViewById(R.id.analysis);
        analysis.setText("Final conslusion page");
    }
}
