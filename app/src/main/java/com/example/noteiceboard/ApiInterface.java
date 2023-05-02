package com.example.noteiceboard;

import static com.example.noteiceboard.Constants.CONTENT_TYPE;
import static com.example.noteiceboard.Constants.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key="+SERVER_KEY, "CONTENT_TYPE:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);

}
