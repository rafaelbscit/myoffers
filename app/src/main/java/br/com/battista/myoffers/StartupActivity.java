package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.view.tasks.StartupApp;

public class StartupActivity extends AppCompatActivity {

    public static final String TAG_CLASSNAME = StartupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_startup);

        final Activity currentActivity = this;
        new StartupApp(this, "Iniciando My Offers!", false) {
            @Override
            protected void onPostExecute(Boolean result) {
                if (!result) {
                    Log.e(TAG_CLASSNAME, "Error loading server data to the application database.");
                    Toast.makeText(currentActivity,
                            String.format("Erro on create app: %s", currentActivity.getTitle()),
                            Toast.LENGTH_LONG).show();
                    currentActivity.finish();
                } else {
                    Log.i(TAG_CLASSNAME, "Load next activity Main!");
                    startActivity(new Intent(currentActivity, MainActivity.class));
                }
                getProgress().dismiss();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    new OfferController().loadFromServerAndSaveOffers();
                } catch (Exception e) {
                    Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                    return false;
                }
                return true;
            }
        }.execute();
    }

    public void openMainActivity(View view) {
        Log.i(TAG_CLASSNAME, "Load next activity Main!");
        startActivity(new Intent(this, MainActivity.class));
    }
}
