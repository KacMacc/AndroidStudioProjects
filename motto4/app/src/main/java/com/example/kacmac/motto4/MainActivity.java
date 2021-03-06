package com.example.kacmac.motto4;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        data_list = new ArrayList<>();
        load_data_from_server("mott");

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this, data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()) {
                    load_data_from_server(data_list.get(data_list.size()-1).getMott());
                }

            }
        });

    }


    private void load_data_from_server(String mott) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override

            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.0.14/Android/cos5.php")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject OBJECT = array.getJSONObject(i);

                        MyData data = new MyData(OBJECT.getString("mott"));

                        data_list.add(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {

                    System.out.println("End of content");

                }
                return null;
            }

            @Override

            protected void onPostExecute(Void aVoid) {

                adapter.notifyDataSetChanged();
            }


        };


        task.execute();

    }
    }















