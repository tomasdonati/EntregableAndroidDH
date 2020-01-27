package com.TomasDonati.mercadoesclavodh.view.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.FirestoreController;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.TomasDonati.mercadoesclavodh.view.Activities.LoginActivity;
import com.TomasDonati.mercadoesclavodh.view.Adapters.ProductListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProductListAdapter.ProductListAdapterListener {

    private RecyclerView favouriteProductRecycler;
    private TextView userEmailTextView;
    private LinearLayoutManager linearLayoutManager;
    private ProductListAdapter productListAdapter;

    private FirebaseUser currentUser;
    private ProfileFragmentListener profileFragmentListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewFinder(view);

        //a cambiar
        userEmailTextView.setText("Bienvenido a su perfil!");

        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        productListAdapter = new ProductListAdapter(new ArrayList<Product>(), this);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final FirestoreController firestoreController = new FirestoreController();
        firestoreController.bringFavouriteProducts(new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                if(result == null){
                    Toast.makeText(getContext(), "Aun no has agregado favoritos", Toast.LENGTH_SHORT).show();
                }else{
                    firestoreController.bringFavouriteProducts(new ResultListener<List<Product>>() {
                        @Override
                        public void finish(List<Product> result) {
                            productListAdapter.setProductList(result);
                        }
                    });

                    favouriteProductRecycler.setLayoutManager(linearLayoutManager);
                    favouriteProductRecycler.setAdapter(productListAdapter);
                }
            }
        });
    }

    private void viewFinder(View view){
        favouriteProductRecycler = view.findViewById(R.id.profileFragment_recyclerView_favouriteList);
        userEmailTextView = view.findViewById(R.id.profileFragment_textView_userEmail);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        profileFragmentListener = (ProfileFragmentListener) context;
    }

    @Override
    public void listenToProduct(Product product) {
        profileFragmentListener.recieveFavouriteProduct(product);
    }

    public interface ProfileFragmentListener{
        public void recieveFavouriteProduct(Product product);
    }
}
