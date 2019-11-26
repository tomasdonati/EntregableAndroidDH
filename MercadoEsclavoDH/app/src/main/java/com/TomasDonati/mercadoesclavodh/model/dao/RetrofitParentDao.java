package com.TomasDonati.mercadoesclavodh.model.dao;

import com.TomasDonati.mercadoesclavodh.model.service.ProductService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitParentDao  {

    private Retrofit retrofit;
    protected ProductService productService;

    public RetrofitParentDao(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productService = retrofit.create(ProductService.class);
    }
}
