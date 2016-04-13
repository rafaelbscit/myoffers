package br.com.battista.myoffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.model.Offer;

public class ProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = ProductActivity.class.getSimpleName();

    private Long codeProduct;

    private TextView lblCodeProduct;
    private TextView lblNameProduct;
    private TextView lblCategory;
    private TextView lblBrand;
    private TextView lblVendor;
    private TextView lblUpdatedAt;
    private TextView lblAveragePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Offer offer = loadDataFromDatabase();
        if (offer != null) {
            codeProduct = offer.getCodeProduct();
            loadUIViews();
            fillDataUI(offer);
        } else {
            Toast.makeText(this, "O produto não foi localizado, favor tentar novamente!",
                    Toast.LENGTH_LONG);
        }
    }

    private void fillDataUI(Offer offer) {
        lblCodeProduct.setText(String.valueOf(offer.getCodeProduct()));
        lblNameProduct.setText(offer.getName());
        lblCategory.setText(offer.getCategory());
        lblBrand.setText(offer.getBrand());
        lblVendor.setText(offer.getVendor());
        lblUpdatedAt.setText(DateFormat.format("dd/MM/yyyy HH:mm", offer.getUpdatedAt()));

        Locale locale = new Locale("pt", "BR");
        String price = NumberFormat.getNumberInstance(locale).format(offer.getPrice());
        lblAveragePrice.setText(price);

    }

    private void loadUIViews() {
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblNameProduct = (TextView) findViewById(R.id.lblNameProduct);
        lblCategory = (TextView) findViewById(R.id.lblCategory);
        lblBrand = (TextView) findViewById(R.id.lblBrand);
        lblVendor = (TextView) findViewById(R.id.lblVendor);
        lblUpdatedAt = (TextView) findViewById(R.id.lblUpdatedAt);
        lblAveragePrice = (TextView) findViewById(R.id.lblAveragePrice);
    }

    private Offer loadDataFromDatabase() {
        Long id = getIntent().getExtras().getLong(ViewConstant.PARAM_ID_PRODUCT, 0l);
        return new OfferController().loadFromDatabaseById(id);
    }

    public void cancelViewProduct(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void editViewProduct(View view) {
        Offer offer = new OfferController().loadFromDatabaseByCodeProduct(codeProduct);
        if (offer.getId() != null) {
            startEditProductActivity(offer.getId());
        } else {
            Toast.makeText(this, "O produto não foi localizado, favor tentar novamente!",
                    Toast.LENGTH_LONG);
        }
    }

    private void startEditProductActivity(Long id) {
        Log.i(TAG_CLASSNAME, String.format("Load to product with id:%s.", id));
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_ID_PRODUCT, id);

        Intent intent = new Intent(this, EditProductActivity.class);
        intent.putExtras(args);
        startActivity(intent, args);
    }
}
