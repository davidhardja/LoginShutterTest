package com.example.android.loginshuttertest;

/**
 * Created by bocist-8 on 01/10/15.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.widget.TextView;


/**
 * Created by bocist-8 on 30/09/15.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private final String noComment = "No Comment";
    public ArrayList<Comment> commentList;

    public CommentAdapter(Context c, ArrayList<Comment> commentList){
        mContext= c;
        this.commentList = commentList;
    }

    public int getCount(){
        return commentList.size();
    }

    public Object getItem(int position){
        return commentList.get(position).getComment();
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView,ViewGroup parent){
        TextView textView = new TextView(mContext);
        if(!commentList.isEmpty()){
            textView.setTextSize(16);
            textView.setText(commentList.get(position).getComment());
        }
        return textView;
    }
}

