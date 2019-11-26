package com.TomasDonati.mercadoesclavodh.view.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.TomasDonati.mercadoesclavodh.R;
import com.TomasDonati.mercadoesclavodh.model.poho.Product;
import com.TomasDonati.mercadoesclavodh.view.Fragments.HomeFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.ProductDetailFragment;
import com.TomasDonati.mercadoesclavodh.view.Fragments.ProductListFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements ProductListFragment.FragmentListener {

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

        pasteFragment(new HomeFragment());

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
                .add(R.id.mainActivity_frameLayout_fragmentContainer, fragment, null)
                .addToBackStack(null)
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
}
