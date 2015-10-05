package com.example.android.loginshuttertest;

/**
 * Created by bocist-8 on 29/09/15.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "ShutterSongLogin";
    private static final String EMAIL_NAME = "EmailName";
    private static final String TOKEN_NAME = "TokenName";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String email, String token) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        if(isLoggedIn==true){
            editor.putString(TOKEN_NAME, token);
            editor.putString(EMAIL_NAME,email);
        }else{
            editor.putString(TOKEN_NAME, null);
            editor.putString(EMAIL_NAME, null);
        }

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getToken(){
        return pref.getString(TOKEN_NAME, null);

    }
    public String getEmail(){
        return pref.getString(EMAIL_NAME, null);
    }

}
