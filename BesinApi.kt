package com.example.besinleruygulamasi.service

import com.example.besinleruygulamasi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

//interface olarak olusturulur.

interface BesinApi {

    //Get ve Post islemleri burda yapilir
    //https://myavuz76.lima-city.de/besinler.json

    @GET("besinler.json")
    fun getBesin() :Single<List<Besin>>
}
