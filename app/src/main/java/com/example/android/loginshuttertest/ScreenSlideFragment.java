package com.example.android.loginshuttertest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by bocist-8 on 05/10/15.
 */
public class ScreenSlideFragment extends Fragment {
    String urlpp = "";
    ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        System.out.println("DUREN"+rootView.getChildAt(0));
        return rootView;
    }

    public void setPP(String url){
        this.urlpp = url;

        //RoundedImageView rs = (RoundedImageView) rootView.getChildAt(0);
        //UrlImageViewHelper.setUrlDrawable(rs, url);
    }

}
