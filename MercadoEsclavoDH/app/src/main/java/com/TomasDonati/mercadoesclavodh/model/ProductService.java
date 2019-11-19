package com.TomasDonati.mercadoesclavodh.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/sites/MLA/search/")
    Call<ProductContainer> searchProductByText(@Query("q") String query);

}
