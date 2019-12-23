package com.TomasDonati.mercadoesclavodh.model.dao;

import android.util.Log;

import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.model.pojo.ProductContainer;
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

    public void searchProductByTextInPages(String query, Integer offset, Integer limit, final ResultListener<ProductContainer> controllerListener){
        Call<ProductContainer> call = productService.searchProductByTextInPages(query, offset, limit);

        call.enqueue(new Callback<ProductContainer>() {
            @Override
            public void onResponse(Call<ProductContainer> call, Response<ProductContainer> response) {
                ProductContainer productContainer = response.body();
                controllerListener.finish(productContainer);
            }

            @Override
            public void onFailure(Call<ProductContainer> call, Throwable t) {
                Log.d("tag", "error");
            }
        });
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
                Log.d("tag", "error");
            }
        });
    }

    public void bringProductDescription(String productId, final ResultListener<Description> controllerListener){
        Call<List<Description>> call = productService.bringProductDesription(productId);

        call.enqueue(new Callback<List<Description>>() {
            @Override
            public void onResponse(Call<List<Description>> call, Response<List<Description>> response) {
                Description productDescription = response.body().get(0);
                controllerListener.finish(productDescription);

            }

            @Override
            public void onFailure(Call<List<Description>> call, Throwable t) {
                Log.d("ddd","dd");
            }
        });
    }

    public void bringProductById(String productId, final ResultListener<Product> controllerListener){
        Call<Product> call = productService.bringProductById(productId);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                controllerListener.finish(product);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }


}
