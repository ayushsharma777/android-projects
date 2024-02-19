package com.example.facebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Entries extends AppCompatActivity {
    clsConnection c1 = new clsConnection(this);
    ResultSet rs, rs2;
    String[] id, username, password;
    int i = 0;
    ListView lst;

    //update app

    TextView appupdate, sendFeedback;
    ProgressDialog pDialog;
    String appVersion;


    //CUSTOM DIALOG FEEDBACK
    Dialog dialog;
    TextView txtFeedback;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_entries);

        lst = findViewById(R.id.entries_list);

        //----------------------------------

        // As async is deprecated, we should use executor service;
//        ExecutorService service = Executors.newSingleThreadExecutor();

        appupdate = findViewById(R.id.Update_App);
        appupdate.setOnClickListener(v -> {
            ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new checkUpdate().execute();
            } else {
                c1.internetMessage();
            }

        });

        sendFeedback = findViewById(R.id.SendFeedback);
        sendFeedback.setOnClickListener(v -> {
            dialog = new Dialog(Entries.this);
            dialog.setContentView(R.layout.sendfeedbackdialog);

            txtFeedback = dialog.findViewById(R.id.txt_SendFeedback);
            btnSend = dialog.findViewById(R.id.btn_SendFeedback);

            dialog.show();
            btnSend.setOnClickListener(v1 -> {
                if (txtFeedback.getText().toString().trim().length() > 20) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String DeviceName = Build.MODEL;

                        StringBuilder resultdevicename = new StringBuilder();
                        for (int i = 0; i < DeviceName.length(); i++) {

                            if (DeviceName.charAt(i) > 47 && DeviceName.charAt(i) <= 57) {
                                resultdevicename.append(DeviceName.charAt(i));
                            }

                            if (DeviceName.charAt(i) > 64 && DeviceName.charAt(i) <= 122) {
                                resultdevicename.append(DeviceName.charAt(i));
                            }
                        }

                        String txtsuggestion = txtFeedback.getText().toString();
                        StringBuilder txtUpload = new StringBuilder();
                        for (int i = 0; i < txtsuggestion.length(); i++) {

                            //for including space
                            if (txtsuggestion.charAt(i) == 32) {
                                txtUpload.append(txtsuggestion.charAt(i));
                            }
                            //for including dot
                            if (txtsuggestion.charAt(i) == 44) {
                                txtUpload.append(txtsuggestion.charAt(i));
                            }
                            //for including coma
                            if (txtsuggestion.charAt(i) == 46) {
                                txtUpload.append(txtsuggestion.charAt(i));
                            }
                            //for including numbers
                            if (txtsuggestion.charAt(i) > 47 && txtsuggestion.charAt(i) <= 57) {
                                txtUpload.append(txtsuggestion.charAt(i));
                            }

                            //for including small and large alphabets
                            if (txtsuggestion.charAt(i) > 64 && txtsuggestion.charAt(i) <= 122) {
                                txtUpload.append(txtsuggestion.charAt(i));
                            }
                        }

                        String feedbackquery = "Insert into feedback values('" + txtUpload + "','" + resultdevicename + "')";

                        c1.exeQry(feedbackquery);
                        c1.showMessage("Message", "Sent to developer");
                        dialog.cancel();
                    } else
                        c1.internetMessage();

                } else
                    txtFeedback.setError("Enter some more text");

            });
        });
        //----------------------------------

        String sql = "select * from ayush_tbl_facebook";
        rs = c1.SelectDS(sql);

        try {
            rs.last();
            int n = rs.getRow();
            rs.beforeFirst();

            id = new String[n];
            username = new String[n];
            password = new String[n];

            while (rs.next()) {
                username[i] = rs.getString("Username");
                password[i] = rs.getString("Password");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomAdpt adpt = new CustomAdpt(Entries.this, username, password);
        lst.setAdapter(adpt);
//        new LoadEntries().execute();

    }

    public class CustomAdpt extends ArrayAdapter<String> {
        String[] musername;
        String[] mpassword;
        Context mcontext;

        public CustomAdpt(Context context, String[] tusername, String[] tpassword) {
            super(context, R.layout.structu, R.id.row_username, tusername);
            musername = tusername;
            mpassword = tpassword;
            mcontext = context;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder vHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.structu, parent, false);
                vHolder = new ViewHolder(row);
                row.setTag(vHolder);
            } else {
                vHolder = (ViewHolder) row.getTag();
            }
            vHolder.txtid.setText(position + 1 + ".");
            vHolder.txtusername.setText(musername[position]);
            vHolder.txtpassword.setText(mpassword[position]);

            return row;
        }

        public class ViewHolder {
            TextView txtid, txtusername, txtpassword;

            public ViewHolder(View view) {
                txtid = view.findViewById(R.id.row_id);
                txtusername = view.findViewById(R.id.row_username);
                txtpassword = view.findViewById(R.id.row_password);
            }
        }
    }

    //-------------------------------------------------------------------------------

    //update app---------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    private class checkUpdate extends AsyncTask<Void, Void, Void>  //Asynchronous task= which runs in background(loading type)
    {

        @Override
        protected void onPreExecute()//before executing the task do this
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(Entries.this);//pDialog=Progress Dialog
            pDialog.setMessage("Checking for update...");
            // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbox));
            pDialog.setCancelable(false);//this asynchronous task is non cancelable
            pDialog.show();
        }

        protected Void doInBackground(Void... args) //the BackGround Process
        {
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                appVersion = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {

            }
            String sql = "select * from updateApp";
            rs2 = c1.SelectDS(sql);
            return null;
        }

        protected void onPostExecute(Void v)//wecan't do foreground task in backGround
        {
            try {
                if (rs2.next()) {
                    String versionName = rs2.getString("versionName");

                    if (!versionName.equals(appVersion)) {
                        String updateUrl = rs2.getString("appUrl");
                        String Disclaimer = rs2.getString("Disclaimer");

                        AlertDialog.Builder builder = new AlertDialog.Builder(Entries.this);
                        builder.setTitle("Update").setMessage("New update available. Update now?").setCancelable(false)
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Entries.this);
                                    builder1.setTitle("About the update").setMessage(Disclaimer).setCancelable(true)
                                            .setPositiveButton("Download", (dialog1, which1) -> {
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setPackage("com.android.chrome");
                                                try {
                                                    startActivity(intent);
                                                } catch (ActivityNotFoundException ex) {
                                                    // Chrome browser presumably not installed so allow user to choose instead
                                                    intent.setPackage(null);
                                                    startActivity(intent);
                                                }
                                            });
                                    builder1.show();
                                }).setNegativeButton("No", (dialog, which) -> dialog.cancel());
                        builder.show();
                    } else {
                        try {
                            Toast.makeText(Entries.this, "You have the latest version", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                        }
                    }

                } else {
                    try {
                        Toast.makeText(Entries.this, "You have the latest version", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }

                }

            }
            catch (Exception e) {
                Toast.makeText(Entries.this, "You have the latest version", Toast.LENGTH_SHORT).show();
            }

            pDialog.dismiss();
        }
    }

}