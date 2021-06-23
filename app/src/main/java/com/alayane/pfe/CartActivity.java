package com.alayane.pfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.alayane.pfe.adpters.CartAdapter;
import com.alayane.pfe.models.Categories;
import com.alayane.pfe.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        GridView gv=findViewById(R.id.gvCartItems);
        AppDB appDB= Room.databaseBuilder(this,
                AppDB.class, "db")
                .allowMainThreadQueries()
                .build();
        CartAdapter adp=new CartAdapter(this,appDB.orderDAO().getAll());
        gv.setAdapter(adp);

        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl("http://192.168.1.107:45455")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        Button btnOrder=findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PfeAPI pfeAPI=retrofit.create(PfeAPI.class);
                Call<List<Order>> repos=pfeAPI.postOrder(appDB.orderDAO().getAll());
                repos.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if( response.isSuccessful()){
                            Toast.makeText(CartActivity.this, "Your order will be prepared in few minutes", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(CartActivity.this, "Something went wrong. please try again!", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {

                    }
                });

                appDB.orderDAO().deleteAll();
                finish();
                startActivity(getIntent());
                Intent intent=new Intent(CartActivity.this,CategoriesActivity.class);
                startActivity(intent);
            }
        });


    }
}