package com.example.app.onlineshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.onlineshop.R;
import com.example.app.onlineshop.adapter.MobilePhoneAdapter;
import com.example.app.onlineshop.model.Product;
import com.example.app.onlineshop.ultil.CheckConnection;
import com.example.app.onlineshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobilePhoneActivity extends AppCompatActivity {

    Toolbar toolbarPhone;
    ListView listView;
    MobilePhoneAdapter mobilePhoneAdapter;
    ArrayList<Product>products;
    int idPhone = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    myHanler myHanler;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_phone);
        Mapp();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIdProType();
            Actiontoolbar();
            GetData(page);
            LoadMoreData();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Không có kết nối internet");
            finish();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class );
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
                intent.putExtra("InfoProduct", products.get(i));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading ==false && limitData ==false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String path = Server.pathProductPhone + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String namePhone ="";
                int price=0;
                String imgPhone ="";
                String Des = "";
                int idProductType=0;
                if (response !=null && response.length()!=2){
                    listView.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i< response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            namePhone= jsonObject.getString("name_product");
                            imgPhone= jsonObject.getString("image_product");
                            Des= jsonObject.getString("description");
                            price= jsonObject.getInt("price");
                            idProductType= jsonObject.getInt("id_product");

                            products.add(new Product(id, namePhone, imgPhone, price, Des, idProductType));
                            mobilePhoneAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    listView.removeFooterView(footerView);CheckConnection.showToast_Short(getApplicationContext(),"Hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idProduct", String.valueOf(idPhone));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIdProType() {
        idPhone = getIntent().getIntExtra("idProType", -1);
        Log.d("Giá trị loại sản phẩm ", idPhone+ "");
    }

    private void Mapp() {
        toolbarPhone = findViewById(R.id.toolbarPhone);
        listView = findViewById(R.id.listViewPhone);
        products = new ArrayList<>();
        mobilePhoneAdapter = new MobilePhoneAdapter(getApplicationContext(),products);
        listView.setAdapter(mobilePhoneAdapter);
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        myHanler = new myHanler();
    }

    public class myHanler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading =false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myHanler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHanler.obtainMessage(1);
            myHanler.sendMessage(message);
            super.run();
        }
    }
}