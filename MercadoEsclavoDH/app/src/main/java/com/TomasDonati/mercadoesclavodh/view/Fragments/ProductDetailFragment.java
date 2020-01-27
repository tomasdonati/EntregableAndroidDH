package com.TomasDonati.mercadoesclavodh.view.Fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.FirestoreController;
import com.TomasDonati.mercadoesclavodh.controller.ProductController;
import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.TomasDonati.mercadoesclavodh.view.Activities.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    public static final String PRODUCT_DETAIL_KEY = "productDetailKey";

    private TextView productName;
    private TextView productPrice;
    private ImageView productImage;
    private ProgressBar productImageProgressBar;
    private ToggleButton favouriteButton;
    private TextView productDescription;

    private Product foundProduct;
    private ProductController productController;

    private Boolean isFavourite;

    private FirebaseUser currentUser;
    private FirestoreController firestoreController;


    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        viewFinder(view);

        initializeComponents();

        Bundle bundle = getArguments();
        foundProduct = (Product) bundle.getSerializable(PRODUCT_DETAIL_KEY);

        bringAndSetDescription();

        setProductDetailView(foundProduct);

        setFavButtonOnClick();

        firestoreController.bringFavouriteProducts(new ResultListener<List<Product>>() {
            @Override
            public void finish(List<Product> result) {
                isFavourite = result.contains(foundProduct);
                updateFavButtonState();
            }
        });

        return view;

    }

    private void setFavButtonOnClick() {
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser == null){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    firestoreController.addProductToFav(foundProduct);
                    isFavourite = !isFavourite;
                    updateFavButtonState();
                }
            }
        });
    }

    private void bringAndSetDescription() {
        productController.bringProductDescription(foundProduct.getProductId(), new ResultListener<Description>() {
            @Override
            public void finish(Description result) {
                productDescription.setText(result.getDescriptionText());
            }
        });
    }

    private void initializeComponents() {
        firestoreController = new FirestoreController();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        productController = new ProductController();
    }

    private void setProductDetailView(Product foundProduct) {
        productName.setText(foundProduct.getProductName());
        productPrice.setText("$" + foundProduct.getProductPrice());
        Glide.with(productImage).load(foundProduct.getProductThumbnail()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                productImageProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                productImageProgressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .into(productImage);
    }

    private void updateFavButtonState(){
        if(isFavourite){
            favouriteButton.setChecked(true);
        } else{
            favouriteButton.setChecked(false);
        }
    }

    private void viewFinder(View view) {
        productName = view.findViewById(R.id.productDetailFragment_textView_productName);
        productPrice = view.findViewById(R.id.productDetailFragment_textView_productPrice);
        productImage = view.findViewById(R.id.productDetailFragment_imageView_productImage);
        productImageProgressBar = view.findViewById(R.id.productDetailFragment_progressBar);
        favouriteButton = view.findViewById(R.id.productDetailFragment_toggleButton_favouriteButton);
        productDescription = view.findViewById(R.id.productListCell_textView_productDescription);

    }

}
