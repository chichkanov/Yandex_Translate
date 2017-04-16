package com.chichkanov.yandex_translate;

import java.util.HashMap;

/**
 * Created by chichkanov on 16.04.17.
 */

public class ConstResources {

    public final static HashMap<String, String> LANGUAGES = createMap();
    public final static String KEY = "trnsl.1.1.20170415T124611Z.ad6b17355364d141.64ba3777be0638c0334939d343b1920945530598";
    public final static String PREFS_SPINNERS_STATE = "spinners_state";

    private static HashMap<String, String> createMap()
    {
        HashMap<String,String> myMap = new HashMap<String,String>();
        myMap.put("русский", "ru");
        myMap.put("английский", "en");
        return myMap;
    }

}
