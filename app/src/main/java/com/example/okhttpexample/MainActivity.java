package com.example.okhttpexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String url = "http://103.10.85.131:1126/index.php/Order/price?scId=1";
    JSONObject json;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data();
        }

        private void data() {
            // TODO 自动生成的方法存根
            try {
                Log.i("log", "==" + json.getJSONArray("price"));//  value
                JSONArray jsonArray = json.getJSONArray("price");
                for (int j = 0; j < jsonArray.length(); j++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    JSONObject jsonobject = jsonArray.getJSONObject(j);
                    map.put("id", jsonobject.getString("id"));
                    list.add(map);
                }
                Log.i("log", "list==" + list);
            } catch (JSONException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    };

    private void initview() {
        // TODO 自动生成的方法存根
        OkHttpClient mok = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mok.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                // TODO 自动生成的方法存根
                try {
                    // Log.i("log", "数据=="+response.body().string());
                    json = new JSONObject(response.body().string());
                    handler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                // TODO 自动生成的方法存根
                Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
