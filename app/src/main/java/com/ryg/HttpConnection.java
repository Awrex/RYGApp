package com.ryg;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Francois Mindiel
 * Modified by Alex Stewart
 * An interface that is called within the AthleteInfo and sends all the data into a Google Form.
 */
public interface HttpConnection {

    @POST("1FAIpQLSc-bfM9FxZ806H7ngJgekEj1hiyCQ1sUsOwVWAWwgk7mmROqw/formResponse")
    @FormUrlEncoded
    Call<Void> userInfo(
            @Field("entry.1098651120") String email,
            @Field("entry.82115465") int age,
            @Field("entry.1536362469") String gender,
            @Field("entry.724368029") String skillLevel,
            @Field("entry.847844195") String country,
            @Field("entry.1552342281") String zipcode,
            @Field("entry.1968082884") String team,
            @Field("entry.421294328") String position,
            @Field("entry.1564669507") String strength,
            @Field("entry.733364938") String medium,
            @Field("entry.1484652033") String weakness,
            @Field("entry.104309976") String high,
            @Field("entry.312873612") String mid,
            @Field("entry.873530762") String low


    );


}