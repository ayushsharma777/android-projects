package com.example.facebook;

public class ConnectionClass//To establish connection
{
    String ip;
    String classs;
    String db;
    String un;  //username
    String password;

    public ConnectionClass()//these things below are pre fix for sir's server and this is constructor
    {
        classs = "net.sourceforge.jtds.jdbc.Driver";
        db = "db_a_facebook";
        un = "db_a9df_facebook_admin";
        password = "hello1234rd";
        ip = "SQL1234.site1now.net";
// port 1433

    }

//    public ConnectionClass(String Ip, String Classs, String Db, String Un, String Password)
//    {
//        ip = Ip;
//        classs = Classs;
//        db = Db;
//        un = Un;
//        password = Password;
//    }

    public String getip() {
        return ip;
    }

    public String getclasss() {
        return classs;
    }

    public String getdb() {
        return db;
    }

    public String getun() {
        return un;
    }

    public String getpassword() {
        return password;
    }

    public void setip(String Ip) {
        ip = Ip;
    }

    public void setdb(String Db) {
        db = Db;
    }

    public void setclasss(String Classs) {
        classs = Classs;
    }

    public void setun(String Un) {
        un = Un;
    }

    public void setpassword(String Password) {
        password = Password;
    }
}


