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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;



public class MapActivity extends Activity implements LocationListener{
	
	private GoogleMap map;
	private LatLng _latLng;
    private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		// Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        
        
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		
		// Initialize the location fields
        if (location != null) {
          Log.i("PROVIDER","Provider " + provider + " has been selected.");
          onLocationChanged(location);
          setupMap();
        } else {
          
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_add:
        
			Intent intent = new Intent(this, AddActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_map:
	        
			Crouton.makeText(this, "map works", Style.INFO).show();
			return true;
		case R.id.action_calendar:
	        
			Crouton.makeText(this, "calendar works", Style.INFO).show();
			return true;
		case R.id.action_list:
			Crouton.makeText(this, "list works", Style.INFO).show();
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	 //Resume Location updates
    @Override
    protected void onResume() {
      super.onResume();
      locationManager.requestLocationUpdates(provider, 0, 0, this);
    }

    //Pause Location updates
    @Override
    protected void onPause() {
      super.onPause();
      locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
      double lat = (double) (location.getLatitude());
      double lng = (double) (location.getLongitude());
      _latLng = new LatLng(lat, lng);
      
      ;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
      Toast.makeText(this, "Enabled new provider " + provider,
          Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
      Toast.makeText(this, "Disabled provider " + provider,
          Toast.LENGTH_SHORT).show();
    }
    
    private void setupMap(){
		@SuppressWarnings("unused")
		Marker resultPoint = map.addMarker(new MarkerOptions()
        .position(_latLng)
        .title("You are here!"));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(_latLng, 1));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    
		 
	}

}
