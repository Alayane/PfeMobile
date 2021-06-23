package com.alayane.pfe.adpters;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alayane.pfe.ItemsActivity;
import com.alayane.pfe.R;
import com.alayane.pfe.models.Categories;
import com.bumptech.glide.Glide;


import java.util.List;

public class CategoriesAdapter extends BaseAdapter {
    List<Categories> categoriesList;
    Context ctx;
    LayoutInflater inflater;

    public CategoriesAdapter(List<Categories> categoriesList, Context ctx) {
        this.categoriesList = categoriesList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoriesList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Categories categories =categoriesList.get(position);
        if (convertView==null){
            inflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.categories_list, parent,false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, ItemsActivity.class);
                intent.putExtra("CateName",categories.getName());
                ctx.startActivity(intent);
            }
        });

        TextView tvName=convertView.findViewById(R.id.tvCatName);
        ImageView ivCat =convertView.findViewById(R.id.ivCat);

        tvName.setText(categories.getName());
        Glide.with(ctx)
                .asBitmap()
                .load(Base64.decode(categories.getImage().getBytes(),Base64.DEFAULT))
                .into(ivCat);


        return convertView;
    }
}
