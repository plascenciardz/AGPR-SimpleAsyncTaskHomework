package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int s = n * 2;

        for (int i = 0; i < 100; i++) {
            // Sleep for the random amount of time
            try {
                Thread.sleep(s);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Return a String result
        return "Awake at last after sleeping for " + s * 100 + " milliseconds!";
    }

    protected void onProgressUpdate(Integer... progress) {
        mProgressBar.get().setProgress(progress[0]);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
        mProgressBar.get().setProgress(100);
    }
}
