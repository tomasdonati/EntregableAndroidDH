package com.TomasDonati.mercadoesclavodh.controller;

import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.model.dao.ProductDao;
import com.TomasDonati.mercadoesclavodh.model.pojo.ProductContainer;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;

import java.util.List;

public class ProductController {

    private Integer pageOffset = 0;
    private static final Integer PAGE_SIZE = 10;
    private Boolean areThereMoreProducts = true;

    public void searchProductByTextInPages(String query, final ResultListener<ProductContainer> viewListener){
        ProductDao productDao = new ProductDao();
        productDao.searchProductByTextInPages(query, pageOffset, PAGE_SIZE, new ResultListener<ProductContainer>() {
            @Override
            public void finish(ProductContainer result) {

                if(result.getProductList().size() < PAGE_SIZE){
                    areThereMoreProducts = false;
                }
                pageOffset = pageOffset + PAGE_SIZE;
                viewListener.finish(result);
            }
        });
    }

    public Boolean getAreThereMoreProducts() {
        return areThereMoreProducts;
    }

    public void searchProductByText(String query, final ResultListener<List<Product>> viewListener){
        ProductDao productDao = new ProductDao();
        productDao.searchProductByText(query, new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                viewListener.finish(result);
            }
        });
    }

    public void bringProductDescription(String productId, final ResultListener<Description> viewListener){
        ProductDao productDao = new ProductDao();
        productDao.bringProductDescription(productId, new ResultListener<Description>() {
            @Override
            public void finish(Description result) {
                viewListener.finish(result);
            }
        });
    }

    public void bringProductById(String productId, final ResultListener<Product> viewListener){
        ProductDao productDao = new ProductDao();
        productDao.bringProductById(productId, new ResultListener<Product>() {
            @Override
            public void finish(Product result) {
                viewListener.finish(result);
            }
        });
    }
}
