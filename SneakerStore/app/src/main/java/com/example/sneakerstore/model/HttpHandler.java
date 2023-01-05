package com.example.sneakerstore.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    public static String getMethod(String s) {
        System.out.println(s);
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(s);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line = "";
            while((line = bufferedReader.readLine()) !=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String deleteMethod(String urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            Log.i("Re", urls);
            url = new URL(urls);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            System.out.println("IN");
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            System.out.println(result);
        }catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }
    public static String updateCart(String urls, JSONArray jsonArray) {
        String status = "0";
        URL url = null;
        try {
            url = new URL(urls);

            // Uploading process
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PATCH");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(jsonArray.toString());
            status = Integer.toString(connection.getResponseCode());
            os.flush();
            os.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Log.i("INFO", status);
        return status;
    }
}
