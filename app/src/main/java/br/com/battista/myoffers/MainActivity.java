package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.view.adapter.ProductRecyclerViewAdapter;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = MainActivity.class.getSimpleName();
    private EditText txtProduct;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        createUiView();
    }

    private void createUiView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView = (RecyclerView) findViewById(R.id.rvwListProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        ProductRecyclerViewAdapter recyclerViewAdapter = new ProductRecyclerViewAdapter(this, offers);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private List<Offer> filterSizeListProducts(List<Offer> offers) {
        if (offers.size() > ViewConstant.MAX_PRODUCTS_GRID) {
            return offers.subList(0, ViewConstant.MAX_PRODUCTS_GRID);
        }
        return offers;
    }

    public void scanProduct(View view) {
        Log.i(TAG_CLASSNAME, "Scan barcode now!");
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, intent);

        if (scanningResult != null) {
            txtProduct = (EditText) findViewById(R.id.txtProduct);
            String barcode = scanningResult.getContents();
            Log.i(TAG_CLASSNAME, String.format("Result to scan barcode:", barcode));
            txtProduct.setText(barcode);
            loadProductByBarcode(barcode);
        } else {
            Log.i(TAG_CLASSNAME, "No scan data received!");
            Toast.makeText(getApplicationContext(),
                    "Erro ao scanear o código de barra. Por favor, tente novamente ou digite manualmente.",
                    Toast.LENGTH_LONG).show();
        }

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

        loadProductByBarcode(codeProduct);

    }

    private void loadProductByBarcode(String codeProduct) {
        final Long lCodeProduct = Long.valueOf(codeProduct);
        final Activity currentActivity = this;
        new StartupApp(this, "", false) {
            private Offer offer = null;

            @Override
            protected void onPostExecute(Boolean result) {
                if (offer == null) {
                    if (isOnline()) {
                        startAddProductActivity(lCodeProduct);
                    } else {
                        Toast.makeText(currentActivity,
                                "Para cadastrar um novo produto é necessário está conectado a internet!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    startProductActivity(offer.getId());
                }
                getProgress().dismiss();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    OfferController offerController = new OfferController();
                    offerController.loadFromServerAndSaveOffer(lCodeProduct);
                    offer = offerController.loadFromDatabaseByCodeProduct(lCodeProduct);
                } catch (Exception e) {
                    Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                    return false;
                }
                return true;
            }

            public boolean isOnline() {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnectedOrConnecting();
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

    private void startAddProductActivity(Long lCodeProduct) {
        Log.i(TAG_CLASSNAME, String.format("Add product with code:%s.", lCodeProduct));
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_CODE_PRODUCT, lCodeProduct);

        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtras(args);
        startActivity(intent, args);
    }

}
