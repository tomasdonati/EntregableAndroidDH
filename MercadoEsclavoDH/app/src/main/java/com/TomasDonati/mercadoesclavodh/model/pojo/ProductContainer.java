package com.TomasDonati.mercadoesclavodh.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductContainer {

    @SerializedName("results")
    private List<Product> productList;

    public ProductContainer() {
    }

    public ProductContainer(List<Product> productList){
        this.productList = productList;
    }

    public Boolean containsProduct(Product product){
        return productList.contains(product);
    }

    public void addProduct(Product product){
        productList.add(product);
    }

    public void removeProduct(Product product){
        productList.remove(product);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
