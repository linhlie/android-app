package com.example.app.onlineshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.onlineshop.R;
import com.example.app.onlineshop.ultil.CheckConnection;
import com.example.app.onlineshop.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoCustomerActivity extends AppCompatActivity {

    EditText editTextCusName, editTextCusPhone, editTextCusEmail;
    Button buttonConfirm, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_customer);
        Mapp();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EvenButton();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Không có kết nối!");
        }
    }

    private void EvenButton() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = editTextCusName.getText().toString().trim();
                final String phone1 = editTextCusPhone.getText().toString().trim();
                final String email1 = editTextCusEmail.getText().toString().trim();
                if (name1.length()>0&&phone1.length()>0&&email1.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathOrder, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String resp) {
                            Log.d("Ma don hang", resp);
                            if(Integer.parseInt(resp)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.pathBill, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.cartProducts.clear();
                                            CheckConnection.showToast_Short(getApplicationContext(), "Thanh toán thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        } else {
                                            CheckConnection.showToast_Short(getApplicationContext(),"Dữ liệu bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0;i<MainActivity.cartProducts.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("idOrder", resp);
                                                jsonObject.put("price", MainActivity.cartProducts.get(i).getPrice());
                                                jsonObject.put("amount", MainActivity.cartProducts.get(i).getTotalPro());
                                                jsonObject.put("namePro", MainActivity.cartProducts.get(i).getName());
                                                jsonObject.put("idPro", MainActivity.cartProducts.get(i).getId());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);

                                        }
                                        HashMap<String, String>hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("name_customer", name1);
                            hashMap.put("phone", phone1);
                            hashMap.put("email", email1);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(),"Kiểm tra dữ liệu nhập vào!");
                }
            }
        });
    }

    private void Mapp() {
        editTextCusName = (EditText)findViewById(R.id.editNameCus);
        editTextCusPhone = (EditText)findViewById(R.id.editPhoneCus);
        editTextCusEmail = (EditText)findViewById(R.id.editEmailCus);
        buttonConfirm = (Button)findViewById(R.id.confirm);
        buttonBack = (Button)findViewById(R.id.back);
    }
}