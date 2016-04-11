package br.com.battista.myoffers.view.tasks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class StartupApp extends AsyncTask<Void, Integer, Boolean> {

    public static final String TAG_CLASSNAME = StartupApp.class.getSimpleName();

    private ProgressDialog progress;
    private Integer currProgress = 0;
    private Integer maxProgress = 100;
    private Integer offsetProgress = 10;
    private Integer timeSleep = 1000;
    private Activity activity;
    private Boolean styleProgress = Boolean.TRUE;

    public StartupApp(Activity activity, String message) {
        this.activity = activity;
        createNewProgressDialog(activity, message);
    }

    public StartupApp(Activity activity, String message, Boolean styleProgress) {
        this.activity = activity;
        this.styleProgress = styleProgress;
        createNewProgressDialog(activity, message);
    }

    public StartupApp(Activity activity, String message, Integer currProgress,
                      Integer maxProgress, Integer offsetProgress, Integer timeSleep, Boolean styleProgress) {
        this.currProgress = currProgress;
        this.maxProgress = maxProgress;
        this.offsetProgress = offsetProgress;
        this.timeSleep = timeSleep;
        this.activity = activity;
        this.styleProgress = styleProgress;

        createNewProgressDialog(activity, message);
    }

    private void createNewProgressDialog(Activity activity, String message) {
        if (activity == null) {
            String detailMessage = "Activity don't null!";
            Log.e(TAG_CLASSNAME, detailMessage);
            throw new RuntimeException(detailMessage);
        }

        progress = new ProgressDialog(activity);
        progress.setCancelable(false);
        if (styleProgress) {
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        } else {
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progress.setIndeterminate(false);
        progress.setProgress(currProgress);
        progress.setMax(maxProgress);
        progress.setMessage(message);
        progress.setIcon(android.R.drawable.ic_dialog_info);

        Log.i(TAG_CLASSNAME,
                String.format("New progress dialog [progress:%s, max:%s, offset:%s, message:%s, activity:%s]",
                        currProgress, maxProgress, offsetProgress, message, activity.getTitle()));
    }

    public ProgressDialog getProgress() {
        return progress;
    }

    public StartupApp withCurrProgress(final Integer currProgress) {
        this.currProgress = currProgress;
        return this;
    }

    public StartupApp withMaxProgress(final Integer maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }

    public StartupApp withOffsetProgress(final Integer offsetProgress) {
        this.offsetProgress = offsetProgress;
        return this;
    }

    public StartupApp withTimeSleep(final Integer timeSleep) {
        this.timeSleep = timeSleep;
        return this;
    }

    @Override
    protected void onPreExecute() {
        progress.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (currProgress <= maxProgress) {
                currProgress += offsetProgress;
                Thread.sleep(timeSleep);
                publishProgress(currProgress);
            }
        } catch (InterruptedException e) {
            Log.e(TAG_CLASSNAME, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setProgress(currProgress);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progress.dismiss();
        if (!result) {
            Toast.makeText(activity,
                    String.format("Erro on create activity: %s", activity.getTitle()),
                    Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }
}
