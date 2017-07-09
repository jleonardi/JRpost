package edu.bluejack16_2.jrpost.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.bluejack16_2.jrpost.TranslateChoiceActivity;

/**
 * Created by User on 7/8/2017.
 */

public class ReadAvailableLanguageTask extends TemplateTask{


    TranslateChoiceActivity activity;

    public ReadAvailableLanguageTask(TranslateChoiceActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //Log.e("Response", "" + server_response);

        try {
            JSONObject json = new JSONObject(server_response);
            JSONObject languagesJson = json.getJSONObject("langs");

            String convertedJson = languagesJson.toString().substring(1, languagesJson.toString().length() - 1);
            String[] codeLanguages = convertedJson.split(",");
            activity.doneReading(codeLanguages);

        } catch (JSONException e) {
            Log.e("Response", "Gagalllzz" + e.getMessage());
            e.printStackTrace();
        }
    }

}
