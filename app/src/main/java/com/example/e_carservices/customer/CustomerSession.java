package com.example.e_carservices.customer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class CustomerSession {
    SharedPreferences owner;
    SharedPreferences.Editor editor;
    Context context;
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "UserName";
    public static final String KEY_USERID = "1";


    public CustomerSession(Context context) {
        this.context = context;

        owner = context.getSharedPreferences("CustomerLoginSession", Context.MODE_PRIVATE);
        editor = owner.edit();
    }

    public void CreateLogin(String username, String id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USERID, id);
        editor.commit();
    }

    public String customerid() {
        return owner.getString(KEY_USERID, null);
    }

    public String cutomername() {
        return owner.getString(KEY_USERNAME, null);
    }

    public boolean checkLogincustomer() {
        if (owner.getBoolean(IS_LOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void Logoutcustomer() {
        editor.clear();
        editor.commit();
    }


}
