package com.moaz.mymovieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by xkcl0301 on 10/31/2016.
 */
public class Utilities {
    public static boolean isNetworkConnected(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
