package com.example.mobilliumvitrinovachallangeapp.api.apiservice

import com.example.mobilliumvitrinovachallangeapp.model.HomeModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/v2/discover")
    fun getVitrinovaContent(): Call<HomeModel>
}