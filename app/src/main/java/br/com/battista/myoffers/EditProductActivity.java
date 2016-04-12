package br.com.battista.myoffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.battista.myoffers.constants.ViewConstant;
import br.com.battista.myoffers.model.Offer;

public class EditProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = EditProductActivity.class.getSimpleName();
    private TextView lblCodeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));
        fillCategories();

        Long codeProduct = getIntent().getExtras().getLong(ViewConstant.PARAM_CODE_PRODUCT, 0l);
        lblCodeProduct = (TextView) findViewById(R.id.lblCodeProduct);
        lblCodeProduct.setText(String.valueOf(codeProduct));
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

}
