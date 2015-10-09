package com.example.android.loginshuttertest;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bocist-8 on 08/10/15.
 */
public class SearchFragment extends Fragment {
    private SessionManager session;
    private ListView listView;
    private ProgressDialog pDialog;
    //private ArrayList<User> userResult;
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    private final String url = "http://b.sso.ng/api/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getActivity());
        //userResult = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        listView = (ListView) v.findViewById(R.id.list_view_people);
        return v;
    }

    public void setAdapter(ArrayList<User> userResult){
        if(userResult.isEmpty()){
            listView.setAdapter(null);
        }else{
            System.out.println("CEKNULL1"+userResult);
            System.out.println("CEKNULL2"+getActivity());
            listView.setAdapter(new PeopleAdapter(getActivity(), userResult));
        }

    }
}
