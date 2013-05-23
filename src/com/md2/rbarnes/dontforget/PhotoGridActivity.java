package com.md2.rbarnes.dontforget;

import com.rbarnes.other.ImageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class PhotoGridActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
 
        GridView gridView = (GridView) findViewById(R.id.grid_view);
 
        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(this));
    }
}
