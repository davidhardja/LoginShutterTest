package com.example.android.loginshuttertest;

/**
 * Created by bocist-8 on 28/09/15.
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceHandler {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public ServiceHandler(){

    }

    public String makeServiceCall(String url, int method){
        return this.makeServiceCall(url,method,null);
    }

    public String makeServiceCall(String url, int method,List<NameValuePair> params){

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            if(method == POST){
                HttpPost httpPost = new HttpPost(url);
                if(params!=null){
                    String paramString = URLEncodedUtils.format(params,"utf-8");
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                    System.out.println("YYYY" + url+paramString);
                }

                httpResponse = httpClient.execute(httpPost);
            }else if(method==GET){
                if(params!=null){
                    String paramString = URLEncodedUtils.format(params,"utf-8");
                    url += "?" + paramString;
                    System.out.println("XXXX"+url);
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
            System.out.println("RESPONSE"+response);

            if(response.matches("Invalid email or password")){
                response = "error";
            }

        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
