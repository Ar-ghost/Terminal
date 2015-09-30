package com.ar_ghost.terminal;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    Button btnShutdown;
    Button btnCancelSD;
    HttpClient httpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShutdown=(Button)findViewById(R.id.btnShutdown);
        btnCancelSD=(Button)findViewById(R.id.btnCancelSD);
        httpClient=new DefaultHttpClient();
        btnShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try

                        {
                            HttpPost httpPost = new HttpPost("http://172.18.49.1/terminal.php");
                            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                            pairs.add(new BasicNameValuePair("order", "shutdown"));
                            pairs.add(new BasicNameValuePair("passwd", "I_AM_ME"));
                            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
                            HttpResponse response = httpClient.execute(httpPost);
                            if (response.getStatusLine().getStatusCode() == 200){
                                String msg = EntityUtils.toString(response.getEntity());
                                Looper.prepare();
                                if (msg .equals("0")) {
                                    Toast.makeText(MainActivity.this, "关机成功！系统将在一分钟内关机！", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "出错！返回码："+msg, Toast.LENGTH_LONG).show();
                                }
                                Looper.loop();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        btnCancelSD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            HttpPost httpPost = new HttpPost("http://172.18.49.1/terminal.php");
                            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                            pairs.add(new BasicNameValuePair("order", "cancelshutdown"));
                            pairs.add(new BasicNameValuePair("passwd", "I_AM_ME"));
                            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
                            HttpResponse response = httpClient.execute(httpPost);
                            if (response.getStatusLine().getStatusCode() == 200) {
                                String msg = EntityUtils.toString(response.getEntity());
                                Looper.prepare();
                                if (msg .equals("0")) {
                                    Toast.makeText(MainActivity.this, "取消成功！", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "出错！返回码："+msg, Toast.LENGTH_LONG).show();
                                }
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });



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
