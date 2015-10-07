package com.example.android.loginshuttertest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by bocist-8 on 07/10/15.
 */
public class PeopleAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> users;
    public PeopleAdapter(Context c, ArrayList<User> u){
        context = c;
        users = u;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_list_view_people, parent, false);
        RoundedImageView pro_pic = (RoundedImageView) convertView.findViewById(R.id.image_view_profile_pic);
        TextView username = (TextView) convertView.findViewById(R.id.text_view_name_people);
        TextView follow_status = (TextView) convertView.findViewById(R.id.text_view_follow_status);

        System.out.println("DACING"+users.get(position).getFollowing_status());
        if(users.get(position).getFollowing_status()==3){
            follow_status.setBackgroundResource(R.drawable.following_button_720);
            follow_status.setText("Following V");
            follow_status.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(users.get(position).isVerified()){
            ImageView verified = (ImageView) convertView.findViewById(R.id.image_view_certified);
            verified.setImageResource(R.drawable.certified_icon_720);
        }
        username.setText(users.get(position).getUsername());
        if(!users.get(position).getPicture_thumb_url().matches("default-mermaid.png")){
            UrlImageViewHelper.setUrlDrawable(pro_pic, users.get(position).getPicture_thumb_url());
        }

        pro_pic.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return convertView;
    }
}
