package edu.bluejack16_2.jrpost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TranslateResultActivity extends AppCompatActivity {

    TextView translateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_result);
        Intent intent = getIntent();
        String translateResultString = intent.getStringExtra("translateResult");
        translateResult = (TextView) findViewById(R.id.translateResultTV);
        translateResult.setText((translateResultString));
    }
}
