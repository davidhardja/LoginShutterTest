package com.example.android.loginshuttertest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bocist-8 on 06/10/15.
 */
public class CommunityActivity extends Activity {
    private SessionManager session;
//    private ListView listView;
    private ProgressDialog pDialog;
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    private final String url = "http://b.sso.ng/api/";
    private ArrayList<User> userResult;
    private FragmentManager fm;
    private FragmentTransaction ft;
    public SearchFragment searchFragment;
    public CommunityFragment communityFragment;
    public boolean cek = false;


    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_layout);
        session = new SessionManager(getApplicationContext());
        //communityList = new ArrayList<Shuttersong>();
        userResult = new ArrayList<User>();
        searchView = (SearchView) findViewById(R.id.search_view_people);
        //listView = (ListView) findViewById(R.id.list_view_people);

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        CommunityFragment communityFragment = new CommunityFragment();
        searchFragment = new SearchFragment();
        ft.add(R.id.fragment_layout, communityFragment);
        ft.commit();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //ft.replace(R.id.fragment_layout, searchFragment);

                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(cek == false){
                    cek = true;
                    FragmentManager fm2 = getFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    ft2.add(R.id.fragment_layout, searchFragment);
                    ft2.commit();
                }

//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
//
//              }
                return false;
            }

            public void callSearch(String query) {
                new searchUser(query).execute();
            }
        });
    }

    public class searchUser extends AsyncTask<Void,Void,Void> {
        JSONArray jsonArrayUser;
        String search;

        public searchUser(String s){
            search = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommunityActivity.this);
            pDialog.setMessage("Search...");
            pDialog.setCancelable(true);
            pDialog.show();
            userResult = new ArrayList<User>();
            searchFragment.setAdapter(userResult);
            //listView.setAdapter(null);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("access_token", session.getToken()));
            params.add(new BasicNameValuePair("device_type", DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));
            params.add(new BasicNameValuePair("keyword", search));


            String jsonStr = sh.makeServiceCall(url+"users/search", ServiceHandler.GET,params);
            Log.d("ResponseSearch: ", "> " + jsonStr);


            if (jsonStr != null) {
                try {

                    jsonArrayUser = new JSONArray(jsonStr);
                    System.out.println("SIZE"+jsonArrayUser.length());
                    for (int i = 0; i < jsonArrayUser.length(); i++) {
                        JSONObject c = jsonArrayUser.getJSONObject(i);

                        String id = c.getString("id");
                        String username = c.getString("username");
                        String picture_thumb_url = c.getString("picture_thumb_url");
                        boolean verified = Boolean.getBoolean(c.getString("verified"));
                        boolean public_shuttersong = Boolean.getBoolean(c.getString("public_shuttersong"));
                        int following_status = -1;
                        boolean cek = c.has("following_status");

                        if(Boolean.compare(cek,true)==0){
                            following_status = Integer.getInteger(c.getString("following_status"));
                        }

//                        if(cek){
//                            System.out.println("asd");
//                            following_status = Integer.getInteger(c.getString("following_status"));
//
//                        }

//                        System.out.println("CEKER"+username + cek);
//                        following_status =   (int)(Math.random()*4);

                        User u = new User(id,username,picture_thumb_url,verified,public_shuttersong,following_status);
                        userResult.add(u);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                searchFragment.setAdapter(userResult);
                //listView.setAdapter(new PeopleAdapter(CommunityActivity.this, userResult));
            }
        }
    }

}


