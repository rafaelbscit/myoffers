package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

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
                Log.i("INFO", "Load next activity Product!");
                startActivity(new Intent(currentActivity, ProductActivity.class));
            }
        });

        ImageButton btnSearchProduct = (ImageButton) findViewById(R.id.btnSearchProduct);
        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("INFO", "Load next activity Edit Product!");
                startActivity(new Intent(currentActivity, EditProductActivity.class));
            }
        });
    }
}
