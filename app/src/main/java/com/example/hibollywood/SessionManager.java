package com.example.hibollywood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE=0;
    private static  final String PREF_NAME="LOGIN";
    private static  final String  LOGIN="IS_LOGGEDIN";
    public static  final String  EMAIL="EMAIL";
    public static  final String  PASSWORD="PASSWORD";
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("LOGIN",PRIVATE_MODE);
        editor=sharedPreferences.edit();

    }
    public void createSession(String Email, String Password){
        editor.putBoolean(LOGIN,true);
        editor.putString(EMAIL,Email);
        editor.putString(PASSWORD,Password);
        editor.apply();

    }
    public  boolean isLoggedIn(){
        return  sharedPreferences.getBoolean(LOGIN, false);
    }
    public  void CheckLogin(){
        if (!this.isLoggedIn()){
            Intent i=new Intent(context,MainActivity.class);
            context.startActivity(i);
            ((NavDrawer) context).finish();
        }
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user=new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        return user;
    }
    public  void logout(){
        editor.clear();
        editor.commit();
        Intent i= new Intent(context,MainActivity.class);
        context.startActivity(i);
        ((NavDrawer) context).finish();

    }
}
