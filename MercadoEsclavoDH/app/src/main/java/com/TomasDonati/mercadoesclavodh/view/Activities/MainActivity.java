package com.TomasDonati.mercadoesclavodh.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.ProductController;
import com.TomasDonati.mercadoesclavodh.model.pojo.Description;
import com.TomasDonati.mercadoesclavodh.model.pojo.Product;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.TomasDonati.mercadoesclavodh.view.Fragments.AboutUsFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.HomeFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.ProductDetailFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.ProductListFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListFragment.FragmentListener, NavigationView.OnNavigationItemSelectedListener {

    private ProductListFragment productListFragment;
    private AboutUsFragment aboutUsFragment;
    private ProfileFragment profileFragment;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private MaterialSearchView searchView;
    private NavigationView navigationView;
    private Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFinder();
        setSupportActionBar(toolbar);
        setBurgerButton();
        setSearchView();
        navigationView.setNavigationItemSelectedListener(this);


        aboutUsFragment = new AboutUsFragment();
        profileFragment = new ProfileFragment();
        replaceFragment(new HomeFragment());
    }

    private void setBurgerButton() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


//methods



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        MenuItem searchButton = menu.findItem(R.id.toolbar_item_search);

        searchView.setMenuItem(searchButton);

        return super.onCreateOptionsMenu(menu);
    }

    private void setSearchView(){
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(first || !productListFragment.isVisible()){
                    productListFragment = new ProductListFragment();
                    pasteFragment(productListFragment);
                    first = false;
                }
                productListFragment.searchForProduct(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void viewFinder(){
        toolbar = findViewById(R.id.mainActivity_toolbar);
        searchView = findViewById(R.id.mainActivity_materialSearchView_searchView);
        drawerLayout = findViewById(R.id.mainActivity_drawerLayout);
        navigationView = findViewById(R.id.mainActivity_navigationView);
    }

    private void pasteFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainActivity_frameLayout_fragmentContainer, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivity_frameLayout_fragmentContainer, fragment, null)
                .commit();
    }

    @Override
    public void recieveProduct(Product product) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProductDetailFragment.PRODUCT_DETAIL_KEY, product);
        productDetailFragment.setArguments(bundle);
        pasteFragment(productDetailFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        switch (itemId){
            case R.id.navigationViewMenu_item_aboutUs:
                pasteFragment(aboutUsFragment);
                break;
            case R.id.navigationViewMenu_item_profile:
                pasteFragment(profileFragment);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
