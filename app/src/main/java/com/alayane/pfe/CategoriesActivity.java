package com.alayane.pfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alayane.pfe.adpters.CategoriesAdapter;
import com.alayane.pfe.models.Categories;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


     /*   View toolbar=findViewById(R.id.toolbar);
        NotificationBadge badge= toolbar.findViewById(R.id.badge);
        AppDB appDB= Room.databaseBuilder(this,
                AppDB.class, "db")
                .allowMainThreadQueries()
                .build();
        if(appDB.orderDAO().getBadge()){

        badge.setNumber(0);
        }

      */
        View btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoriesActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

         TextView activityName=findViewById(R.id.activityName);
         activityName.setText("Categories");

        GridView gv=findViewById(R.id.gvCategories);
        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl("http://192.168.1.107:45455")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PfeAPI pfeAPI=retrofit.create(PfeAPI.class);
        Call<List<Categories>> repos=pfeAPI.getCategories();
        repos.enqueue(new Callback<List<Categories>>() {

            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if( response.isSuccessful()){
                    List<Categories> categories=response.body();
                    CategoriesAdapter adapter=new CategoriesAdapter(categories,CategoriesActivity.this);
                    gv.setAdapter(adapter);
                }
                Toast.makeText(CategoriesActivity.this,response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}