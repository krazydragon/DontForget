/*
 * project	DontForget
 * 
 * package	com.rbarnes.other
 * 
 * @author	Ronaldo Barnes
 * 
 * date		May 22, 2013
 */
package com.rbarnes.other;

import java.net.MalformedURLException;
import java.net.URL;


import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class SearchService extends IntentService{
	
	private static int _resultStatus = Activity.RESULT_CANCELED;;
	String _result = null;
	String _phoneNumber = null;

	public SearchService() {
		super("SearchService");


		
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
        
        	String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
        		    "json?location="+28.59671310+","+-81.30159879+
        		    "&radius=1000&sensor=true" +
        		    "&types=food|bar|store|museum|art_gallery"+
        		    "&key=AIzaSyCgYeuzXjh7-yi_8ybuBf_s_TD_4W7ca3c";
    		
    		URL searchURL = null;

    		Boolean connected = WebInterface.getConnectionStatus(this);
    		
    		if(connected){
    			try{
        			searchURL = new URL(url);
        			_result = WebInterface.getUrlStringResponse(searchURL);
        			if(_result.length() > 0){
        				
        				Log.i("RESULT", _result);
        				_resultStatus = Activity.RESULT_OK;
        			}
        		} catch (MalformedURLException e){
        			Log.e("BAD URL", "MALFORMED URL");
        			searchURL = null;
        		}
    		}
    		
    		
    		// Messenger is received and API response is sent back via a message object
        	Messenger messenger = (Messenger) extras.get("messenger");
        	Message msg = Message.obtain();
        	msg.arg1 = _resultStatus;
        	msg.obj = _result;
    
        	try{
        		messenger.send(msg);
        	} catch (android.os.RemoteException e){
        		Log.e(getClass().getName(), "EXCEPTION sending message", e);
        	}  
        
		
	}

}
