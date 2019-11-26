package com.TomasDonati.mercadoesclavodh.model.dao;

import com.TomasDonati.mercadoesclavodh.model.poho.Product;
import com.TomasDonati.mercadoesclavodh.model.poho.ProductContainer;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDao extends RetrofitParentDao {

    public static final String BASE_URL = "https://api.mercadolibre.com/";
    public static final String API_KEY = "";

    public ProductDao() {
        super(BASE_URL);
    }

    public void searchProductByText(String query, final ResultListener<List<Product>> controllerListener){
        Call<ProductContainer> call = productService.searchProductByText(query);

        call.enqueue(new Callback<ProductContainer>() {
            @Override
            public void onResponse(Call<ProductContainer> call, Response<ProductContainer> response) {
                ProductContainer productContainer = response.body();
                controllerListener.finish(productContainer.getProductList());
            }

            @Override
            public void onFailure(Call<ProductContainer> call, Throwable t) {

            }
        });

    }


}
