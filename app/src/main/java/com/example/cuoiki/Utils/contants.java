package com.example.cuoiki.Utils;

public class contants {

    public static String localhost = "192.168.0.103:8080";
    //public static String localhost = "192.168.1.20:8080";
    //login và register API
    public static final String ROOT_URL = "http://" + localhost + "/";
    public static final String URL_REGISTER = ROOT_URL + "/shoppingapp/registrationapi.php?apicall=signup";
    public static final String URL_LOGIN= ROOT_URL + "Web/api/v1/login";

    public static final String URL_CATEGORY= ROOT_URL + "Web/api/v1/categories/";

    public static final String URL_PRODUCT = ROOT_URL + "Web/api/v1/products/";

    public static final String URL_PRODUCT2 = ROOT_URL + "Web/api/v1/";


    private static final String URL_EDIT_PROFILE = "http://app.iotstar.vn/appfoods/updateimages.php";

    //private static final String URL_PRODUCT = "http://app.iotstar.vn/appfoods/getcategory.php";
}
