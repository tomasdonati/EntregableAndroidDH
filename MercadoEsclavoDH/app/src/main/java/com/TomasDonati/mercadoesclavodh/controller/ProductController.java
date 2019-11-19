package com.TomasDonati.mercadoesclavodh.controller;

import com.TomasDonati.mercadoesclavodh.model.Product;
import com.TomasDonati.mercadoesclavodh.model.ProductDao;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;

import java.util.List;

public class ProductController {

    public void searchProductByText(String query, final ResultListener<List<Product>> viewListener){
        ProductDao productDao = new ProductDao();
        productDao.searchProductByText(query, new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                viewListener.finish(result);
            }
        });
    }
}
