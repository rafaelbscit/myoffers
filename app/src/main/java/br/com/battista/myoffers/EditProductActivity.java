package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.google.common.base.Strings;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.battista.myoffers.constants.SharedPreferencesKeys;
import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.controller.SharedPreferencesController;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.model.Vendor;
import br.com.battista.myoffers.view.adapter.EditVendorRecyclerViewAdapter;
import br.com.battista.myoffers.view.adapter.EditVendorRecyclerViewHolders;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class EditProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = EditProductActivity.class.getSimpleName();

    private Locale locale = new Locale("pt", "BR");

    private TextView lblCodeProduct;
    private TextView lblNameProduct;
    private TextView lblCategory;
    private TextView lblBrand;
    private TextView lblUpdatedAt;

    private CheckBox chkRevise;

    private Offer offer = null;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        createUiView();
    }

    private void createUiView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditProductActivity.this, LinearLayoutManager.VERTICAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.rvwEditListVendor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
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

        Long id = getIntent().getExtras().getLong(ViewConstant.PARAM_ID_PRODUCT);
        if (id == null || id == 0) {
            return;
        }

        Offer offerDB = new OfferController().loadFromDatabaseById(id);
        if (offerDB != null && offerDB.getCodeProduct() > 0l) {
            offer = offerDB;
            fillDataUI(offerDB);

            List<Vendor> vendors = offer.getVendors();
            if (vendors != null) {
                configureListVendor(vendors);
            }
        } else {
            Toast.makeText(this, "O produto não foi localizado, favor tentar novamente!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void cancelEditProduct(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void saveProduct(View view) {
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
                            String.format("Sucesso ao editar o produto '%s' com código de barra: %s!",
                                    offer.getName(), offer.getCodeProduct()), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(currentActivity, MainActivity.class));
                } else {
                    Toast.makeText(currentActivity,
                            String.format("Error ao editar o produco com código de barra :%s! " +
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

    private void fillDataUI(Offer offer) {
        lblCodeProduct.setText(String.valueOf(offer.getCodeProduct()));
        lblNameProduct.setText(offer.getName());

        Boolean revise = offer.getRevise();
        chkRevise.setChecked(revise);
        chkRevise.setEnabled(!revise);

        lblCategory.setText(offer.getCategory());
        lblBrand.setText(offer.getBrand());
        lblUpdatedAt.setText(DateFormat.format("dd/MM/yyyy HH:mm", offer.getUpdatedAt()));

    }

    private void loadUIViews() {
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblNameProduct = (TextView) findViewById(R.id.lblNameProduct);
        lblCategory = (TextView) findViewById(R.id.lblCategory);
        lblBrand = (TextView) findViewById(R.id.lblBrand);
        lblUpdatedAt = (TextView) findViewById(R.id.lblUpdatedAt);

        chkRevise = (CheckBox) findViewById(R.id.chkRevise);
    }

    private Offer fillOfferByData() {
        Log.i(TAG_CLASSNAME, "Create new offer from activity data!");

        offer.setCodeProduct(Long.valueOf(lblCodeProduct.getText().toString()));
        offer.setName(lblNameProduct.getText().toString());
        offer.setCategory(lblCategory.getText().toString());
        offer.setBrand(lblBrand.getText().toString());
        offer.revise(chkRevise.isChecked());

        fillVendorByData(offer);

        return offer;
    }

    private void fillVendorByData(Offer offer) {
        List<Vendor> vendors = new ArrayList<Vendor>();
        offer.setVendors(vendors);

        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (recyclerView.findViewHolderForLayoutPosition(i) instanceof EditVendorRecyclerViewHolders) {
                EditVendorRecyclerViewHolders childHolder = (EditVendorRecyclerViewHolders) recyclerView.findViewHolderForLayoutPosition(i);

                Vendor vendor = childHolder.getVendor();
                android.util.Log.i(TAG_CLASSNAME, "Load vendor by recyclerView");

                SharedPreferencesController sharedPreferencesController = new SharedPreferencesController(this);
                vendor.setCity(sharedPreferencesController.getString(SharedPreferencesKeys.USER_LOCATION_CITY_KEY, ""));
                vendor.setState(sharedPreferencesController.getString(SharedPreferencesKeys.USER_LOCATION_STATE_KEY, ""));

                vendor.setCodeProduct(offer.getCodeProduct());
                String nameVendor = childHolder.getTxtItemNameVendor().getText().toString();
                vendor.setVendor(nameVendor);

                String strPrice = childHolder.getTxtItemPriceVendor().getText().toString();
                if (Strings.isNullOrEmpty(nameVendor) || Strings.isNullOrEmpty(strPrice)) {
                    continue;
                }
                try {
                    NumberFormat numberInstance = NumberFormat.getNumberInstance(locale);
                    numberInstance.setMinimumFractionDigits(2);
                    Number number = numberInstance.parse(strPrice);

                    vendor.setPrice(number.doubleValue());
                } catch (ParseException e) {
                    vendor.setPrice(Double.valueOf(strPrice.replaceAll("\\,", "\\.")));
                }
                vendors.add(vendor);
            }
        }

    }


    private void configureListVendor(List<Vendor> vendors) {

        EditVendorRecyclerViewAdapter recyclerViewAdapter =
                new EditVendorRecyclerViewAdapter(this, filterSizeListProducts(vendors));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private List<Vendor> filterSizeListProducts(List<Vendor> vendors) {
        if (vendors.size() > ViewConstant.MAX_VENDORS_GRID) {
            return vendors.subList(0, ViewConstant.MAX_VENDORS_GRID);
        }
        vendors.add(new Vendor());
        return vendors;
    }

}
