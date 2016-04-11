package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import br.com.battista.myoffers.controller.facade.OfferFacade;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tlbApp));

        final Activity currentActivity = this;
        ImageButton btnViewProduct = (ImageButton) findViewById(R.id.btnProduct01);
        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i(TAG_CLASSNAME, "Load next activity Product!");
                startActivity(new Intent(currentActivity, ProductActivity.class));
            }
        });

        Log.i(TAG_CLASSNAME, String.format("Load %s offers by database!",
                new OfferFacade().loadFromBatabase().size()));
    }

    public void scanProduct(View view) {

    }

    public void searchProduct(View view) {
        Log.i(TAG_CLASSNAME, "Load next activity Edit Product!");
        startActivity(new Intent(this, EditProductActivity.class));
    }
}
