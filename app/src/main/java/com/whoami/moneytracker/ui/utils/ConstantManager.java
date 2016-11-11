package com.whoami.moneytracker.ui.utils;


public final class ConstantManager {

    public static final String STATUS_SUCCEED = "success";
    public static final String STATUS_LOGIN_BUSY_ALREADY = "Login busy already";

    public static final String SHARE_PREF_FILE_NAME = "money_manager_shared_pref";
    public static final String SHARED_PREF = "shared_pref";

    public static final String REGISTER_FLAG = "1";
    public static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";

    public static final int ID = 1;
    public static final String SEARCH_QUERY_ID = "search_query_id";
    public static final int DELAY = 300;

    public static final String DateFormat = "dd.MM.yyyy";
    public static final int SPLASH_SCREEN_TIMEOUT = 2000;

    public static final int MIN_LENGTH = 5;

    public static int REQUEST_CODE = 10;
    public static final String TOKEN_KEY = "token_key";
    public static final String GOOGLE_TOKEN_KEY = "google_token_key";
    public static final String LOGIN_SUCCEED = "success";
    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

    public static final String AVATAR = "avatar";
    public static final String USER_NAME = "name";
    public static final String USER_EMALE = "email";


}
