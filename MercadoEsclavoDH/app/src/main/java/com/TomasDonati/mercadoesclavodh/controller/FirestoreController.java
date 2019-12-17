package com.TomasDonati.mercadoesclavodh.controller;

import com.TomasDonati.mercadoesclavodh.model.dao.FirestoreDao;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;

import java.util.List;

public class FirestoreController {

    private FirestoreDao firestoreDao;

    public FirestoreController(){
        firestoreDao = new FirestoreDao();
    }

    public void addProductToFav(Product product){
        firestoreDao.addProductToFav(product);
    }

    public void bringFavouriteProducts(final ResultListener<List<Product>> viewListener){
        firestoreDao.bringFavouriteProducts(new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                viewListener.finish(result);
            }
        });
    }
}
