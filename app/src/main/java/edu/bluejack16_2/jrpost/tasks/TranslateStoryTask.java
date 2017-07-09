package edu.bluejack16_2.jrpost.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import edu.bluejack16_2.jrpost.TranslateChoiceActivity;
import edu.bluejack16_2.jrpost.TranslateResultActivity;

/**
 * Created by User on 7/8/2017.
 */

public class TranslateStoryTask extends AsyncTask<String, String, String>{

    String server_response;
    TranslateChoiceActivity activity;

    public TranslateStoryTask(TranslateChoiceActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        Log.v("Catalog", "Prop:" + params[0]);

        try {
            //url = new URL(params[0]);
            //url = new URL(URLEncoder.encode(params[0]));
            url = new URL(params[0] + "&text=" + URLEncoder.encode(params[1]));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            int responseCode = urlConnection.getResponseCode();
            Log.v("Catalog", "HTTP:" + responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK){
                Log.v("Catalog", "Anjya");
                server_response = readStream(urlConnection.getInputStream());
                Log.v("CatalogResponse", server_response);
            }
            Log.v("Catalog", "Anjya2");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v("CatalogClientz", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("CatalogClientz", e.getMessage());
        } catch(Exception e){

            Log.v("CatalogClient Other Exception", e.getMessage() + "-");
        }

        return null;
//        URL url;
//        //url = new URL(params[0]);
//        HttpClient httpClient = new DefaultHttpClient();
//        try {
//            HttpResponse response = httpClient.execute(new HttpGet(params[0]));
//            StatusLine statusLine = response.getStatusLine();
//            Log.v("CatalogResponse: status", statusLine.toString());
//            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                response.getEntity().writeTo(out);
//                String responseString = out.toString();
//                out.close();
//            } else{
//                response.getEntity().getContent().close();
//                throw new IOException(statusLine.getReasonPhrase());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    protected String readStream(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Translate Result", "" + server_response);
        try {
            JSONObject json = new JSONObject(server_response);
            JSONArray jsonArr = json.getJSONArray("text");
            String result = jsonArr.getString(0);
            activity.doneTranslating(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            Log.e("Response", "" + server_response);
//
//        } catch (JSONException e) {
//            Log.e("Response", "Gagalllzz" + e.getMessage());
//            e.printStackTrace();
//        }
    }
}
