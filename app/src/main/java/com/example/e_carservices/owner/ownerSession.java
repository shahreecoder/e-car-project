package com.example.e_carservices.owner;

import android.content.Context;
import android.content.SharedPreferences;

public class ownerSession {
    //variables
    SharedPreferences owner;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "UserName";

    ownerSession(Context _context) {
        context = _context;
        owner = context.getSharedPreferences("ownerLoginSession", Context.MODE_PRIVATE);
        editor = owner.edit();
    }

    public void createloginowner(String username) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);

        editor.commit();
    }

    public String ownername() {
        return owner.getString(KEY_USERNAME, null);
    }

    public boolean checkLogin() {
        if (owner.getBoolean(IS_LOGIN, false)) {
            return true;
        } else
            return false;
    }

    public void Logoutowner() {
        editor.clear();
        editor.commit();
    }

}
