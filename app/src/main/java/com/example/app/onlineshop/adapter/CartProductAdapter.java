package com.example.app.onlineshop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.onlineshop.R;
import com.example.app.onlineshop.activities.CartActivity;
import com.example.app.onlineshop.activities.MainActivity;
import com.example.app.onlineshop.model.CartProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<CartProduct>cartProducts;

    public CartProductAdapter(Context context, ArrayList<CartProduct> cartProducts) {
        this.context = context;
        this.cartProducts = cartProducts;
    }

    @Override
    public int getCount() {
        return cartProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return cartProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        public TextView txtNameCart, txtPriceCart;
        public ImageView imageViewCart;
        public Button buttonMin, buttonValue, buttonPlus;


    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_cart_product, null);
            viewHolder.txtNameCart = (TextView)view.findViewById(R.id.textCartProduct);
            viewHolder.txtPriceCart = (TextView)view.findViewById(R.id.textViewPriceCart);
            viewHolder.imageViewCart = (ImageView)view.findViewById(R.id.imgCartProduct);
            viewHolder.buttonMin = (Button)view.findViewById(R.id.buttonMin);
            viewHolder.buttonValue = (Button)view.findViewById(R.id.buttonValues);
            viewHolder.buttonPlus = (Button)view.findViewById(R.id.buttonPlus);
            view.setTag(viewHolder);
        } else {
            viewHolder = new ViewHolder();
        }
        //đúng rồi mo t xem cai ham xoa
        CartProduct cartProduct = (CartProduct) getItem(position);
        viewHolder.txtNameCart.setMaxLines(2); // cái này aà sản pâẩm trong giỏaà// cai dong aày éo sai/ sai the nao dc, t nghĩ nó do cập nhật lại cái dr
        //data giỏ hàng nên lỗi
        //để mai t mang lên xem cho dễ t nói code thì m hiểu dễ sửa hơn//nhưng aà khó hêểu aãi
        viewHolder.txtNameCart.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtNameCart.setText(cartProduct.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceCart.setText(decimalFormat.format(cartProduct.getPrice()) +" Đ");
        Picasso.with(context).load(cartProduct.getImg())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageViewCart);
        viewHolder.buttonValue.setText(String.valueOf(cartProduct.getTotalPro()));

        int num = Integer.parseInt(viewHolder.buttonValue.getText().toString());
        if (num>=10){
            viewHolder.buttonPlus.setVisibility(View.INVISIBLE);
            viewHolder.buttonMin.setVisibility(View.VISIBLE);
        } else if (num<=1){
            viewHolder.buttonMin.setVisibility(View.INVISIBLE);
        } else if (num>=1){
            viewHolder.buttonPlus.setVisibility(View.VISIBLE);
            viewHolder.buttonMin.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numNew = Integer.parseInt(finalViewHolder.buttonValue.getText().toString())+1;
                int numCurrent = MainActivity.cartProducts.get(position).getTotalPro();
                long priceCurrent = MainActivity.cartProducts.get(position).getPrice();
                MainActivity.cartProducts.get(position).setTotalPro(numNew);
                long newPrice =(numNew * priceCurrent)/numCurrent;
                MainActivity.cartProducts.get(position).setPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPriceCart.setText(decimalFormat.format(newPrice) +" Đ");

                CartActivity.EventUltil();
                if (numNew>9){
                    finalViewHolder1.buttonPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder1.buttonMin.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValue.setText(String.valueOf(numNew));
                } else {
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMin.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValue.setText(String.valueOf(numNew));
                }

            }
        });
        viewHolder.buttonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numNew = Integer.parseInt(finalViewHolder.buttonValue.getText().toString())-1;
                int numCurrent = MainActivity.cartProducts.get(position).getTotalPro();
                long priceCurrent = MainActivity.cartProducts.get(position).getPrice();
                MainActivity.cartProducts.get(position).setTotalPro(numNew);
                long newPrice =(numNew * priceCurrent)/numCurrent;
                MainActivity.cartProducts.get(position).setPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPriceCart.setText(decimalFormat.format(newPrice) +" Đ");

                CartActivity.EventUltil();
                if (numNew<2){
                    finalViewHolder1.buttonMin.setVisibility(View.INVISIBLE);
                    finalViewHolder1.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValue.setText(String.valueOf(numNew));
                } else {
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMin.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValue.setText(String.valueOf(numNew));
                }
            }
        });
        return view;
    }
}
