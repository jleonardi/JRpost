package edu.bluejack16_2.jrpost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.jrpost.tasks.ReadAvailableLanguageTask;
import edu.bluejack16_2.jrpost.tasks.TranslateStoryTask;
import edu.bluejack16_2.jrpost.utilities.LanguageModel;

public class TranslateChoiceActivity extends AppCompatActivity {

    Spinner toLanguageSpinner, fromLanguageSpinner;
    Button translateButton;
    String text;
    TranslateChoiceActivity thisActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        text = intent.getStringExtra("content");
        setContentView(R.layout.activity_translate_choice);
        toLanguageSpinner = (Spinner) findViewById(R.id.toLanguageSpinner);
        fromLanguageSpinner = (Spinner) findViewById(R.id.fromLanguageSpinner);
        translateButton = (Button) findViewById(R.id.translateBtn);
        progressDialog = new ProgressDialog(TranslateChoiceActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ReadAvailableLanguageTask(this).execute("https://translate.yandex.net/api/v1.5/tr.json/getLangs?ui=en&key=trnsl.1.1.20170708T103845Z.6169a37cb6e16ce5.54e011c42b5573f742cfb006014bf678ca1ffd77");
    }

    public void doneTranslating(String translateResult){
        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), TranslateResultActivity.class);

        intent.putExtra("translateResult", translateResult);

        startActivity(intent);
        finish();
    }

    public void doneReading(String[] codeLanguages) {
        try {
//            LanguageModel[] languageModels = new LanguageModel[255];
            Vector<LanguageModel> vLanguageModels = new Vector<LanguageModel>();
            int currentIndex = 0;
            for (String str : codeLanguages) {
                String[] current = str.split(":");
                String code = current[0].substring(1, current[0].length() - 1);
                String lang = current[1].substring(1, current[1].length() - 1);
                LanguageModel languageModel = new LanguageModel(code, lang);
                //languageModels[currentIndex] = languageModel;
                vLanguageModels.add(languageModel);
                Log.e("Response", "status: " + code + " - " + lang);

            }
            LanguageModel[] languageModels = new LanguageModel[vLanguageModels.size()];
            for(int i = 0; i < vLanguageModels.size(); i++){
                languageModels[i] = vLanguageModels.get(i);
            }
            ArrayAdapter toLanguageAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, languageModels){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    //return super.getView(position, convertView, parent);
                    TextView label = new TextView(getApplicationContext());
                    label.setTextColor(Color.BLACK);
                    label.setText(((LanguageModel)getItem(position)).getLanguageName());
                    return label;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView label = new TextView(getApplicationContext());
                    label.setTextColor(Color.BLACK);
                    label.setText(((LanguageModel)getItem(position)).getLanguageName());
                    return label;
                }
            };
            ArrayAdapter fromLanguageAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, languageModels){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    //return super.getView(position, convertView, parent);
                    TextView label = new TextView(getApplicationContext());
                    label.setTextColor(Color.BLACK);
                    label.setText(((LanguageModel)getItem(position)).getLanguageName());
                    return label;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView label = new TextView(getApplicationContext());
                    label.setTextColor(Color.BLACK);
                    label.setText(((LanguageModel)getItem(position)).getLanguageName());
                    return label;
                }
            };

            toLanguageSpinner.setAdapter(toLanguageAdapter);

            fromLanguageSpinner.setAdapter(fromLanguageAdapter);
            thisActivity = this;
            translateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LanguageModel fromLanguage = (LanguageModel)fromLanguageSpinner.getSelectedItem();
                    LanguageModel toLanguage = (LanguageModel)toLanguageSpinner.getSelectedItem();
                    String lang = fromLanguage.getLanguageCode() + "-" + toLanguage.getLanguageCode();

                    String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170708T103845Z.6169a37cb6e16ce5.54e011c42b5573f742cfb006014bf678ca1ffd77" +
                            "&lang=" + lang;
                    Log.e("TranslateBtn", url);
                    new TranslateStoryTask(thisActivity).execute(url, text);
                    progressDialog = new ProgressDialog(TranslateChoiceActivity.this);
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });
            progressDialog.dismiss();
        } catch(Exception e){
            Log.e("Spinner Problem", e.getMessage());
        }

    }
}
