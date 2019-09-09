package com.segal.lqtauditproject.Helpers;

/**
 * Created by Hossein on 11/1/2017.
 */

public class StringHelpers {
    public static boolean isNullOrEmpty(String string){
        if(string==null)
            return true;

        if(string.length() < 1)
            return true;

        return false;
    }
}
