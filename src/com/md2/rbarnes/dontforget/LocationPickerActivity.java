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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rbarnes.other.SearchService;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class LocationPickerActivity extends Activity implements SearchView.OnQueryTextListener{
	private int userIcon;
	private SearchView placesSearchView;
	private GoogleMap map;
	private double _lat = 28.59671310;
	private double _lng = -81.30159879;
	
	//location manager
		private LocationManager locMan;

		//user marker
		private Marker userMarker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//Crouton.makeText(this, "Looking for places near you!", Style.INFO).show();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		userIcon = R.drawable.ic_launcher; 
		LatLng latLng = new LatLng(_lat, _lng);
    	Marker locationPoint = map.addMarker(new MarkerOptions()
        .position(latLng)
        .title("You are here!"));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		 placesSearchView = (SearchView) searchItem.getActionView();
	        setupSearchView(searchItem);
	 
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_add:
        
			Intent addIntent = new Intent(this, AddActivity.class);
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
	
	
	
	private void setupSearchView(MenuItem searchItem) {
		 
        if (isAlwaysExpanded()) {
        	placesSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
 
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
 
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            
            placesSearchView.setSearchableInfo(info);
        }
 
        placesSearchView.setOnQueryTextListener(this);
    }
 
    public boolean onQueryTextChange(String newText) {
    	
        return false;
    }
 
    public boolean onQueryTextSubmit(String query) {
    	lookupNumber(query);
        return true;
    }
 
    public boolean onClose() {
        
        return false;
    }
    protected boolean isAlwaysExpanded() {
        return false;
    }
    
    private void updatePlaces(){
		//get location manager
    	LocationManager locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		//get last location
		Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		double lat = lastLoc.getLatitude();
		double lng = lastLoc.getLongitude();
		//create LatLng
		LatLng lastLatLng = new LatLng(lat, lng);

		//remove any existing marker
		if(userMarker!=null) userMarker.remove();
		//create and set marker properties
		userMarker = map.addMarker(new MarkerOptions()
		.position(lastLatLng)
		.title("You are here")
		.icon(BitmapDescriptorFactory.fromResource(userIcon))
		.snippet("Your last recorded location"));
		//move to location
		map.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);
		
		//build places query string
		String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
				"json?location="+lat+","+lng+
				"&radius=1000&sensor=true" +
				"&types=food|bar|store|museum|art_gallery"+
				"&key=your_key_here";//ADD KEY
	}
	
	private class GetPlaces extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... placesURL) {
			//fetch places
			
			//build result as string
			StringBuilder placesBuilder = new StringBuilder();
			//process search parameter string(s)
			for (String placeSearchURL : placesURL) {
				HttpClient placesClient = new DefaultHttpClient();
				try {
					//try to fetch the data
					
					//HTTP Get receives URL string
					HttpGet placesGet = new HttpGet(placeSearchURL);
					//execute GET with Client - return response
					HttpResponse placesResponse = placesClient.execute(placesGet);
					//check response status
					StatusLine placeSearchStatus = placesResponse.getStatusLine();
					//only carry on if response is OK
					if (placeSearchStatus.getStatusCode() == 200) {
						//get response entity
						HttpEntity placesEntity = placesResponse.getEntity();
						//get input stream setup
						InputStream placesContent = placesEntity.getContent();
						//create reader
						InputStreamReader placesInput = new InputStreamReader(placesContent);
						//use buffered reader to process
						BufferedReader placesReader = new BufferedReader(placesInput);
						//read a line at a time, append to string builder
						String lineIn;
						while ((lineIn = placesReader.readLine()) != null) {
							placesBuilder.append(lineIn);
						}
					}
				}
				catch(Exception e){ 
					e.printStackTrace(); 
				}
			}
			return placesBuilder.toString();
		}
	}
	
	private void lookupNumber(String phoneNumber){
		//search Internet for business information using the white pages. also setup messenger to receive result
		Intent searchIntent = new Intent(getApplicationContext(), SearchService.class);
		
		
		
		
		startService(searchIntent);
	}

}
