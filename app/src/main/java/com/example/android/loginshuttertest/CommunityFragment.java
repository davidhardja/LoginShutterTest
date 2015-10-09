package com.example.android.loginshuttertest;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
public class CommunityFragment extends Fragment {
    private ArrayList<Shuttersong> communityList;
    private ProgressDialog pDialog;
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    private final String url = "http://b.sso.ng/api/";
    private SessionManager session;
    private CustomGridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getActivity());
        communityList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.community_fragment, container, false);
        new generateCommunity().execute();
        gridView = (CustomGridView) v.findViewById(R.id.grid_view_community);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println("PRINTESFRAGMENT");
            }
        });
        return v;
    }

    private class generateCommunity extends AsyncTask<Void,Void,Void> {
        JSONArray jsonArrayImage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
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
                gridView.setAdapter(new ImageAdapter(getActivity(), communityList));
            }
        }
    }
}
