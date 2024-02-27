package com.example.besinleruygulamasi.viewModel


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.besinleruygulamasi.model.Besin
import com.example.besinleruygulamasi.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {

    //burda List kullanmamisza gerek yok cünkü bize sadece bir besin gelecek
    val besinLiveData = MutableLiveData<Besin>()

    // room sqlite yapisini kullanmamizi saglayan bir küttüphane.
    fun roomVerisiniAl(id :Int ){

        launch {
            val dao = BesinDatabase(getApplication()).besinDAO()
            val besin = dao.getBesin(id)
            besinLiveData.value = besin


        }

    }

}
