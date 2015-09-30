package com.example.android.loginshuttertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by bocist-8 on 30/09/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String url;

    public ArrayList<String> gallery;
    public Integer[] mThumbsIds={};

    public ImageAdapter(Context c, ArrayList<String> galleryUrl){
        mContext= c;
        gallery = galleryUrl;

    }

    public int getCount(){
        //return mThumbsIds.length;
        return gallery.size();
    }

    public Object getItem(int position){
        return gallery.get(position);
    }

    public long getItemId(int position){
        return 0;
    }

    public static Bitmap getBitmap(String s) {
        try {
            URL url = new URL(s);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    public View getView(int position, View convertView,ViewGroup parent){

        ImageView imageView = new ImageView(mContext);
        UrlImageViewHelper.setUrlDrawable(imageView, gallery.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;
    }
}
