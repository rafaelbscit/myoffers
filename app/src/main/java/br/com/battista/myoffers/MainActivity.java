package br.com.battista.myoffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.util.List;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.view.fragments.ProductRecyclerViewAdapter;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = MainActivity.class.getSimpleName();
    private GridLayoutManager gridLayoutManager;
    private EditText txtProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        List<Offer> offers = loadDataFromDatabase();
        configureUI(offers);

    }

    private List<Offer> loadDataFromDatabase() {
        List<Offer> offers = new OfferController().loadFromBatabase();
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

        ProductRecyclerViewAdapter recyclerViewAdapter =
                new ProductRecyclerViewAdapter(this, offers);
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
        txtProduct = (EditText) findViewById(R.id.txtProduct);
        String codeProduct = txtProduct.getText().toString();
        if (Strings.isNullOrEmpty(codeProduct) ||
                codeProduct.length() < ViewConstant.MIN_DIGITS_CODE_BAR) {
            Log.e(TAG_CLASSNAME,
                    "Code product can not be null and and must have size greater than 12 digits!");
            Toast.makeText(this,
                    "Código do produto não pode ser nulo e deve possuir o tamanho maior que 12 digitos!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        final Long lCodeProduct = Long.valueOf(codeProduct);
        new StartupApp(this, "", false) {
            private Offer offer = null;
            private Boolean loadFromServer = Boolean.FALSE;

            @Override
            protected void onPostExecute(Boolean result) {
                if (offer == null) {
                    startEditProductActivity(lCodeProduct);
                } else {
                    startProductActivity(offer.getId());
                }
                getProgress().dismiss();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    OfferController offerController = new OfferController();
                    loadFromServer = offerController.loadFromServerAndSaveOffer(lCodeProduct);
                    if (loadFromServer) {
                        offer = offerController.loadFromDatabaseByCodeProduct(lCodeProduct);
                    }
                } catch (Exception e) {
                    Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                    return false;
                }
                return true;
            }
        }.execute();

    }

    private void startProductActivity(Long id) {
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_ID_PRODUCT, id);

        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtras(args);
        startActivity(intent, args);
    }

    private void startEditProductActivity(Long lCodeProduct) {
        Log.i(TAG_CLASSNAME, String.format("Load to product with code:%s.", lCodeProduct));
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_CODE_PRODUCT, lCodeProduct);

        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtras(args);
        startActivity(intent, args);
    }

}
