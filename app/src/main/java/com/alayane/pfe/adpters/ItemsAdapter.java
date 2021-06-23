package com.alayane.pfe.adpters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.alayane.pfe.AppDB;
import com.alayane.pfe.ItemsActivity;
import com.alayane.pfe.OrderDAO;
import com.alayane.pfe.R;
import com.alayane.pfe.models.Items;
import com.alayane.pfe.models.Order;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

public class ItemsAdapter extends BaseAdapter {
    List<Items> itemsList;
    Context ctx;
    LayoutInflater inflater;
    public int count=0;
    public String ItemName;

    public ItemsAdapter(List<Items> itemsList, Context ctx) {
        this.itemsList = itemsList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Items items =itemsList.get(position);
        if (convertView==null){
            inflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.items_list, parent,false);
        }

        ItemsActivity act=(ItemsActivity)ctx;
        View toolbar=act.findViewById(R.id.toolbar);
        NotificationBadge badge= toolbar.findViewById(R.id.badge);
        LinearLayout ln=convertView.findViewById(R.id.CartGroupView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(
                        ctx,R.style.ButtomSheetDialogTheme
                );
                View bottomSheetView= LayoutInflater.from(ctx)
                        .inflate( R.layout.cart_view,ln);
                TextView tvCartItemName=bottomSheetView.findViewById(R.id.CartItemName);
                EditText etQte=bottomSheetView.findViewById(R.id.etQte);
                Button btnLess=bottomSheetView.findViewById(R.id.btn_less);
                Button btnMore=bottomSheetView.findViewById(R.id.btn_more);
                tvCartItemName.setText(items.getName());
                etQte.setText("0");

                btnLess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etQte.getText().toString().equals("0")){
                            return;
                        }else
                        {
                            int nbr=Integer.parseInt( etQte.getText().toString());
                            nbr--;
                            etQte.setText(String.valueOf(nbr));
                        }
                    }
                });
                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int nbr=Integer.parseInt( etQte.getText().toString());
                        nbr++;
                        etQte.setText(String.valueOf(nbr));
                    }
                });
                bottomSheetView.findViewById(R.id.btnAddToList).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ctx, "Added...", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                        try{
                        AppDB appDB= Room.databaseBuilder(ctx,
                                AppDB.class, "db")
                                .allowMainThreadQueries()
                                .build();
                            SharedPreferences preferences=ctx.getSharedPreferences("red", Context.MODE_PRIVATE);
                            int cust= preferences.getInt("CustomerId",0);
                            int tabl= preferences.getInt("TableId",0);

                        appDB.orderDAO().insertOrder(new Order(Integer.parseInt(etQte.getText().toString()),items.getImage()
                                ,items.getName(),items.getPrice(),items.getId(),cust,tabl));
                        badge.setNumber(appDB.orderDAO().getBadge());

                        }catch (Exception ex){
                            Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

        TextView tvName=convertView.findViewById(R.id.ItemName);
        TextView tvPrice=convertView.findViewById(R.id.ItemPrice);
        TextView tvQte=convertView.findViewById(R.id.ItemQte);
        TextView tvDes=convertView.findViewById(R.id.ItemDes);
        ImageView ivItem=convertView.findViewById(R.id.ItemImg);

        tvName.setText(items.getName());
        tvPrice.setText("$"+items.getPrice());
        tvDes.setText(items.getDes());
        tvQte.setText(items.getQte());
        Glide.with(ctx)
                .asBitmap()
                .load(Base64.decode(items.getImage().getBytes(),Base64.DEFAULT))
                .into(ivItem);


        return convertView;
    }
}
