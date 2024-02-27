package com.example.besinleruygulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinleruygulamasi.databinding.FragmentBesinListesiBinding
import com.example.besinleruygulamasi.adapter.besinRecyclerAdapter
import com.example.besinleruygulamasi.viewModel.BesinListesiViewModel

class BesinListesi: Fragment() {
    private lateinit var binding: FragmentBesinListesiBinding
    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerAdapter = besinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBesinListesiBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //burda fragment ile ViewModel sinifimizi bagliyoruz
        viewModel =ViewModelProvider(this).get(BesinListesiViewModel::class.java)

        viewModel.refreshData()

        binding.besinlerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.besinlerRecyclerView.adapter = recyclerAdapter

        binding.swiperefreshlayout.setOnRefreshListener {

            binding.besinlerRecyclerView.visibility= View.GONE
            binding.besinHataMesaji.visibility = View.GONE
            binding.ProgressBarBesinYukleniyor.visibility =View.VISIBLE
            viewModel.refreshFrominternet()

            binding.swiperefreshlayout.isRefreshing= false

        }


        observeLiveData()


    }

    fun observeLiveData(){
        //observeLiveData demek lifeData yi gözlemle
        // owner yasam döngüsünün sahibi kim
        //nullabel ile calisirsak it.let

        viewModel.besinler.observe(viewLifecycleOwner, Observer {
            it?.let {
                //burda besinListReycle yi de görünür hale getirmemiz gerekiyor
                binding.besinlerRecyclerView.visibility = View.VISIBLE
                //gözlemledigimiz observe bize besinleri(it) vercek bizde onu recycleViewe verecegiz
                recyclerAdapter.besinListesiniGuncelle(it)
            }
        })

        //Burda da degisik this yerine baska kullanimini gösterdik

        viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if(it){
                    binding.besinHataMesaji.visibility = View.VISIBLE
                    binding.besinlerRecyclerView.visibility = View.GONE
                }else{
                    binding.besinHataMesaji.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer {yukleniyor ->
            yukleniyor?.let {
                if (it){
                    binding.besinlerRecyclerView.visibility = View.GONE
                    binding.besinHataMesaji.visibility = View.GONE
                    binding.ProgressBarBesinYukleniyor.visibility = View.VISIBLE
                }else{
                    binding.ProgressBarBesinYukleniyor.visibility = View.GONE
                }
            }

        })

        //simdi bu fonksiyonu onViewCreated fonksiyonu icerisinde cagirmamiz lazim
    }
}
