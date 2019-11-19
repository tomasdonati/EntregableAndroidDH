package com.TomasDonati.mercadoesclavodh.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.ProductController;
import com.TomasDonati.mercadoesclavodh.model.Product;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    private RecyclerView productListRecyclerView;
    private ProductListAdapter productListAdapter;
    private ProductController productController = new ProductController();

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        productListRecyclerView = view.findViewById(R.id.productListFragment_recyclerView_productList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        productListAdapter = new ProductListAdapter(new ArrayList<Product>());

        productListRecyclerView.setLayoutManager(linearLayoutManager);
        productListRecyclerView.setAdapter(productListAdapter);

        return view;
    }

    //metodo para que se ejecute el pediddo del controller
    public void searchForProduct(String query){
        if(query.length() < 3){
            return;
        }
        productController.searchProductByText(query, new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                productListAdapter.setProductList(result);
            }
        });
    }

}
