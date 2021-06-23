package com.alayane.pfe;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alayane.pfe.models.Order;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        EditText etCustomer=findViewById(R.id.etName);
        Button btnDone=findViewById(R.id.btnDone);

        GridView gv=findViewById(R.id.gvItems);
        Retrofit retrofit= new  Retrofit.Builder()
                .baseUrl("http://192.168.1.107:45455")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etCustomer.getText().toString().isEmpty()){
                    return;
                }else {

                    PfeAPI pfeAPI=retrofit.create(PfeAPI.class);
                    Call<String> repos=pfeAPI.postCustomer(etCustomer.getText().toString());
                    repos.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if( response.isSuccessful()){

                                SharedPreferences preferences=getSharedPreferences("red", Context.MODE_PRIVATE);
                                SharedPreferences.Editor customerID = preferences.edit().putInt("CustomerId", Integer.parseInt(response.body()));
                                customerID.commit();
                                Intent intent=new Intent(CustomerActivity.this,CategoriesActivity.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(CustomerActivity.this, "Something went wrong. please try again!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(CustomerActivity.this, "Something went wrong. please try again!", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }
}