package com.example.covidhospitals.smsIntegration;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SmsHelper extends AsyncTask<String,String,String> {

@Override
protected String doInBackground(String... params) {
        String contact=params[0];
        String msg=params[1];
        //here I have to pass

        try {
        URL url = new URL("https://stark-earth-78790.herokuapp.com/index.php?message_body="+msg+"&receiver_number="+contact); //Enter URL here

        //now try and error
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        // conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "GET" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
//                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
//                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//                    wr.write( postData );
//                }
        BufferedReader br = null;
        if(100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
        Log.e("A","connection oke");
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String strCurrentLine;
        while ((strCurrentLine = br.readLine()) != null) {
        System.out.println(strCurrentLine);
        }
        } else {
        br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }


        } catch (MalformedURLException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        return "";
        }


        }