package com.example.app.onlineshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.activities.DetailProductActivity;
import com.example.app.onlineshop.model.Product;
import com.example.app.onlineshop.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_product_new, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = products.get(position);
        holder.nameProduct.setText(product.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        holder.price.setText("Giá : "+decimalFormat.format(product.getPrice())+"Đ");
        Picasso.with(context).load(product.image_product)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imageProduct);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageProduct;
        public TextView nameProduct, price;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct =(ImageView) itemView.findViewById(R.id.imgProduct);
            price = (TextView)itemView.findViewById(R.id.textPrice);
            nameProduct = (TextView)itemView.findViewById(R.id.textNamePro);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("InfoProduct", products.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
