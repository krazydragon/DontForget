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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddActivity extends Activity {
	
	private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private double _lat;
	private double _lng;
	private Intent _locationIntent;
	private Context _context; 
	private Bitmap _photo;
	private ToggleButton _alertToggle;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        
        _context = this;
        _locationIntent = new Intent(this, LocationPickerActivity.class);
        _alertToggle = (ToggleButton) findViewById(R.id.toggleButton1);
        
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
        imageView.setDrawingCacheEnabled(true);
        
        
        Button addButton =(Button)findViewById(R.id.captureButton);
        addButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	startCamera();
		    	
		    	
		    }
		 });
        
        Button saveButton =(Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	EditText colorField   = (EditText)findViewById(R.id.titleText);
				String titleText = colorField.getText().toString();
		    	saveImage(titleText);
		    	if (_alertToggle.isChecked()==(true)) {

		    		showNotication(titleText);

		    		   }
		    	//showNotification("Information Saved");
		    	
		    	
		    }
		 });
        
        
        _alertToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                	//showNotification("Reminder Enabled");
                } else {
                	//showNotification("Reminder Disabled");
                }
            }
        });
        
        ToggleButton locationToggle = (ToggleButton) findViewById(R.id.ToggleButton01);
        locationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
       
        			//showNotification("Location Enabled");
        			launchPlaceSeach();
                } else {
                   // showNotification("Location Disabled");
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
			
			Intent listIntent = new Intent(this, ListViewActivity.class);
			startActivity(listIntent);
			
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	 //LAUNCH CAMERA
    public void startCamera() {
        Log.i("CAMERA", "Camera Launched");
        /*String fileName = "pic.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, IMAGE_CAPTURE);*/
        
        Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
       
        
    }

    //DISPLAY PREVIEW
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
            if (requestCode == CAMERA_REQUEST) { 
                _photo = (Bitmap) data.getExtras().get("data"); 
                imageView.setImageBitmap(_photo);
            }
        
        
    }
    
    void showNotification(String msg){
    	
//    	Crouton.makeText(this, msg, Style.INFO).show();
    	
    }
    void launchPlaceSeach(){
    	
    	startActivity(_locationIntent);
    	
    }
    
    void saveImage(String title){
    	
    	Bitmap bitmap = imageView.getDrawingCache();
    	OutputStream outStream = null;
    	   File file = new File(_context.getExternalFilesDir(null), title +".PNG");
    	   try {
    	    outStream = new FileOutputStream(file);
    	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
    	    outStream.flush();
    	    outStream.close();
    

    	   Toast.makeText(_context, "Saved", Toast.LENGTH_LONG).show();
    	   
    } catch (FileNotFoundException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
    } catch (IOException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();
    }
    }
    
private void showNotication(String contentText){
    	
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        
        Notification noti = new Notification.Builder(this)
        .setSmallIcon(R.drawable.simle) // notification icon
        .setContentTitle("Don't Forget...")
        .setContentText(contentText)
        .setContentIntent(pIntent).build();
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    // Hide the notification after its selected
    noti.flags |= Notification.FLAG_AUTO_CANCEL;

    notificationManager.notify(0, noti);
    }
}
