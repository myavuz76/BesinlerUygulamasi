package com.example.besinleruygulamasi.service


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.besinleruygulamasi.model.Besin


@Dao
interface BesinDAO {

    // veritabanina baglandigimiz, verileri cektigimiz, verileri ekledigimiz kodlari
    //ekleyecegimiz sinif burasi
    //DAO Data Access Object
    // @Insert ile veri ekleme islemini yapiyoruz
    // birden fazla veri almak icin varrag kullaniyoruz

    @Insert
    suspend fun insertAll(vararg besin : Besin) : List<Long>
    //neden Long veritabaninda otomatik olusturulan id leridöndürecek

    /*
    1- @Insert yaptik -> room dan geliyor
    2- suspend fonksiyonu olusturduk corotine scope de calistirlacagi icin
    3-vararg kullandik birden fazla ve istedigimiz kadar besin objesini vermemize olanak sagliyor
    4- List<Long> döndürüyor bunun nedeni de id ler

    bundan sonra insertAll fonksiyonunu cagirarak ve bütün listemi vererek ekleme islemi yapiyoruz
     */

    //veri cekme

    @Query("SELECT * FROM besin")
    suspend fun getAllBesin(): List<Besin>

    @Query("SELECT * FROM besin WHERE uuid=:besinId")
    suspend fun getBesin(besinId : Int):Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin()
}
