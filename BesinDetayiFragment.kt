package com.example.besinleruygulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.besinleruygulamasi.R
import com.example.besinleruygulamasi.databinding.FragmentBesinDetayiBinding
import com.example.besinleruygulamasi.util.gorselIndir
import com.example.besinleruygulamasi.util.placeholderYap
import com.example.besinleruygulamasi.viewModel.BesinDetayiViewModel

class BesinDetayi : Fragment() {
    private lateinit var binding: FragmentBesinDetayiBinding
    private lateinit var viewModel: BesinDetayiViewModel
    var besinId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return binding.root
    }override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //argumanlari alabilmek icin arguments?.let yapisini kullniyoruz
        arguments?.let {
            //eger bir argument geldi ise bun bize bir bohca(Bundle) olarak veriliyor

            besinId = BesinDetayiArgs.fromBundle(it).besinId

            // Toast.makeText(context,"BesinId = ${besinId}",Toast.LENGTH_LONG).show()

        }


        viewModel= ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)
        observeLiveData()

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData(){
        //gözlemlenebilir veri sinifi
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin->
            besin?.let {
                binding.secilenBesin =it


                /*
                binding.besinIsmi.text = besin.besinIsmi
                binding.besinKalori.text= besin.besinKalori
                binding.besinKarbonhidrat.text= besin.besinKarbonhidrat
                binding.besinProtein.text= besin.besinProtein
                binding.besinYag.text=besin.besinYag
                context?.let {
                    binding.BesinDetayGorseli.gorselIndir(besin.besinGorsel, placeholderYap(it))

                 */
                }
        })

    }
}
