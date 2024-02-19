package com.example.facebook;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class clsConnection {
    ConnectionClass c1;
    String call, db, un, passwords;
    Connection connect;
    Context mcontext;
    ResultSet rs;

    public clsConnection(Context context) {
        mcontext = context;
    }

    @SuppressLint("NewAPI")
    private Connection CONN(String _user, String _pass, String _db, String _server) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connURL;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connURL = "jdbc:jtds:sqlserver://" + _server + ";Databasename=" + _db + ";user=" + _user + ";password=" + _pass + ";";
            conn = DriverManager.getConnection(connURL);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }


    public ResultSet SelectDS(String sql) {
//        /************* CONNECTION DATABASE VARIABLES ***************/
        c1 = new ConnectionClass();
        call = c1.getip();
        un = c1.getun();
        passwords = c1.getpassword();
        db = c1.getdb();

        connect = CONN(un, passwords, db, call);

        try {
            Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            rs = null;
        }
        return rs;
    }

    public void exeQry(String sql) {
//        /************* CONNECTION DATABASE VARIABLES ***************/
        c1 = new ConnectionClass();
        call = c1.getip();
        un = c1.getun();
        passwords = c1.getpassword();
        db = c1.getdb();
        connect = CONN(un, passwords, db, call);

        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void internetMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("Your Data is Off");
        builder.setMessage("Turn on data or Wi-Fi in\nSettings.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.cancel())
                .setNegativeButton("Settings", (dialog, id) -> mcontext.startActivity(new Intent(Settings.ACTION_SETTINGS)));
        final AlertDialog alert = builder.create();
        alert.show();
    }

}


//public class clsConnection // declaring connection type of class
//{
//    ConnectionClass c1;
//    String call, db, un, passwords;
//    Connection connect;
//    ResultSet rs;//rs is cursor type of data type that will hold data from database
//    Context mContext;
//
//    public clsConnection(Context context) {
//        mContext = context;
//    }
//
//    @SuppressLint("NewApi")
////SuppressLint will allow other program's api to access classes of this program
//    private Connection CONN(String _user, String _pass, String _DB, String _server) //return type is connection
//    {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        Connection conn = null;
//        String ConnURL = null;
//        try {
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");//
//            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
//                    + "databaseName=" + _DB + ";user=" + _user + ";password="
//                    + _pass + ";";
//
//            conn = DriverManager.getConnection(ConnURL);
//        } catch (SQLException se) {
//            Log.e("ERROR", se.getMessage());
//        } catch (ClassNotFoundException e) {
//            Log.e("ERROR", e.getMessage());
//        } catch (Exception e) {
//            Log.e("ERROR", e.getMessage());
//        }
//        return conn;
//    }


//select query only
//    public ResultSet SelectDS(String sql) {
//        /************* CONNECTION DATABASE VARIABLES ***************/
//        c1 = new ConnectionClass();
//        call = c1.getip();
//        un = c1.getun();
//        passwords = c1.getpassword();
//        db = c1.getdb();
//
//        connect = CONN(un, passwords, db, call);
//
//        try {
//            Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            rs = statement.executeQuery(sql);
//        } catch (SQLException e) {
//            rs = null;
//        }
//        return rs;
//    }
//
//}
//Except Select Statement
//    public void exeQry(String sql) {
//        /************* CONNECTION DATABASE VARIABLES ***************/
//        c1 = new ConnectionClass();
//        call = c1.getip();
//        un = c1.getun();
//        passwords = c1.getpassword();
//        db = c1.getdb();
//        connect = CONN(un, passwords, db, call);
//
//        try {
//            Statement statement = connect.createStatement();
//            statement.executeUpdate(sql);
//
//        } catch (SQLException e) {
//
//        }
//    }