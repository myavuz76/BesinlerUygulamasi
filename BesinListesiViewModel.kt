package com.example.besinleruygulamasi.viewModel


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.example.besinleruygulamasi.model.Besin
import com.example.besinleruygulamasi.service.BesinApiService
import com.example.besinleruygulamasi.service.BesinDatabase
import com.example.besinleruygulamasi.service.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    //RecycleView de veri varsa göstercek yoksa göstermeyecek
    //Hata var ise hata mesaji gösterecek yoksa göstermeyeek
    // resim yükleniyor ise göstercek yoksa göstermeyecek
    //Internetten veri cekme islemleri

    //bu islemleri yapabilmek icin MutableLiveData(degistirilebilir) yi kullanarak sürekli bir dinleme yaparak
    // gerceklestiriyoruz

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    //intrnetten verilri almak icin ilk önce BesinApiSrvice den bir nesne olusturuyouz

    private val besinApiService= BesinApiService()
    private val disposable = CompositeDisposable()//birlermis disposable
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private val guncellemZamani =10*60*1000*1000*1000L //dakikanin nano time cevrilmis hali

    // disposable bir kere kullan at bir yapi
    // internetten vri cekecegimiz zaman bir kere calisan sonra hafizada yer kaplamamak
    //icin kapanan bir yapi RxJava yapisidir


    fun refreshData(){
        //burda Zamanlama algoritmasini olusturuyoruz
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime()-kaydedilmeZamani < guncellemZamani){
            // günceleme zamanini simdi ki zamandan cikariyoruz, 10dk dan az gecmis demektir o zaman sqlite den al

            verileriSqlitedenAL()

        }else{
            //10dk dan fazla zaman gecmis demektir o zaman internetten al
            verileriInternettenAl()
        }
        //simdi kaydedilme zamanindan 10dk gectimi 5dk gectimi sunu yap diyoru
        verileriInternettenAl()

    }//Refreshdata son
    fun refreshFrominternet(){
        verileriInternettenAl()
    }

    private fun verileriSqlitedenAL(){
        besinYukleniyor.value=true
        launch {
            //BesinDao da ki getAllBesin fonksiyonunu cagiriyoruz
            val besinListesi= BesinDatabase(getApplication()).besinDAO().getAllBesin()
            besinleriGoster(besinListesi)

            Toast.makeText(getApplication(),"Besinleri SQLite(Room) den aldik",Toast.LENGTH_LONG).show()
        }


    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value= true
        disposable.add(
            besinApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        //basarili olursa ne yapilacak
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Besinleri Internetten den aldik",Toast.LENGTH_LONG).show()


                    }

                    override fun onError(e: Throwable) {
                        //bir hata olusursa ne olacak
                        besinYukleniyor.value= false
                        besinHataMesaji.value=true
                        e.printStackTrace()
                    }

                })//subscribeWith son
        )//disposqable.add son
    }//verileri internetten al son

    private fun besinleriGoster(besinlerListesi : List<Besin>){

        //Verileri gerektiginde internetten gerektiginde de sqlite en alabilmek icin
        //bu islemleri ayri bir fonksiyonda tanimliyoruz

        besinler.value =besinlerListesi
        besinYukleniyor.value= false
        besinHataMesaji.value=false

    }
    private fun sqliteSakla(besinListesi : List<Besin>){

        launch {
            //burda bütün islemleri burda yapabiliriz
            val dao = BesinDatabase(getApplication()).besinDAO()
            //bu besinDtabase icerinde olusturdugumuz besinDao fonksiyonu

            //simdi dao. yazinca BesinDatabase sinifinda yazdigimiz fonksiyonlara ulasabiliyoruz

            //sonra sqlite de olan eski
            // veri var ise onlari temizliyoruz
            dao.deleteAllBesin()

            //sonra liste halinde verilen verileri tek tek almak icin
            val uuidListesi= dao.insertAll(*besinListesi.toTypedArray())
            // * liste icerisinde ki verileri tek tek almamiza yariyor

            var i = 0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i+1

                besinleriGoster(besinListesi)

            }//while son
            ozelSharedPreferences.zamaniKaydet(System.nanoTime())
        }//lounch son
    }//Sqlite son
}//BesinListesiViewModel son
