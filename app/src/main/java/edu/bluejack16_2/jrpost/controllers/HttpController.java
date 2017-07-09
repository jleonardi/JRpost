package edu.bluejack16_2.jrpost.controllers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.R.attr.data;

/**
 * Created by User on 7/8/2017.
 */

public class HttpController {
    public static String httpPostRequest(Context context, String url, String email) {
        String response = "";
        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try {
//            LogUtils.d("RequestManager", url + " ");
//            LogUtils.e("data::", " " + data);
            URL urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//            data += "&" + URLEncoder.encode("Email", "UTF-8") + "="
//                    + URLEncoder.encode(email, "UTF-8");


            wr.write(data);
            wr.flush();

            //LogUtils.d("post response code", conn.getResponseCode() + " ");

            int responseCode = conn.getResponseCode();



            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //LogUtils.d("Error", "error");
        } finally {
            try {
                reader.close();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception ex) {
            }
        }
        //LogUtils.d("RESPONSE POST", response);
        return response;
    }
}
