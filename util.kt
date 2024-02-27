package com.example.besinleruygulamasi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.besinleruygulamasi.R

fun ImageView.gorselIndir(url: String? , placeHolder : CircularProgressDrawable){
    val options= RequestOptions().placeholder(placeHolder).error(R.mipmap.ic_launcher)

    Glide.with(context).load(url).into(this)
}

fun placeholderYap( context : Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:imageDownload")
fun imageDownload(view: ImageView, url: String?){
    view.gorselIndir(url, placeholderYap(view.context))

}
