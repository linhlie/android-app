package com.example.app.onlineshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.model.CartProduct;
import com.example.app.onlineshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imageView;
    TextView txtName, txtPrice, txtDes;
    Button buttonCart;
    Spinner spinner;

    int id = 0;
    String nameProDetail ="";
    int price = 0;
    String imgDetail ="";
    String desDetail ="";
    int idProType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Mapp();
        ActionToolbar();
        GetInfo();
        CatchEventSpinner();
        EventButton();
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

    private void EventButton() {
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.cartProducts.size()>0){
                    int temp = (Integer.parseInt(spinner.getSelectedItem().toString()));
                    boolean exits = false;
                    for (int i=0;i<MainActivity.cartProducts.size();i++){
                        if (MainActivity.cartProducts.get(i).getId()==id){
                            MainActivity.cartProducts.get(i).setTotalPro(MainActivity.cartProducts.get(i).getTotalPro()+temp);
                            if (MainActivity.cartProducts.get(i).getTotalPro()>=10){
                                MainActivity.cartProducts.get(i).setTotalPro(10);
                            }
                            MainActivity.cartProducts.get(i).setPrice(price * MainActivity.cartProducts.get(i).getTotalPro());
                            exits = true;
                        }
                    }
                    if (exits ==false){
                        int temp2 = (Integer.parseInt(spinner.getSelectedItem().toString()));
                        long newPrice = temp*price;
                        MainActivity.cartProducts.add(new CartProduct(id, nameProDetail, newPrice, imgDetail,temp2));
                    }
                } else {
                    int temp = (Integer.parseInt(spinner.getSelectedItem().toString()));
                    long newPrice = temp*price;
                    MainActivity.cartProducts.add(new CartProduct(id, nameProDetail, newPrice, imgDetail,temp));

                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] total = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,total);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfo() {


        Product product = (Product) getIntent().getSerializableExtra("InfoProduct");
//        Log.d(product.getName_product(),product.getName_product());
        id = product.getId();
        nameProDetail = product.getName_product();
        price = product.getPrice();
        imgDetail = product.getImage_product();
        desDetail = product.getDescription();
        idProType= product.getId_product();

        txtName.setMaxLines(1);
        txtName.setEllipsize(TextUtils.TruncateAt.END);
        txtName.setText(nameProDetail);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtPrice.setText("Giá : "+decimalFormat.format(price)+ "Đ");
        txtDes.setText(desDetail);
        Picasso.with(getApplicationContext()).load(imgDetail)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageView);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapp() {
        toolbar = (Toolbar)findViewById(R.id.toolbarDetails);
        imageView = (ImageView)findViewById(R.id.imgDetails);
        txtName = (TextView)findViewById(R.id.textViewNameProductDetails);
        txtPrice = (TextView)findViewById(R.id.textPriceDetail);
        txtDes = (TextView)findViewById(R.id.textDescriptionDetails);
        spinner = (Spinner)findViewById(R.id.spinnerDetails);
        buttonCart = (Button) findViewById(R.id.addCart);

    }
}