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

public class MobilePhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product>products;

    public MobilePhoneAdapter(Context context, ArrayList<Product> products) {
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
        public TextView textPhone, pricePhone, txtDescriptionPhone;
        public ImageView imgPhone;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_mobile_phone,null);
            viewHolder.textPhone = view.findViewById(R.id.textViewPhone);
            viewHolder.pricePhone = view.findViewById(R.id.pricePhone);
            viewHolder.txtDescriptionPhone = view.findViewById(R.id.descriptionPhone);
            viewHolder.imgPhone = view.findViewById(R.id.imgPhone);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product=(Product) getItem(position);
        viewHolder.textPhone.setMaxLines(1);
        viewHolder.textPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textPhone.setText(product.getName_product());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.pricePhone.setText("Giá : "+ decimalFormat.format(product.getPrice())+" Đ");
        viewHolder.txtDescriptionPhone.setMaxLines(2);
        viewHolder.txtDescriptionPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDescriptionPhone.setText(product.getDescription());
        Picasso.with(context).load(product.getImage_product()).placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgPhone);
        return view;
    }
}
