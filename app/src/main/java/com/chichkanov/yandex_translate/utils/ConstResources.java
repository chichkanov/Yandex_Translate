package com.chichkanov.yandex_translate.utils;

import java.util.HashMap;
import java.util.Map;

public class ConstResources {

    // Имена shared preferences для состояния языков (определяются спиннерами) и кэша (история и избранное)
    public final static String PREFS_SPINNERS_STATE = "spinners_state";
    public final static String PREFS_CACHE_NAME = "translation_cache";

    // Массив языков и их кодов
    public final static HashMap<String, String> LANGUAGES = createMap();
    public final static String KEY = "trnsl.1.1.20170415T124611Z.ad6b17355364d141.64ba3777be0638c0334939d343b1920945530598";

    private static HashMap<String, String> createMap() {
        HashMap<String, String> lang = new HashMap<>();
        lang.put("английский", "en");
        lang.put("русский", "ru");
        lang.put("азербайджанский", "az");
        lang.put("албанский", "sq");
        lang.put("амхарский", "am");
        lang.put("арабский", "ar");
        lang.put("армянский", "hy");
        lang.put("африкаанс", "af");
        lang.put("баскский", "eu");
        lang.put("башкирский", "ba");
        lang.put("белорусский", "be");
        lang.put("бенгальский", "bn");
        lang.put("болгарский", "bg");
        lang.put("боснийский", "bs");
        lang.put("валлийский", "cy");
        lang.put("венгерский", "hu");
        lang.put("вьетнамский", "vi");
        lang.put("гаитянский", "ht");
        lang.put("галисийский", "hl");
        lang.put("голландский", "nl");
        lang.put("горномарийский", "mrj");
        lang.put("греческий", "el");
        lang.put("грузинский", "ka");
        lang.put("гуджарати", "gu");
        lang.put("датский", "da");
        lang.put("иврит", "he");
        lang.put("идиш", "yi");
        lang.put("индонезийский", "id");
        lang.put("ирландский", "ga");
        lang.put("итальянский", "it");
        lang.put("исландский", "is");
        lang.put("испанский", "es");
        lang.put("казахский", "kk");
        lang.put("каннада", "kn");
        lang.put("каталанский", "ca");
        lang.put("киргизский", "ky");
        lang.put("китайский", "zh");
        lang.put("корейский", "ko");
        lang.put("коса", "xh");
        lang.put("латынь", "la");
        lang.put("латышский", "lv");
        lang.put("литовский", "lt");
        lang.put("люксембургский", "lb");
        lang.put("малагасийский", "mg");
        lang.put("малайский", "ms");
        lang.put("малаялам", "ml");
        lang.put("мальтийский", "mt");
        lang.put("македонский", "mk");
        lang.put("маори", "mi");
        lang.put("марийский", "mhr");
        lang.put("монгольский", "mn");
        lang.put("немецкий", "de");
        lang.put("непальский", "ne");
        lang.put("норвежский", "no");
        lang.put("панджаби", "pa");
        lang.put("папьяменто", "pap");
        lang.put("персидский", "fa");
        lang.put("польский", "pl");
        lang.put("португальский", "pt");
        lang.put("румынский", "ro");
        lang.put("себуанский", "seb");
        lang.put("сербский", "sr");
        lang.put("сингальский", "si");
        lang.put("словацкий", "sk");
        lang.put("словенский", "sl");
        lang.put("суахили", "sw");
        lang.put("сунданский", "su");
        lang.put("таджикский", "tg");
        lang.put("тайский", "th");
        lang.put("тагальский", "tl");
        lang.put("тамильский", "la");
        lang.put("татарский", "tt");
        lang.put("телугу", "te");
        lang.put("турецкий", "tr");
        lang.put("удмуртский", "udm");
        lang.put("узбекский", "uz");
        lang.put("украинский", "uk");
        lang.put("урду", "ur");
        lang.put("финский", "fi");
        lang.put("французский", "fr");
        lang.put("хинди", "hi");
        lang.put("хорватский", "hr");
        lang.put("чешский", "cs");
        lang.put("шведский", "sv");
        lang.put("шотландский", "gd");
        lang.put("эстонский", "et");
        lang.put("эсперанто", "eo");
        lang.put("яванский", "jv");
        lang.put("японский", "ja");
        return lang;
    }

    // Получить ключ по значению языка
    public static String getKeyByValue(String value) {
        for (HashMap.Entry<String, String> entry : LANGUAGES.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
