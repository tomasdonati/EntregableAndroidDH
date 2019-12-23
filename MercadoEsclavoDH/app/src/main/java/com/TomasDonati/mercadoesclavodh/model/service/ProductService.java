package com.TomasDonati.mercadoesclavodh.model.service;

import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.model.pojo.ProductContainer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/sites/MLA/search/")
    Call<ProductContainer> searchProductByTextInPages(@Query("q") String query, @Query("offset") Integer pageOffset, @Query("limit") Integer pageLimit);

    @GET("/sites/MLA/search/")
    Call<ProductContainer> searchProductByText(@Query("q") String query);

    @GET("items/{id}/descriptions/")
    Call<List<Description>> bringProductDesription(@Path("id") String productId);

    @GET("items/{id}")
    Call<Product> bringProductById(@Path("id") String productId);

}
