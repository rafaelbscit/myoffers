package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.battista.myoffers.constants.SharedPreferencesKeys;
import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.controller.SharedPreferencesController;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.model.Vendor;
import br.com.battista.myoffers.util.NumberFormatUtil;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class AddProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = EditProductActivity.class.getSimpleName();
    private TextView lblCodeProduct;

    private EditText txtNameProduct;
    private Spinner spnCategory;
    private EditText txtBrand;
    private EditText txtVendor;
    private EditText txtPrice;

    private Offer offer = null;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));
        fillCategories();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUIViews();
        reloadCodeProduct();

    }

    private void reloadCodeProduct() {
        Long codeProduct = getIntent().getExtras().getLong(ViewConstant.PARAM_CODE_PRODUCT, 0l);
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblCodeProduct.setText(String.valueOf(codeProduct));
        offer = new Offer();
    }

    private void fillCategories() {
        Spinner spinner = (Spinner) findViewById(R.id.spnCategory);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void cancelEditProduct(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void addProduct(View view) {
        if (!isOnline()) {
            Toast.makeText(this,
                    "Para cadastrar/editar um produto é necessário está conectado a internet!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        offer = fillOfferByData();

        final Activity currentActivity = this;
        new StartupApp(this, "", false) {

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(currentActivity,
                            String.format("Sucesso ao criar o produto '%s' com código de barra: %s!",
                                    offer.getName(), offer.getCodeProduct()), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(currentActivity, MainActivity.class));
                } else {
                    Toast.makeText(currentActivity,
                            String.format("Error ao criar um novo produco com código:%s! " +
                                            "Favor verificar sua conexão com a internet!",
                                    offer.getCodeProduct()), Toast.LENGTH_LONG).show();
                }
                getProgress().dismiss();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    OfferController offerController = new OfferController();
                    offer = offerController.createNewOfferInServerAndDatabase(offer);
                    return offer.getId() != null;
                } catch (Exception e) {
                    android.util.Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                    return false;
                }
            }
        }.execute();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadUIViews() {
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        txtNameProduct = (EditText) findViewById(R.id.txtNameProduct);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        txtBrand = (EditText) findViewById(R.id.txtBrand);
        txtVendor = (EditText) findViewById(R.id.txtVendor);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
    }

    private Offer fillOfferByData() {
        Log.i(TAG_CLASSNAME, "Create new offer from activity data!");

        Long codeProduct = Long.valueOf(lblCodeProduct.getText().toString());
        offer.setCodeProduct(codeProduct);
        offer.setName(txtNameProduct.getText().toString());
        offer.setCategory(String.valueOf(spnCategory.getSelectedItem()));
        offer.setBrand(txtBrand.getText().toString());

        Log.i(TAG_CLASSNAME, "Create new vendor from activity data!");
        List<Vendor> vendors = new ArrayList<Vendor>();
        offer.setVendors(vendors);

        Vendor vendor = new Vendor();
        vendors.add(vendor);
        vendor.setVendor(txtVendor.getText().toString());
        vendor.setCodeProduct(codeProduct);

        SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);
        vendor.setCity(sharedPreferencesController.getString(SharedPreferencesKeys.USER_LOCATION_CITY_KEY, ""));
        vendor.setState(sharedPreferencesController.getString(SharedPreferencesKeys.USER_LOCATION_STATE_KEY, ""));

        String strPrice = txtPrice.getText().toString();
        vendor.setPrice(NumberFormatUtil.parse(strPrice));
        return offer;
    }

}