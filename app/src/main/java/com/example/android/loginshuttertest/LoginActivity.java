package com.example.android.loginshuttertest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends Activity {
    public String email;
    public String password;
    private String token;
    public Button buttonLogin;
    public ProgressDialog pDialog;
    public SessionManager session;

    //curl -X POST http://b.sso.ng/api/sign_in?email=asd%40gmail.com&password=12345678&device_type=android&app_version=1.0.0
    //curl -X POST http://192.168.0.40:3000/sign_in?email=asd@gmail.com\&password=12345678\&device_type=android\&app_version=1.0.0

    //curl -X GET http://b.sso.ng/api/shuttersongs?access_token=xuQRNg49pXduomiZqkB8\&device_type=iOS\&app_version=1.0.0
    //curl -X GET 192.168.0.40:3000/api/shuttersongs?access_token=GDVGqWdvJDBefhaL4gML\&device_type=iOS\&app_version=1.0.0

    private final String url = "http://b.sso.ng/api/sign_in";
    //private final String url = "http://192.168.0.40:3000/api/sign_in";
    private final String DEVICE_TYPE = "android";
    private final String VERSION_TYPE = "1.0.0";


    private static final String TAG_ID = "id";

    public String returnID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("EMAIL",session.getEmail());
            intent.putExtra("TOKEN",session.getToken());
            startActivity(intent);
            finish();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        buttonLogin = (Button) findViewById(R.id.btnLogin);
        session = new SessionManager(getApplicationContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ((EditText) findViewById(R.id.email)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();
                new checkLogin(email,password).execute();

            }
        });

    }

    private class checkLogin extends AsyncTask<Void,Void,Void>{
        String email;
        String password;
        String errorMsg;


        private checkLogin(String s, String p){
            email = s;
            password = p;
            errorMsg = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("device_type",DEVICE_TYPE));
            params.add(new BasicNameValuePair("app_version",VERSION_TYPE));

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,params);
            Log.d("ResponseLOGIN: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    if(!jsonStr.matches("error")){
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        returnID = jsonObj.getString(TAG_ID);
                        token = jsonObj.getString("access_token");
                        session.setLogin(true,email,token);
                    }else{
                        errorMsg = "Invalid Username or Password";
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
                if(session.isLoggedIn()){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("EMAIL",email);
                    intent.putExtra("TOKEN",token);
                    startActivity(intent);
                    finish();
                }else if(!errorMsg.matches("")){
                    Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                }
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
