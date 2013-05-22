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

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class LocationPickerActivity extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView placesSearchView;
	private GoogleMap map;
	private double _lat = 28.59671310;
	private double _lng = -81.30159879;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		Crouton.makeText(this, "Looking for places near you!", Style.INFO).show();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
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
    	
        return true;
    }
 
    public boolean onClose() {
        
        return false;
    }
    protected boolean isAlwaysExpanded() {
        return false;
    }

}
