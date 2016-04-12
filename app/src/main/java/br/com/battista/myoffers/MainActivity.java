package br.com.battista.myoffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.facade.OfferFacade;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.view.fragments.ProductRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = MainActivity.class.getSimpleName();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        List<Offer> offers = loadDataFromDatabase();
        configureUI(offers);

    }

    private List<Offer> loadDataFromDatabase() {
        List<Offer> offers = new OfferFacade().loadFromBatabase();
        Log.i(TAG_CLASSNAME, String.format("Load %s offers by database!", offers.size()));
        offers = filterSizeListProducts(offers);
        return offers;
    }

    private void configureUI(List<Offer> offers) {
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        RecyclerView recyclerView
                = (RecyclerView) findViewById(R.id.rvwListProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        ProductRecyclerViewAdapter recyclerViewAdapter = new ProductRecyclerViewAdapter(this, offers);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private List<Offer> filterSizeListProducts(List<Offer> offers) {
        if (offers.size() > ViewConstant.MAX_PRODUCTS_GRID) {
            return offers.subList(0, ViewConstant.MAX_PRODUCTS_GRID);
        }
        return offers;
    }

    public void scanProduct(View view) {

    }

    public void searchProduct(View view) {
        Log.i(TAG_CLASSNAME, "Load next activity Edit Product!");
        startActivity(new Intent(this, EditProductActivity.class));
    }
}
