package com.TomasDonati.mercadoesclavodh.view.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.ProductController;
import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.model.pojo.ProductContainer;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.TomasDonati.mercadoesclavodh.view.Adapters.ProductListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment implements ProductListAdapter.ProductListAdapterListener {

    private FragmentListener fragmentListener;

    private RecyclerView productListRecyclerView;
    private ProductListAdapter productListAdapter;
    private ProductController productController = new ProductController();
    private List<Product> productList = new ArrayList<>();
    private List<Description> descriptionList = new ArrayList<>();

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
        productListAdapter = new ProductListAdapter(new ArrayList<Product>(), this);

        productListRecyclerView.setLayoutManager(linearLayoutManager);
        productListRecyclerView.setAdapter(productListAdapter);

        //le asigno la callback al recycler
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(productListRecyclerView);

        return view;
    }







    //creo la callback para hacer el drag n drop...
    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(productList, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    //metodo para que se ejecute el pediddo del controller
    public void searchForProduct(String query){
        if(query.length() < 3){
            return;
        }
        productController.searchProductByText(query, new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                productListAdapter.setProductList(result);
                productList = result;
            }
        });

        //esto seguro vuele
        String productId;
        for (Product product:productList) {

            productId = product.getProductId();
            productController.bringProductDescription(productId, new ResultListener<Description>() {
                @Override
                public void finish(Description result) {
                    descriptionList.add(result);
                }
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentListener = (FragmentListener) context;

    }

    @Override
    public void listenToProduct(Product product) {
        fragmentListener.recieveProduct(product);
    }

    public interface FragmentListener {
        public void recieveProduct(Product productList);
    }
}
