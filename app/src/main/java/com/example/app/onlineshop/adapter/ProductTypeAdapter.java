package com.example.app.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.model.ProductType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductTypeAdapter extends BaseAdapter {
    ArrayList<ProductType> productTypes;
    Context context;

    public ProductTypeAdapter(ArrayList<ProductType> productTypes, Context context) {
        this.productTypes = productTypes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return productTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtNameProType;
        ImageView imgProType;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_list_view_product_type, null);
            viewHolder.txtNameProType = (TextView)convertView.findViewById(R.id.textViewProType);
            viewHolder.imgProType =(ImageView)convertView.findViewById(R.id.imageViewProType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        ProductType productType =  (ProductType) getItem(position);
        viewHolder.txtNameProType.setText(productType.getNameProductType());
        Picasso.with(context).load(productType.getImageType()).placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgProType);
        return convertView;
    }
}
