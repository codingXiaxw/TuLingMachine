package com.example.codingboy.tulingdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    class MyAsyncTask extends AsyncTask<String,Void,Answer>
    {


        @Override
        protected Answer doInBackground(String... params)
        {

            HttpURLConnection connection=null;
            try {
                URL url = new URL(params[0]);
                connection= (HttpURLConnection) url.openConnection();
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuffer response=new StringBuffer();
                while ((line=reader.readLine())!=null)
                {
                    response.append(line);
                }

                Gson gson=new Gson();
                Answer answer=gson.fromJson(response.toString(),Answer.class);
                in.close();
                return answer;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Answer answer) {
            super.onPostExecute(answer);
            Data data2=new Data(answer.text,0);
            list.add(data2);
            adapter.notifyDataSetChanged();


        }
    }

    private boolean isExit=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitBy2Click() {
        if(isExit==false)
        {
            isExit=true;
            Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);
        }else {
            finish();
        }
    }
}

