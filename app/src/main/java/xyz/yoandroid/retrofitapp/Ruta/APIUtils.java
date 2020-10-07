package xyz.yoandroid.retrofitapp.Ruta;

import xyz.yoandroid.retrofitapp.Interface.JsonPlaceHolderApi;

public class APIUtils {

    public static final String BASE_URL = "https://node-api-mysql.herokuapp.com/";

    public static JsonPlaceHolderApi getInterfaceEmployee() {
        return APIClient.client(BASE_URL).create(JsonPlaceHolderApi.class);
    }

}
