package com.example.codingboy.tulingdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_chat;
    private EditText et_chat;
    private Button bt_send;
    private List<Data> list;
    private String address = "http://www.tuling123.com/openapi/api?key=cbfdd3e20541005fa06f85484e215455&info=";
    MsgAdapter adapter;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       initView();

        bt_send.setOnClickListener(this);

    }
    public void initView()
    {
        lv_chat = (ListView) findViewById(R.id.lv_chat);
        et_chat = (EditText) findViewById(R.id.et_chat);
        bt_send = (Button) findViewById(R.id.send);
        list = new ArrayList<Data>();
        adapter=new MsgAdapter(list,MainActivity.this);
        lv_chat.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        String content=et_chat.getText().toString();
        if (!content.equals(""))
        {
            Data data1 = new Data(content, 1);
            list.add(data1);
            adapter.notifyDataSetChanged();
            new MyAsyncTask().execute(address + content);
            et_chat.setText("");
        }

    }

    class MyAsyncTask extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... params)
        {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
                String line;
                StringBuffer response=new StringBuffer();
                while ((line=reader.readLine())!=null)
                {
                    response.append(line);
                }

                JSONObject jsonObject=new JSONObject(response.toString());
                result=jsonObject.getString("text");
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Data data2=new Data(s,0);
            list.add(data2);
            adapter.notifyDataSetChanged();


        }
    }



}

