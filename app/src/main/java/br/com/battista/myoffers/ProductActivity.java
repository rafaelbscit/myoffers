package br.com.battista.myoffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.facade.OfferFacade;
import br.com.battista.myoffers.model.Offer;

public class ProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = ProductActivity.class.getSimpleName();

    private TextView lblCodeProduct;
    private TextView lblNameProduct;
    private TextView lblCategory;
    private TextView lblBrand;
    private TextView lblVendor;
    private TextView lblAveragePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        Offer offer = loadDataFromDatabase(savedInstanceState);
        if (offer != null) {
            loadUIViews();
        } else {
            Toast.makeText(this, "O produto não foi localizado, favor tentar novamente!", Toast.LENGTH_LONG);
        }

    }

    private void loadUIViews() {
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblCodeProduct.setText("");

        lblNameProduct = (TextView) findViewById(R.id.lblNameProduct);
        lblCodeProduct.setText("");

        lblCategory = (TextView) findViewById(R.id.lblCategory);
        lblCodeProduct.setText("");

        lblBrand = (TextView) findViewById(R.id.lblBrand);
        lblCodeProduct.setText("");

        lblVendor = (TextView) findViewById(R.id.lblVendor);
        lblCodeProduct.setText("");

        lblAveragePrice = (TextView) findViewById(R.id.lblAveragePrice);
        lblCodeProduct.setText("");
    }

    private Offer loadDataFromDatabase(Bundle savedInstanceState) {
        Long id = savedInstanceState.getLong(ViewConstant.PARAM_ID_PRODUCT, 0l);
        return new OfferFacade().loadFromDatabaseById(id);
    }
}
