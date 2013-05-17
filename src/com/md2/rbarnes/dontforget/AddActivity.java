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

import org.apache.http.util.LangUtils;

import com.google.android.gms.maps.model.LatLng;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class AddActivity extends Activity {
	
	private static final int IMAGE_CAPTURE = 0;
    private Uri imageUri;
    private ImageView imageView;
    private double _lat;
	private double _lng;
	private Intent _locationIntent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        
        _locationIntent = new Intent(this, LocationPickerActivity.class);
        
        Intent intent = getIntent();
        if (intent != null)
        {
            Bundle bundle = getIntent().getExtras();
            _lat = bundle.getDouble("lat");
            _lng = bundle.getDouble("lng");
            if((_lat > 0)&&(_lng > 0))
            {
            	_locationIntent.putExtra("lat", _lat);
    			_locationIntent.putExtra("lng", _lng);
            }
        }
        
        
        
        imageView = (ImageView)findViewById(R.id.arrowImageView);
        
        
        Button addButton =(Button)findViewById(R.id.captureButton);
        addButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	startCamera();
		    	
		    	
		    }
		 });
        
        Button saveButton =(Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	
		    	showNotification("Information Saved");
		    	
		    	
		    }
		 });
        
        ToggleButton alertToggle = (ToggleButton) findViewById(R.id.toggleButton1);
        alertToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                	showNotification("Reminder Enabled");
                } else {
                	showNotification("Reminder Disabled");
                }
            }
        });
        
        ToggleButton locationToggle = (ToggleButton) findViewById(R.id.ToggleButton01);
        locationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
       
        			showNotification("Location Enabled");
        			launchPlaceSeach();
                } else {
                    showNotification("Location Disabled");
                }
            }
        });

   
        
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
			Crouton.makeText(this, "list works", Style.INFO).show();
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	 //LAUNCH CAMERA
    public void startCamera() {
        Log.i("CAMERA", "Camera Launched");
        String fileName = "pic.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }

    //DISPLAY PREVIEW
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK){
                
                
                imageView.setImageURI(imageUri);
                
                
        }}
        
    }
    
    void showNotification(String msg){
    	
    	Crouton.makeText(this, msg, Style.INFO).show();
    	
    }
    void launchPlaceSeach(){
    	
    	startActivity(_locationIntent);
    	
    }

}
