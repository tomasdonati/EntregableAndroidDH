package com.TomasDonati.mercadoesclavodh.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.controller.ProductController;
import com.TomasDonati.mercadoesclavodh.model.Product;
import com.TomasDonati.mercadoesclavodh.model.ProductContainer;
import com.TomasDonati.mercadoesclavodh.utils.ResultListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductListFragment productListFragment;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private Boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFinder();
        setSupportActionBar(toolbar);
        setSearchView();

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
        searchView.setVoiceSearch(true);
    }

    private void viewFinder(){
        toolbar = findViewById(R.id.mainActivity_toolbar);
        searchView = findViewById(R.id.mainActivity_materialSearchView_searchView);
    }

    private void pasteFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivity_frameLayout_fragmentContainer, fragment, null)
                .addToBackStack(null)
                .commit();
    }
}
