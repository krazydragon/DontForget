package com.rbarnes.other;

import com.md2.rbarnes.dontforget.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context _context;
 
    // Keep all Images in array
    public Integer[] _thumbIds = {
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle, R.drawable.simle,
            R.drawable.simle
    };
 
    // Constructor
    public ImageAdapter(Context c){
    	_context = c;
    }
 
    @Override
    public int getCount() {
        return _thumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return _thumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(_context);
        imageView.setImageResource(_thumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
 
}
