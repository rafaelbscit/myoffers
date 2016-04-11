package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ProductActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = ProductActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        final Activity currentActivity = this;
        ImageButton btnEditProduct = (ImageButton) findViewById(R.id.btnEditProduct);
        btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i(TAG_CLASSNAME, "Load next activity Edit Product!");
                startActivity(new Intent(currentActivity, EditProductActivity.class));
            }
        });
    }
}
