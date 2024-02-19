package com.example.facebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;

public class AllWebActivity extends AppCompatActivity {
    WebView w1;
    ProgressDialog pDialog;
    String cd;
    String sDaySchedule = "file:///android_asset/website/notavailable.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_web);

        w1 = findViewById(R.id.all_web_view);
        w1.setWebViewClient(new MyBrowser());

        w1.getSettings().setLoadsImagesAutomatically(true);
        w1.getSettings().setJavaScriptEnabled(true);
        w1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        w1.setWebViewClient(new MyBrowser());

        new LoadMessages(this).execute();
    }

    private static class LoadMessages extends AsyncTask<Void, Void, Void>  //Asynchronous task= which runs in background(loading type)
    {
        //Getting a warning about memory leak so changed this class to static and added below-
        //-activity weak reference code...
        private final WeakReference<AllWebActivity> activityWeakReference;

        LoadMessages(AllWebActivity activity)
        {
            activityWeakReference = new WeakReference<>(activity);
        }
        @Override
        protected void onPreExecute()//before executing the task do this
        {
            super.onPreExecute();

            AllWebActivity activity=activityWeakReference.get();
            if (activity == null || activity.isFinishing())
            {
                return;
            }
            activity.pDialog = new ProgressDialog(activity);//pDialog=Progress Dialog
            activity.pDialog.setMessage("Loading...");
            // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbox));
            activity.pDialog.setCancelable(false);//this asynchronous task is non cancelable
            activity.pDialog.show();
        }

        protected Void doInBackground(Void... args) //the BackGround Process
        {

            AllWebActivity activity=activityWeakReference.get();
            if (activity == null || activity.isFinishing())
            {
                return null;
            }
            Intent in = activity.getIntent();
            if (in != null) {
                activity.cd = in.getStringExtra("Url");
            } else {
                activity.cd = activity.sDaySchedule;
            }

            return null;
        }

        protected void onPostExecute(Void v)//wecan't do foreground task in backGround
        //So we need to set list component in postExecute
        {
            AllWebActivity activity=activityWeakReference.get();
            if (activity == null || activity.isFinishing())
            {
                return;
            }

            activity.w1.loadUrl(activity.cd);
            activity.pDialog.dismiss();//after Loading messages dismess the loading message
        }

    }

    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}