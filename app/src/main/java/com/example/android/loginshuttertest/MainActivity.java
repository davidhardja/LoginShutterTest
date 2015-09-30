package com.example.android.loginshuttertest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private SessionManager session;
    private TextView email_text_view;
    private Button btnLogout;
    private String ACCESS_TOKEN;
    private final String url = "http://b.sso.ng/api/shuttersongs";
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    public ProgressDialog pDialog;
    public JSONArray jsonArrayImage = null;
    public ArrayList<String> galleryUrl;
    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        btnLogout = (Button) findViewById(R.id.btnLogout);
        galleryUrl = new ArrayList<String>();

        if(!session.isLoggedIn()){
            logoutUser();
        }

        Intent i = getIntent();
        String email = i.getStringExtra("EMAIL");
        ACCESS_TOKEN = i.getStringExtra("TOKEN");
        email_text_view = (TextView) findViewById(R.id.name);
        email_text_view.setText(email);

        new generateImage().execute();
        gridView = (GridView) findViewById(R.id.image_grid_view);
        System.out.println("MABU" + galleryUrl.isEmpty());

//        if(!galleryUrl.isEmpty()){
//            gridView = (GridView) findViewById(R.id.image_grid_view);
//            gridView.setAdapter(new ImageAdapter(this, galleryUrl));
//        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logoutUser();
            }

        });
    }

    public void inputAdapter(View view){
        gridView.setAdapter(new ImageAdapter(this, galleryUrl));
        gridView.invalidateViews();
    }

    public void logoutUser(){
        session.setLogin(false);
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
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET,params);
            Log.d("Response: ", "> " + jsonStr);


            if (jsonStr != null) {

                try {
                    jsonArrayImage = new JSONArray(jsonStr);
                    for (int i = 0; i < 16; i++)
                    {
                        JSONObject c = jsonArrayImage.getJSONObject(i);
                        String share_url = c.getString("thumb_url");
                        System.out.println("SHAREURL"+share_url);
                        galleryUrl.add(share_url);
                        System.out.println("SHAREURL"+share_url);

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
