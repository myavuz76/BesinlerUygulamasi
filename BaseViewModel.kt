package com.example.besinleruygulamasi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel (application),CoroutineScope{
   private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
//arkaplanda ne is yapilirsa yapilsin sonra main sayfasina gidecek

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        //islemler bitince job isini kapat
    }


}
