package br.com.battista.myoffers.task;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class StartupApp extends AsyncTask<Void, Integer, Boolean> {

    private ProgressDialog progress;
    private Integer currProgress = 0;
    private Integer maxProgress = 100;
    private Integer offsetProgress = 10;
    private Integer timeSleep = 1000;
    private Activity activity;

    public StartupApp(Activity activity, String message) {
        this.activity = activity;
        createNewProgressDialog(activity, message);
    }

    public StartupApp(Activity activity, String message, Integer currProgress,
                      Integer maxProgress, Integer offsetProgress, Integer timeSleep) {
        this.currProgress = currProgress;
        this.maxProgress = maxProgress;
        this.offsetProgress = offsetProgress;
        this.timeSleep = timeSleep;
        this.activity = activity;

        createNewProgressDialog(activity, message);
    }

    private void createNewProgressDialog(Activity activity, String message) {
        if (activity == null) {
            String detailMessage = "Activity don't null!";
            Log.e("Erro", detailMessage);
            throw new RuntimeException(detailMessage);
        }

        progress = new ProgressDialog(activity);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(currProgress);
        progress.setMax(maxProgress);
        progress.setMessage(message);
        progress.setIcon(android.R.drawable.ic_dialog_info);

        Log.i("Info",
                String.format("New progress dialog [progress:%s, max:%s, offset:%s, message:%s, activity:%s]",
                        currProgress, maxProgress, offsetProgress, message, activity.getTitle()));
    }

    public ProgressDialog getProgress() {
        return progress;
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
            Log.e("Error", e.getMessage());
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
                    String.format("Erro on create app: %s", activity.getTitle()),
                    Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }
}
