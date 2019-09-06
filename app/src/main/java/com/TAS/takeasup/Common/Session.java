package com.TAS.takeasup.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        pref = ctx.getSharedPreferences("Controller",Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void setLoggedIn(boolean loggedIn){
        editor.putBoolean("loggedInMode",loggedIn);
        editor.commit();
    }
    public boolean loggedIn(){
        return pref.getBoolean("loggedInMode",false);
    }

}
