package com.alayane.pfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alayane.pfe.adpters.ItemsAdapter;
import com.alayane.pfe.models.Items;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

     /*   View toolbar=findViewById(R.id.toolbar);
        NotificationBadge badge= toolbar.findViewById(R.id.badge);
        AppDB appDB= Room.databaseBuilder(this,
                AppDB.class, "db")
                .allowMainThreadQueries()
                .build();
        badge.setNumber(appDB.orderDAO().getBadge());


      */
        View btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ItemsActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        TextView activityName=findViewById(R.id.activityName);
        activityName.setText("Items");

        Button btnAddToList=findViewById(R.id.btnAddToList);

        String CateName=getIntent().getStringExtra("CateName");
        GridView gv=findViewById(R.id.gvItems);
        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl("http://192.168.1.107:45455")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PfeAPI pfeAPI=retrofit.create(PfeAPI.class);
        Call<List<Items>> repos=pfeAPI.getItems(CateName);
        repos.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if( response.isSuccessful()){
                    List<Items> items=response.body();
                    ItemsAdapter adapter=new ItemsAdapter(items,ItemsActivity.this);
                    gv.setAdapter(adapter);
                }
                Toast.makeText(ItemsActivity.this,response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                Toast.makeText(ItemsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}