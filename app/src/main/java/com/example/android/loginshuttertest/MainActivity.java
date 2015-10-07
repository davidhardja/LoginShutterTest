package com.example.android.loginshuttertest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private SessionManager session;
    private TextView email_text_view;
    private Button btnLogout;
    private Button btnCommunity;
    private String ACCESS_TOKEN;
    private final String url = "http://b.sso.ng/api/";
    //private final String url = "http://cakestaging-env.elasticbeanstalk.com/api/";
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    public ProgressDialog pDialog;
    public JSONArray jsonArrayImage = null;
    public ViewPager mPager;
    public String urlProfilPicture="";
    private PagerAdapter mPagerAdapter;
    public static ArrayList<Shuttersong> shuttersongsList;

    public CustomGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnCommunity =(Button) findViewById(R.id.btnCommunity);
        shuttersongsList = new ArrayList<Shuttersong>();

        if(!session.isLoggedIn()){
            logoutUser();
        }

        Intent i = getIntent();
        String email = i.getStringExtra("EMAIL");
        ACCESS_TOKEN = i.getStringExtra("TOKEN");
        urlProfilPicture = session.getProfilUrl();
        email_text_view = (TextView) findViewById(R.id.name);
        email_text_view.setText(email);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlideAdapter(getSupportFragmentManager(),urlProfilPicture);


        mPager.setAdapter(mPagerAdapter);
        new generateImage().execute();
        gridView = (CustomGridView) findViewById(R.id.image_grid_view);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                String uuid = shuttersongsList.get(position).getUuid();
                i.putExtra("uuid", uuid);
                i.putExtra("token", ACCESS_TOKEN);
                i.putExtra("id", position);
                startActivity(i);
            }
        });


        //gridView.invalidateViews();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logoutUser();
            }

        });

        btnCommunity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToCommunity();
            }

        });
    }

//    public void inputAdapter(View view){
//        gridView.setAdapter(new ImageAdapter(this, shuttersongsList));
//        gridView.invalidateViews();
//    }

    public void goToCommunity(){
        Intent intent = new Intent(MainActivity.this,CommunityActivity.class);
        startActivity(intent);
        finish();
    }

    public void logoutUser(){
        session.setLogin(false,null,null,null);
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private class generateImage extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));

            String jsonStr = sh.makeServiceCall(url+"shuttersongs", ServiceHandler.GET,params);
            Log.d("Response: ", "> " + jsonStr);


            if (jsonStr != null) {
                System.out.println("TINGTONG");
                try {
                    jsonArrayImage = new JSONArray(jsonStr);
                    for (int i = 0; i < 21; i++) {
                        JSONObject c = jsonArrayImage.getJSONObject(i);

                        String file_url = c.getString("file_url");
                        String timeline_image_url = c.getString("timeline_image_url");
                        String audio_url = c.getString("audio_url");
                        String thumb_url = c.getString("thumb_url");
                        String share_url = c.getString("thumb_url");

//                        String file_url = "http://192.168.0.40:3000"+c.getString("file_url");
//                        String timeline_image_url = c.getString("timeline_image_url");
//                        String audio_url = "http://192.168.0.40:3000"+c.getString("audio_url");
//                        String thumb_url = "http://192.168.0.40:3000"+c.getString("thumb_url");
//                        String share_url = "http://192.168.0.40:3000"+c.getString("share_url");
                        //System.out.println("THUMBURL"+thumb_url);
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
                        shuttersongsList.add(s);
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
                gridView.setAdapter(new ImageAdapter(MainActivity.this, shuttersongsList));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}



