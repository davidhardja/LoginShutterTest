package com.example.android.loginshuttertest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DetailActivity extends AppCompatActivity {

    private String uuid;
    private ProgressDialog pDialog;

    private String ACCESS_TOKEN;
    private final String url = "http://b.sso.ng/api/shuttersongs";
    //private final String url = "http://cakestaging-env.elasticbeanstalk.com/api/shuttersongs";
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";
    private JSONArray jsonArrayText;
    private Button addCommentButton;
    private EditText addCommentEditText;
    private ArrayList<Comment> commentList;
    private CommentAdapter ca;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        commentList = new ArrayList<Comment>();
        Intent i =getIntent();
        int position = i.getExtras().getInt("id");
        uuid = i.getStringExtra("uuid");
        ACCESS_TOKEN = i.getStringExtra("token");
        ImageAdapter imageAdapter = new ImageAdapter(this,MainActivity.shuttersongsList);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        UrlImageViewHelper.setUrlDrawable(imageView, imageAdapter.gallery.get(position).getFile_url());

        new generateComment().execute();

        lv = (ListView) findViewById(R.id.comment_list_view);
        ca = new CommentAdapter(DetailActivity.this, commentList);
        lv.setAdapter(ca);
        lv.invalidateViews();

        addCommentButton = (Button) findViewById(R.id.add_comment_button);
        addCommentEditText = (EditText) findViewById(R.id.add_comment_edit_text);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String addComment = addCommentEditText.getText().toString();
                new writeComment(addComment).execute();
                refreshView();
            }

        });

    }

    public void refreshView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommentAdapter cx = new CommentAdapter(DetailActivity.this, commentList);
                cx.notifyDataSetChanged();
                lv.setAdapter(cx);
            }
        });
    }


    private class writeComment extends AsyncTask<Void,Void,Void> {

        private String comment;

        protected writeComment(String c){
            comment = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailActivity.this);
            pDialog.setMessage("Loading Comment...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String urlUuid = url+"/"+uuid+"/comments";
            //http://192.168.0.40:3000/api/shuttersongs/an-unique-universal-identifier-ssng-1             /commentsaccess_token=dummy-access-token  &device_type=iOS    &app_version=1.0.0&comment[full_text]=My+comment.
            //curl -X POST http://192.168.0.40:3000/api/shuttersongs/an-unique-universal-identifier-ssng-1/commentsaccess_token=GDVGqWdvJDBefhaL4gML&device_type=android&app_version=1.0.0&comment%5Bfull_text%5D=vfyjb



            params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));
            params.add(new BasicNameValuePair("comment[full_text]", comment));

            String jsonStr = sh.makeServiceCall(urlUuid, ServiceHandler.POST,params);
            Log.d("ResponseXXX: ", "> " + jsonStr);

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


    private class generateComment extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailActivity.this);
            pDialog.setMessage("Loading Comment...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

//            String since = "2013-09-23T23:03:11Z";
//            String until = "2015-12-30T23:03:11Z";
//
//            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//            String s = sdf.format(since);
//            String u = sdf.format(until);
//            int output_since = Integer.valueOf(s);
//            int output_until = Integer.valueOf(u);

            String listURL = url+"/"+ uuid+ "/comments";

            params.add(new BasicNameValuePair("access_token", ACCESS_TOKEN));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version", VERSION_TYPE));
            params.add(new BasicNameValuePair("shuttersong_id", uuid));
            params.add(new BasicNameValuePair("since", "1043420461"));
            params.add(new BasicNameValuePair("until", "3443420461"));

            String jsonStr = sh.makeServiceCall(listURL, ServiceHandler.GET,params);
            Log.d("ResponseDOD: ", "> " + jsonStr);

            if (jsonStr != null) {
                System.out.println("DOD1");
                try {
                    System.out.println("DOD2");
                    jsonArrayText = new JSONArray(jsonStr);
                    System.out.println("DOD3"+ jsonArrayText.length());
                    for (int i = 0; i < jsonArrayText.length(); i++) {
                        System.out.println("DOD4");
                        JSONObject c = jsonArrayText.getJSONObject(i);

                        JSONObject user = c.getJSONObject("user");
                        String username = user.getString("username");
                        String comment = c.getString("full_text");
                        Comment comm = new Comment(username,comment);
                        commentList.add(comm);
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
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
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
