package com.siolabs.apitest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	StrictMode.ThreadPolicy policy = new StrictMode.
			    ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy); 
    	
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String username = "a6a16ee6-dcc1-4e32-94d6-32172cd831ed";
        String password = "6792c806-5a29-4e36-a291-4e0203beaab1";
        
        
        String url = "https://contactsapi2403.apispark.net/v1/contacts";
        
        HttpUriRequest request = new HttpGet(url); // Or HttpPost(), depends on your needs  
        String credentials = username + ":" + password;  
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);  
        request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

        HttpClient httpclient = new DefaultHttpClient();  
        try {
        	HttpResponse response = httpclient.execute(request);
        	
            StatusLine status = response.getStatusLine();
            
            //check if everything is OK
            if(status.getStatusCode() != 200){
            	Log.d("com.siolabs.apitest", "Wrong statusCode from site");
            }
            	InputStream content = response.getEntity().getContent();
            	BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            	
            	String line = null;
            	StringBuilder jsonData = new StringBuilder();
            	
            	while( (line=reader.readLine()) != null){
            		jsonData.append(line);
            	}
            	
            	
            	Log.d("jsonData", jsonData.toString());
            
        	
        	
             	//Log.d("Login: Response", EntityUtils.toString(response.getEntity()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        HttpURLConnection urlConnection = null;
        
        //code to save a details in the api cloud
        try {
			URL urlObj = new URL(url);
			
			//create a request to apispark and open connection
			urlConnection =(HttpURLConnection) urlObj.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.addRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
			urlConnection.setRequestProperty("Content-Type", "application/json"); 
			urlConnection.setDoOutput(true);
	
			urlConnection.connect();
			//get the writer
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
			
			JSONObject data = new JSONObject();
			data.put("firstname", "Nirmal");
			data.put("lastname","Parwate");
			data.put("age", 25);
			
			writer.write(data.toString());
			writer.flush();
			writer.close();
			//write the data
			
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			while ((line = reader.readLine()) != null) {
			    Log.d("APITEST", line);
			}
			
			Log.d("APITEST","Everything successful");
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        
        
    }
}
