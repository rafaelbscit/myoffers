package br.com.battista.myoffers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.battista.myoffers.task.StartupApp;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        final Activity currentActivity = this;
        new StartupApp(this, "Iniciando My Offers!") {
            @Override
            protected void onPostExecute(Boolean result) {
                getProgress().dismiss();
                if (!result) {
                    Toast.makeText(currentActivity,
                            String.format("Erro on create app: %s", currentActivity.getTitle()),
                            Toast.LENGTH_LONG).show();
                    currentActivity.finish();
                } else {
                    Log.i("INFO", "Load next activity Main!");
                    startActivity(new Intent(currentActivity, MainActivity.class));
                }
            }
        }.withOffsetProgress(20).execute();

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("INFO", "Load next activity Main!");
                startActivity(new Intent(currentActivity, MainActivity.class));
            }
        });
    }
}
