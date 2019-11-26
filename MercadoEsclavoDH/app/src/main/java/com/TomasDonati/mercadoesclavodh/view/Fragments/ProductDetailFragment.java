package com.TomasDonati.mercadoesclavodh.view.Fragments;


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

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.model.poho.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    public static final String PRODUCT_DETAIL_KEY = "productDetailKey";

    private TextView productName;
    private TextView productPrice;
    private ImageView productImage;
    private ProgressBar productImageProgressBar;


    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        viewFinder(view);

        Bundle bundle = getArguments();

        Product foundProduct = (Product) bundle.getSerializable(PRODUCT_DETAIL_KEY);

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


        return view;

    }

    private void viewFinder(View view) {
        productName = view.findViewById(R.id.productDetailFragment_textView_productName);
        productPrice = view.findViewById(R.id.productDetailFragment_textView_productPrice);
        productImage = view.findViewById(R.id.productDetailFragment_imageView_productImage);
        productImageProgressBar = view.findViewById(R.id.productDetailFragment_progressBar);

    }

}
