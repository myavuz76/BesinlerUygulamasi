package com.example.besinleruygulamasi.service

import com.example.besinleruygulamasi.model.Besin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class BesinApiService {

    //Base_Urlolusturuyoruz
    //https://myavuz76.lima-city.de/besinler.json

    private val Base_Url = "https://myavuz76.lima-city.de/"
    private val api = Retrofit.Builder()
        .baseUrl(Base_Url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BesinApi::class.java)
    //bu yazdigimiz degiskenlere ulasabilmek icin bir fonksiyon yaziyoruz

    fun getData(): Single<List<Besin>> {
        return api.getBesin()
    //bu getBesin aBesinApi de olusturdugunuz degisken
    //artik bu sinftan bir nesne olusturur isek BesinApi ve BesinApiServie icerisinde ki
        // bütün fonksiyonlara ulasabiliyouz

    }
}
