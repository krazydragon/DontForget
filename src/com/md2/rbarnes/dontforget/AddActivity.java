package com.md2.rbarnes.dontforget;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class AddActivity extends Activity {
	
	private static final int IMAGE_CAPTURE = 0;
    private Uri imageUri;
    private ImageView imageView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_additem);
        
        
        imageView = (ImageView)findViewById(R.id.imageView1);
        Button addButton =(Button)findViewById(R.id.button1);
        addButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	startCamera();
		    	
		    	
		    }
		 });
        
        

   
        
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

}
