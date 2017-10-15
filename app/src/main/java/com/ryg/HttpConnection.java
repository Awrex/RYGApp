package com.ryg;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface HttpConnection {

    @POST("1FAIpQLSfqWREBmjhUfSwH2DCRk3yyJa1ahIV-foyfMsCePLXA8N6Avg/formResponse")
    @FormUrlEncoded
    Call<Void> userInfo(
            @Field("entry.497691094") String email,
            @Field("entry.258552954") int age,
            @Field("entry.758952046") String gender,
            @Field("entry.8663323") String skillLevel,
            @Field("entry.2130246873") String country,
            @Field("entry.1461153240") String zipcode,
            @Field("entry.472460767") String team,
            @Field("entry.1695673871") String position,
            @Field("entry.1583723510") String strength,
            @Field("entry.211154639") String medium,
            @Field("entry.672614116") String weakness,
            @Field("entry.823592453") String high,
            @Field("entry.758477403") String mid,
            @Field("entry.1511038684") String low


    );


}