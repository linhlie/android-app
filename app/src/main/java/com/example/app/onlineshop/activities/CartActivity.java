package com.example.app.onlineshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.adapter.CartProductAdapter;
import com.example.app.onlineshop.model.CartProduct;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listView;
    TextView textViewNoti;
    static TextView txtTotal;
    Button buttonPay, buttonContinuce;
    Toolbar toolbar;
    CartProductAdapter cartProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapp();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
    }

    private void CatchOnItemListView() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (MainActivity.cartProducts.size()<=0){
                            textViewNoti.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.cartProducts.remove(position);
                            cartProductAdapter.notifyDataSetChanged();
                            listView.setAdapter(cartProductAdapter);
                            EventUltil();
                            if (MainActivity.cartProducts.size()<=0){
                                textViewNoti.setVisibility(View.VISIBLE);
                            } else {
                                textViewNoti.setVisibility(View.INVISIBLE);
                                cartProductAdapter.notifyDataSetChanged();
                                EventUltil(); //giu de xoa
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartProductAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;

            }
        });
    }

    public static void EventUltil() {
        long totalP =0;
        for (int i =0; i<MainActivity.cartProducts.size();i++){
            totalP += MainActivity.cartProducts.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotal.setText(decimalFormat.format(totalP) +" Đ");
    }

    private void CheckData() {
        if (MainActivity.cartProducts.size()<=0){
            cartProductAdapter.notifyDataSetChanged();
            textViewNoti.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            cartProductAdapter.notifyDataSetChanged();
            textViewNoti.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
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
        listView = (ListView)findViewById(R.id.listViewCart);
        textViewNoti = (TextView)findViewById(R.id.textviewNoti);
        txtTotal = (TextView)findViewById(R.id.totalPrice);
        buttonPay = (Button)findViewById(R.id.buttonPayCart);
        buttonContinuce = (Button)findViewById(R.id.buttonContinuce);
        toolbar = (Toolbar)findViewById(R.id.toolbarCart);
        cartProductAdapter = new CartProductAdapter(CartActivity.this, MainActivity.cartProducts);
        listView.setAdapter(cartProductAdapter);
    }
}