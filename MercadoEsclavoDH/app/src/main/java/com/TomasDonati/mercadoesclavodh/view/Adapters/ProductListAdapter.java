package com.TomasDonati.mercadoesclavodh.view.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<Product> productList;
    private ProductListAdapterListener productListAdapterListener;

    public ProductListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public ProductListAdapter(List<Product> productList, ProductListAdapterListener productListAdapterListener){
        this.productList = productList;
        this.productListAdapterListener = productListAdapterListener;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = layoutInflater.inflate(R.layout.cell_product_list, parent, false);

        ProductListViewHolder productListViewHolder = new ProductListViewHolder(cellView);

        return productListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        Product productToShow = productList.get(position);
        holder.bindProduct(productToShow);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder{

        private ImageView productThumbnail;
        private TextView productName;
        private TextView productCondition;
        private TextView productPrice;
        private ProgressBar thumbnailProgressBar;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);

            productThumbnail = itemView.findViewById(R.id.productListCell_imageView_productThumbnail);
            productName = itemView.findViewById(R.id.productListCell_textView_productName);
            productPrice = itemView.findViewById(R.id.productListCell_textView_productPrice);
            thumbnailProgressBar = itemView.findViewById(R.id.productListCell_progressBar);
            productCondition = itemView.findViewById(R.id.productListCell_textView_productCondition);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product clickedProduct = productList.get(getAdapterPosition());
                    productListAdapterListener.listenToProduct(clickedProduct);
                }
            });

        }

        public void bindProduct(Product product){

            productName.setText(product.getProductName());
            productCondition.setText("(" + translateCondition(product) + ")");
            productPrice.setText("$" + product.getProductPrice());

            Glide.with(productThumbnail.getContext())
                    .load(product.getProductThumbnail())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            thumbnailProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            thumbnailProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(productThumbnail);
        }
    }

    private String translateCondition(Product product){
        String productCondition;
        if(product.getProductCondition().equals("new")){
            productCondition = "nuevo";
        } else{
            productCondition = "usado";
        }
        return productCondition;
    }

    public void setProductList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void addProductList(List<Product> results){
        this.productList.addAll(results);
        notifyDataSetChanged();
    }

    public interface ProductListAdapterListener {
        public void listenToProduct(Product product);
    }

}
