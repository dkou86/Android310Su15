package com.uw.android310.lesson8.util;


public class Constants {

    /**
     *  Client ID for Android310 app registered with Imgur
     */
    public static final String IMGUR_CLIENT_ID = "373762842f625d9";

    /**
     *  Client Secret for Android310 app registered with Imgur
     */
    public static final String IMGUR_CLIENT_SECRET = "95e6032ec97388c05d80d88268f3f14af31b2134";

    public static final boolean LOGGING = false;

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + IMGUR_CLIENT_ID;
    }
}
