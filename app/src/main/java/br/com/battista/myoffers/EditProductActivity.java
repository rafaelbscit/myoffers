package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
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

import java.text.NumberFormat;
import java.util.Locale;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class EditProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = EditProductActivity.class.getSimpleName();
    private TextView lblCodeProduct;

    private EditText txtNameProduct;
    private Spinner spnCategory;
    private EditText txtBrand;
    private EditText txtVendor;
    private EditText txtAveragePrice;

    private Offer offer = null;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));
        fillCategories();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUIViews();
        reloadCodeProduct();
        checkEditProduct();

    }

    private void checkEditProduct() {
        Long id = getIntent().getExtras().getLong(ViewConstant.PARAM_ID_PRODUCT, 0l);
        Offer offerDB = new OfferController().loadFromDatabaseById(id);
        if (offerDB != null && offerDB.getCodeProduct() > 0l) {
            offer = offerDB;
            fillDataUI(offerDB);
        } else {
            Toast.makeText(this, "O produto não foi localizado, favor tentar novamente!",
                    Toast.LENGTH_LONG);
        }
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
        offer = fillOfferByData();

        final Activity currentActivity = this;
        new StartupApp(this, "", false) {

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(currentActivity,
                            String.format("Sucesso ao criar um novo produco com código:%s!",
                                    offer.getCodeProduct()), Toast.LENGTH_LONG).show();
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

    private void fillDataUI(Offer offer) {
        lblCodeProduct.setText(String.valueOf(offer.getCodeProduct()));
        txtNameProduct.setText(offer.getName());

        spnCategory.setSelection(adapter.getPosition(offer.getCategory()), true);
        txtBrand.setText(offer.getBrand());
        txtVendor.setText(offer.getVendor());

        Locale locale = new Locale("pt", "BR");
        String price = NumberFormat.getNumberInstance(locale).format(offer.getPrice());
        txtAveragePrice.setText(price);

    }

    private void loadUIViews() {
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        txtNameProduct = (EditText) findViewById(R.id.txtNameProduct);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        txtBrand = (EditText) findViewById(R.id.txtBrand);
        txtVendor = (EditText) findViewById(R.id.txtVendor);
        txtAveragePrice = (EditText) findViewById(R.id.txtPrice);
    }

    private Offer fillOfferByData() {
        Log.i(TAG_CLASSNAME, "Create new offer from activity data!");

        offer.setCodeProduct(Long.valueOf(lblCodeProduct.getText().toString()));
        offer.setName(txtNameProduct.getText().toString());
        offer.setCategory(String.valueOf(spnCategory.getSelectedItem()));
        offer.setBrand(txtBrand.getText().toString());
        offer.setVendor(txtVendor.getText().toString());
        offer.setPrice(Double.valueOf(txtAveragePrice.getText().toString()));

        return offer;
    }

}
