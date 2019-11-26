package com.TomasDonati.mercadoesclavodh.model.poho;

import com.TomasDonati.mercadoesclavodh.model.poho.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductContainer {

    @SerializedName("results")
    private List<Product> productList;

    public ProductContainer(List<Product> productList){
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
