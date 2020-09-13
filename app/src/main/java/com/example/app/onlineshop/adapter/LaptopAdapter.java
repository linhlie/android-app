package com.example.app.onlineshop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> products;

    public LaptopAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textLaptop, priceLaptop, txtDescriptionLaptop;
        public ImageView imgLaptop;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_mobile_laptop,null);
            viewHolder.textLaptop = view.findViewById(R.id.textViewLaptop);
            viewHolder.priceLaptop = view.findViewById(R.id.priceLaptop);
            viewHolder.txtDescriptionLaptop = view.findViewById(R.id.descriptionLaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.imgLaptop);

            view.setTag(viewHolder);

        } else {
            viewHolder = (LaptopAdapter.ViewHolder) view.getTag();
        }
        Product product=(Product) getItem(position);
        viewHolder.textLaptop.setMaxLines(1);
        viewHolder.textLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textLaptop.setText(product.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.priceLaptop.setText("Giá : "+ decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtDescriptionLaptop.setMaxLines(2);
        viewHolder.txtDescriptionLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDescriptionLaptop.setText(product.getDescription());
        Picasso.with(context).load(product.getImage_product()).placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgLaptop);
        return view;
    }
}
