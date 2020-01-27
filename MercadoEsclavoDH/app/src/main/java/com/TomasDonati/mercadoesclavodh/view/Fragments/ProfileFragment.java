package com.TomasDonati.mercadoesclavodh.view.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.TomasDonati.mercadoesclavodh.model.pojo.User;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.TomasDonati.mercadoesclavodh.view.Activities.LoginActivity;
import com.TomasDonati.mercadoesclavodh.view.Adapters.ProductListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProductListAdapter.ProductListAdapterListener {

    private RecyclerView favouriteProductRecycler;
    private TextView userEmailTextView;
    private TextView userFullNameTextView;
    private LinearLayoutManager linearLayoutManager;
    private ProductListAdapter productListAdapter;
    private Button logOutButton;
    private Button logInButton;

    private List<Product> productList = new ArrayList<>();

    private User newUser;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    private ProfileFragmentListener profileFragmentListener;

    private static final String USERS_COLLECTION = "users";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewFinder(view);

        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        productListAdapter = new ProductListAdapter(new ArrayList<Product>(), this);

        configureButtons();

        return view;
    }

    private void configureButtons() {
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                productListAdapter.setProductList(new ArrayList<Product>());
                userEmailTextView.setText(null);
                userFullNameTextView.setText(null);

                Toast.makeText(getContext(), "Has cerrado la sesion", Toast.LENGTH_SHORT).show();
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        controllerCall();

        if(currentUser == null){
            Toast.makeText(getContext(), "Aun no esta logueado", Toast.LENGTH_SHORT).show();
        }else{
            getLoggedInUser();
        }
    }

    private void getLoggedInUser() {
        DocumentReference userInfo = firestore.collection(USERS_COLLECTION).document(currentUser.getUid());
        userInfo.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        newUser = documentSnapshot.toObject(User.class);
                        userFullNameTextView.setText("Nombre: " + newUser.getUserFullName());
                        userEmailTextView.setText("Email: " + newUser.getUserEmail());

                    }else{
                        Toast.makeText(getContext(), "Aun no esta logueado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void controllerCall() {
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
                            productList = result;
                        }
                    });

                    favouriteProductRecycler.setLayoutManager(linearLayoutManager);
                    favouriteProductRecycler.setAdapter(productListAdapter);

                    //drag n drop
                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
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

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(favouriteProductRecycler);
                }
            }
        });
    }

    private void viewFinder(View view){
        favouriteProductRecycler = view.findViewById(R.id.profileFragment_recyclerView_favouriteList);
        userEmailTextView = view.findViewById(R.id.profileFragment_textView_userEmail);
        logOutButton = view.findViewById(R.id.profileFragment_button_logOutButton);
        logInButton = view.findViewById(R.id.profileFragment_button_logInButton);
        userFullNameTextView = view.findViewById(R.id.profileFragment_textView_userFullName);
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
