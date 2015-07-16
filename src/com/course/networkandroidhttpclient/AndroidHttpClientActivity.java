package com.course.networkandroidhttpclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AndroidHttpClientActivity extends Activity {
	private TextView mTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_http_client);
        
        mTextView = (TextView) findViewById(R.id.textView1);
        
        final Button loadButton = (Button) findViewById(R.id.button1);
        loadButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new HttpGetTask().execute();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_http_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HttpGetTask extends AsyncTask<Void, Void, String> {
    	private static final String USER_NAME = "aporter";
    	private static final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
				+ USER_NAME;
    	
    	AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
    	
    	@Override
    	protected String doInBackground(Void... params) {
    		HttpGet request = new HttpGet(URL);
    		ResponseHandler<String> responseHandler = new BasicResponseHandler();
    		
    		try {
    			return mClient.execute(request, responseHandler);
    		} catch (ClientProtocolException exception) {
    			exception.printStackTrace();
    		} catch (IOException exception) {
    			exception.printStackTrace();
    		}
    		return null;
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		if (null != mClient)
    			mClient.close();
    		
    		mTextView.setText(result);
    	}
    }
}
