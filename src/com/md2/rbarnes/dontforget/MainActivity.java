/*
 * project	DontForget
 * 
 * package	com.md2.rbarnes.dontforget
 * 
 * @author	Ronaldo Barnes
 * 
 * date		May 16, 2013
 */
package com.md2.rbarnes.dontforget;

import com.rbarnes.other.WebInterface;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends Activity{
	
	private LocationManager locationManager;
	private String provider;
	private double _lat;
	private double _lng;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
       
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
		
		if(location != null){
			Crouton.makeText(this, "Your Location was found!", Style.INFO).show();
			_lat = location.getLatitude(); 
			_lng = location.getLongitude();
		}else{
			_lat = 0;
			_lng = 0;
		}
      //detect Internet connection
      	final Boolean connected = WebInterface.getConnectionStatus(this);
      		
      	if(!connected){
    			Crouton.makeText(this, "No network found!", Style.ALERT).show();
     		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		searchItem.setVisible(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_add:
        
			Intent addIntent = new Intent(this, AddActivity.class);
			addIntent.putExtra("lat", _lat);
			addIntent.putExtra("lng", _lng);
			startActivity(addIntent);
			return true;
		case R.id.action_map:
	        
			Intent mapIntent = new Intent(this, MapActivity.class);
			startActivity(mapIntent);
			return true;
		case R.id.action_calendar:
	        
			Crouton.makeText(this, "calendar works", Style.INFO).show();
			return true;
		case R.id.action_list:
			
			Intent listIntent = new Intent(this, ListViewActivity.class);
			startActivity(listIntent);
			
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	 
    

}
