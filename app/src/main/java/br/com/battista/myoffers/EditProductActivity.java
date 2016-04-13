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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));
        fillCategories();

        Long codeProduct = getIntent().getExtras().getLong(ViewConstant.PARAM_CODE_PRODUCT, 0l);
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblCodeProduct.setText(String.valueOf(codeProduct));
        offer = null;
    }

    private void startProductActivity(Offer offer) {
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_ID_PRODUCT, offer.getId());

        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtras(args);
        startActivity(intent, args);
    }

    private void fillCategories() {
        Spinner spinner = (Spinner) findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
                                    offer.getCodeProduct()), Toast.LENGTH_LONG);
                    startActivity(new Intent(currentActivity, MainActivity.class));
                } else {
                    Toast.makeText(currentActivity,
                            String.format("Error ao criar um novo produco com código:%s! " +
                                            "Favor verificar sua conexão com a internet!",
                                    offer.getCodeProduct()), Toast.LENGTH_LONG);
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

    private Offer fillOfferByData() {
        Log.i(TAG_CLASSNAME, "Create new offer from activity data!");
        Offer offer = new Offer();

        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        offer.setCodeProduct(Long.valueOf(lblCodeProduct.getText().toString()));

        txtNameProduct = (EditText) findViewById(R.id.txtNameProduct);
        offer.setName(txtNameProduct.getText().toString());

        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        offer.setCategory(String.valueOf(spnCategory.getSelectedItem()));

        txtBrand = (EditText) findViewById(R.id.txtBrand);
        offer.setBrand(txtBrand.getText().toString());

        txtVendor = (EditText) findViewById(R.id.txtVendor);
        offer.setVendor(txtVendor.getText().toString());

        txtAveragePrice = (EditText) findViewById(R.id.txtPrice);
        offer.setPrice(Double.valueOf(txtAveragePrice.getText().toString()));

        return offer;
    }

}
