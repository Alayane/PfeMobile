package com.alayane.pfe.adpters;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.room.Room;

import com.alayane.pfe.AppDB;
import com.alayane.pfe.BuildConfig;
import com.alayane.pfe.CartActivity;
import com.alayane.pfe.CategoriesActivity;
import com.alayane.pfe.ItemsActivity;
import com.alayane.pfe.R;
import com.alayane.pfe.models.Categories;
import com.alayane.pfe.models.Order;
import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;


public class CartAdapter  extends BaseAdapter {
    Context ctx;
    LayoutInflater inflater;
    List<Order> orderList;

    public CartAdapter(Context ctx, List<Order> orderList) {
        this.ctx = ctx;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order =orderList.get(position);
        if (convertView==null){
            inflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_list_cart, parent,false);
        }

        CartActivity c=(CartActivity) ctx;
        ImageButton btnDelete=convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDB appDB= Room.databaseBuilder(ctx,
                        AppDB.class, "db")
                        .allowMainThreadQueries()
                        .build();
                appDB.orderDAO().deleteOrder(order.Id);
                c.finish();
                c.startActivity(c.getIntent());
            }
        });


        TextView tvCartName=convertView.findViewById(R.id.CartItemName);
        TextView tvCartPrice=convertView.findViewById(R.id.CartItemPrice);
        TextView tvCartQte=convertView.findViewById(R.id.CArtItemQte);
        ImageView ivCatImage =convertView.findViewById(R.id.CartItemImg);

        tvCartName.setText(order.Name);
        tvCartPrice.setText("$"+order.Price);
        tvCartQte.setText(String.valueOf( order.qte));
        Glide.with(ctx)
                .asBitmap()
                .load(Base64.decode(order.Image.getBytes(),Base64.DEFAULT))
                .into(ivCatImage);


        return convertView;
    }
}
