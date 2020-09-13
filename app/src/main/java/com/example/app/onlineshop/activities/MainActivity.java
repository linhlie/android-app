package com.example.app.onlineshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.onlineshop.R;
import com.example.app.onlineshop.adapter.ProductAdapter;
import com.example.app.onlineshop.adapter.ProductTypeAdapter;
import com.example.app.onlineshop.model.CartProduct;
import com.example.app.onlineshop.model.Product;
import com.example.app.onlineshop.model.ProductType;
import com.example.app.onlineshop.ultil.CheckConnection;
import com.example.app.onlineshop.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    ArrayList<ProductType>productTypes;
    ProductTypeAdapter productTypeAdapter;

    int id =0;
    String nameProType ="";
    String imgProType ="";
    ArrayList<Product> products;
    ProductAdapter productAdapter;
    public static ArrayList<CartProduct>cartProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapp();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActioViewFlipper();
            GetDataProType();
            GetNewProduct();
            CatchOnItemListView();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(),"Kiểm tra kết nối internet!");
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

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối internet!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MobilePhoneActivity.class);
                            intent.putExtra("idProType", productTypes.get(i).id);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối internet!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idProType", productTypes.get(i).id);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối internet!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối internet!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Không có kết nối internet!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetNewProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathProductNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    int ID = 0;
                    String namePro ="";
                    Integer price =0;
                    String imagePro ="";
                    String description ="";
                    int idPro =0;
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            namePro = jsonObject.getString("name_product");
                            price = jsonObject.getInt("price");
                            imagePro = jsonObject.getString("image_product");
                            description = jsonObject.getString("description");
                            idPro = jsonObject.getInt("id_product");
                            products.add(new Product(ID,namePro, imagePro, price, description, idPro));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataProType() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathProType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i =0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nameProType = jsonObject.getString("name_product_type");
                            imgProType = jsonObject.getString("image_type");
                            productTypes.add(new ProductType(id, nameProType, imgProType));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    productTypes.add(3, new ProductType(0, "Liên hệ", "https://chuakeo.com.vn/wp-content/uploads/2018/07/contact-us.jpg"));
                    productTypes.add(4, new ProductType(0, "Thông tin", "https://bucket.nhanh.vn/store/23282/art/article_1516341371_760.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActioViewFlipper() {
        ArrayList<String> adImage = new ArrayList<>();
        adImage.add("https://cdn.tgdd.vn/Products/Images/42/222596/oppo-reno4-302420-1024532.jpg");
        adImage.add("https://cdn.tgdd.vn/Products/Images/42/210654/iphone-11-pro-max-512gb-tgdd10.jpg");

        for (int i=0; i<adImage.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(adImage.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTransitionName("Trang chủ");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void Mapp(){
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarMain);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listViewMain);
        drawerLayout = findViewById(R.id.drawerlayout);
        productTypes = new ArrayList<>();
        productTypes.add(0, new ProductType(0,"Trang chính","https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png" ));
        productTypeAdapter = new ProductTypeAdapter(productTypes, getApplicationContext());
        listView.setAdapter(productTypeAdapter);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(productAdapter);
        if (cartProducts!=null){

        } else {
            cartProducts = new ArrayList<>();
        }
    }
}