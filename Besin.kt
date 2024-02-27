package com.example.besinleruygulamasi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity room veri tabanindan veri cekilirken kullanilir.

@Entity
data class Besin(
    @ColumnInfo("isim")
    @SerializedName("isim")
    val besinIsmi:String?,
    @ColumnInfo("kalori")
    @SerializedName("kalori")
    val besinKalori:String?,
    @ColumnInfo("karbonhidrat")
    @SerializedName("karbonhidrat")
    val besinKarbonhidrat : String?,
    @ColumnInfo("protein")
    @SerializedName("protein")
    val besinProtein:String?,
    @ColumnInfo("yag")
    @SerializedName("yag")
    val besinYag: String?,
    @ColumnInfo("gorsel")
    @SerializedName("gorsel")
    val besinGorsel: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid :Int =0
}
