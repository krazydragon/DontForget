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

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ListViewActivity extends Activity {
	  
	  private ListView mainListView ;
	  private ArrayAdapter<String> listAdapter ;
	  
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    
	    // Find the ListView resource. 
	    mainListView = (ListView) findViewById( R.id.mainListView );

	    // Create and populate a List of event names.
	    String[] temp = new String[] { "Mike's 3rd birthday", "Klyles Class Filed trip", "5th Wedding Aniversry", "Hello Cupcake!", "Children's Hands on Museum", "Legendary Donuts",};  
	    ArrayList<String> planetList = new ArrayList<String>();
	    planetList.addAll( Arrays.asList(temp) );
	    
	    // Create ArrayAdapter using the planet list.
	    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
	    
	    // Set the ArrayAdapter as the ListView's adapter.
	    mainListView.setAdapter( listAdapter );      
	  }
	}