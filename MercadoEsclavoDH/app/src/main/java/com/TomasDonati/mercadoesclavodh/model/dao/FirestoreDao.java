package com.TomasDonati.mercadoesclavodh.model.dao;

import androidx.annotation.NonNull;

import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.model.pojo.ProductContainer;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreDao {

    public static final String FAVOURITE_PRODUCT = "favouriteProducts";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    private ProductContainer productContainer = new ProductContainer();

    public FirestoreDao(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        bringFavouriteProducts();
    }

    public void addProductToFav(Product product){
        if(currentUser == null){
            return;
        }
        if(productContainer.containsProduct(product)){
            productContainer.removeProduct(product);
        } else{
            productContainer.addProduct(product);
        }
        firebaseFirestore.collection(FAVOURITE_PRODUCT)
                .document(currentUser.getEmail())
                .set(productContainer);
    }

    private void bringFavouriteProducts(){
        if(currentUser == null){
            productContainer = new ProductContainer();
            return;
        }
        firebaseFirestore.collection(FAVOURITE_PRODUCT)
                .document(currentUser.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productContainer = documentSnapshot.toObject(ProductContainer.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                productContainer = new ProductContainer();
            }
        });
    }

    public void bringFavouriteProducts(final ResultListener<List<Product>> controllerListener){
        if(currentUser == null){
            productContainer = new ProductContainer();
            return;
        }
        firebaseFirestore.collection(FAVOURITE_PRODUCT)
                .document(currentUser.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productContainer = documentSnapshot.toObject(ProductContainer.class);
                if(productContainer == null){
                    productContainer = new ProductContainer();
                }
                controllerListener.finish(productContainer.getProductList());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                productContainer = new ProductContainer();
                controllerListener.finish(productContainer.getProductList());
            }
        });
    }

}
