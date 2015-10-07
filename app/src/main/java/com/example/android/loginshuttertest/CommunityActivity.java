package com.example.android.loginshuttertest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class CommunityActivity extends Activity{
    private SessionManager session;
    private CustomGridView gridView;
    private ListView listView;
    private ProgressDialog pDialog;
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    private final String url = "http://b.sso.ng/api/";
    private ArrayList<Shuttersong> communityList;
    private ArrayList<User> userResult;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_layout);
        session = new SessionManager(getApplicationContext());
        communityList = new ArrayList<Shuttersong>();
        userResult = new ArrayList<User>();
        searchView = (SearchView) findViewById(R.id.search_view_people);
        listView = (ListView) findViewById(R.id.list_view_people);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                //callSearch(newText);
//              }
                return false;
            }

            public void callSearch(String query) {
                new searchUser(query).execute();
            }

        });

        //new generateCommunity().execute();
        //gridView = (CustomGridView) findViewById(R.id.grid_view_community);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//
//            }
//        });

    }

    private class searchUser extends AsyncTask<Void,Void,Void> {
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
            pDialog.setCancelable(false);
            pDialog.show();
            userResult = new ArrayList<User>();
            listView.setAdapter(null);
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
                    for (int i = 0; i < 21; i++) {
                        JSONObject c = jsonArrayUser.getJSONObject(i);

                        String id = c.getString("id");
                        String username = c.getString("username");
                        String picture_thumb_url = c.getString("picture_thumb_url");
                        boolean verified = Boolean.getBoolean(c.getString("verified"));
                        boolean public_shuttersong = Boolean.getBoolean(c.getString("public_shuttersong"));
                        int following_status = -1;
                        boolean cek = c.has("following_status");
                        System.out.println("CEKER"+username + cek);
                        following_status =   (int)(Math.random()*4);

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
                listView.setAdapter(new PeopleAdapter(CommunityActivity.this, userResult));


            }
        }
    }

    private class generateCommunity extends AsyncTask<Void,Void,Void> {
        JSONArray jsonArrayImage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommunityActivity.this);
            pDialog.setMessage("Loading Community...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("access_token", session.getToken()));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));
            params.add(new BasicNameValuePair("until", "9073720461"));


            String jsonStr = sh.makeServiceCall(url+"community_shares", ServiceHandler.GET,params);
            Log.d("ResponseCom: ", "> " + jsonStr);



            if (jsonStr != null) {
                try {

                    jsonArrayImage = new JSONArray(jsonStr);
                    System.out.println("SIZE"+jsonArrayImage.length());
                    for (int i = 0; i < 21; i++) {
                        JSONObject c = jsonArrayImage.getJSONObject(i);

                        String file_url = c.getString("file_url");
                        String timeline_image_url = c.getString("timeline_image_url");
                        String audio_url = c.getString("audio_url");
                        String thumb_url = c.getString("thumb_url");
                        String share_url = c.getString("thumb_url");

                        String caption = null;
                        String uuid = c.getString("uuid");
                        boolean is_mine = Boolean.getBoolean(c.getString("is_mine"));
                        int favorite_count = 0;
                        int comment_count = 0;
                        String song_title = null;
                        String song_artist = null;
                        String song_album = null;
                        String created_at = c.getString("created_at");
                        String updated_at = c.getString("updated_at");

                        Shuttersong s = new Shuttersong(file_url,timeline_image_url,audio_url,thumb_url,share_url,
                                caption,uuid,is_mine,favorite_count,comment_count,song_title,song_artist,song_album,created_at,updated_at);
                        communityList.add(s);
                        communityList.add(s);
                        communityList.add(s);
                        communityList.add(s);
                        communityList.add(s);
                    }
                    System.out.println("BULET"+communityList.size());
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
                System.out.println("CEKSIZE" + communityList.size());

                //gridView.setAdapter(new ImageAdapter(CommunityActivity.this, communityList));
            }
        }
    }
}


