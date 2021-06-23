package com.alayane.pfe;


import com.alayane.pfe.models.Categories;
import com.alayane.pfe.models.Items;
import com.alayane.pfe.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PfeAPI {
    @GET("/categories/all")
    Call<List<Categories>> getCategories();

    @GET("/items/all/{category}")
    Call<List<Items>> getItems(@Path("category") String category);

    @POST("/customers/add/{customer}")
    Call<String> postCustomer(@Path("customer") String customer);

    @POST("/orders/add")
    Call<List<Order>> postOrder(@Body List<Order> orders);
}
