package com.example.alex.raiseyourgameapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;





public interface HttpConnection {

    @POST("https://docs.google.com/forms/d/e/1FAIpQLSfbvwpiWdSjXWdWZ-qqDjU9tjn42dtrMgYNhrKZWUVF2vnGGQ/formResponse")
    @FormUrlEncoded
    Call<Void> completeQuestionnaire(
            @Field("entry.1336451505") String name,
            @Field("entry.952145324") String answerQuestionCat
    );


}