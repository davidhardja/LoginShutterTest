package com.example.android.loginshuttertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bocist-8 on 30/09/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ArrayList<Shuttersong> gallery;

    public ImageAdapter(Context c, ArrayList<Shuttersong> galleryUrl){
        mContext= c;
        gallery = galleryUrl;
    }

    public int getCount(){
        return gallery.size();
    }

    public Object getItem(int position){
        return gallery.get(position).getThumb_url();
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView,ViewGroup parent){
        ImageView imageView = new ImageView(mContext);
        UrlImageViewHelper.setUrlDrawable(imageView, gallery.get(position).getThumb_url());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;
    }
}
