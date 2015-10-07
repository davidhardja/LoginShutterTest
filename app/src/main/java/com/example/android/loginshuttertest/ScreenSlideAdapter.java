package com.example.android.loginshuttertest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by bocist-8 on 05/10/15.
 */
public class ScreenSlideAdapter extends FragmentStatePagerAdapter {
    private int numPage = 2;
    private String urlPP = "";
    StatusSlideFragment stsf;
    ScreenSlideFragment ssf;


    public ScreenSlideAdapter(FragmentManager fm, String pp) {
        super(fm);
        this.urlPP = pp;
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0){
            stsf = new StatusSlideFragment();
            return stsf;
        }else{
            ssf = new ScreenSlideFragment();
            ssf.setPP(this.urlPP);
            return ssf;
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }
}
