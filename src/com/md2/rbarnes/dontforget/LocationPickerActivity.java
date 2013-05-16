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

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class LocationPickerActivity extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView placesSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
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
	
	public boolean onOptionsItemSelected(MenuItem item){
	
		 
	        
		
		switch(item.getItemId()) {
	        
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
            Crouton.makeText(this, "Looking for who called you!", Style.INFO).show();
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
