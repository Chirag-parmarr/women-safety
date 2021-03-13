package com.example.womensafety.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wafflecopter.multicontactpicker.ContactResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class prefConfig  {

    private static final String LIST_KEY ="list_key" ;

    public static void writeListInPref(Context context, List<ContactResult> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();
        Log.e("TAG","@@"+list.size());

    }


    public static ArrayList<ContactResult> readListFromPref(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ContactResult>>(){}.getType();
        ArrayList<ContactResult> list = gson.fromJson(jsonString,type);

        return list;
    }

}
