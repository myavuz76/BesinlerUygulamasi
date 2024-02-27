package com.example.besinleruygulamasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinleruygulamasi.model.Besin
import com.example.besinleruygulamasi.R
import com.example.besinleruygulamasi.databinding.BesinRecyclerRowBinding
import com.example.besinleruygulamasi.util.gorselIndir
import com.example.besinleruygulamasi.util.placeholderYap

import com.example.besinleruygulamasi.view.BesinListesiDirections

class besinRecyclerAdapter (val besinListesi : ArrayList<Besin>): RecyclerView.Adapter<besinRecyclerAdapter.BesinViewHolder>(), BesinClickListener {

    class BesinViewHolder(var view:BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater= LayoutInflater.from(parent.context)
         val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)

        return BesinViewHolder(view)


    }

    override fun getItemCount(): Int {
        return besinListesi.size

    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
      /*  //val besinGorsel = holder.itemView.findViewById<ImageView>(R.id.besinIsmiGorselRow)
        val besinIsmi : TextView = holder.itemView.findViewById(R.id.besinIsmiTextView)
        val besinKalori : TextView = holder.itemView.findViewById(R.id.besinKaloriTextView)

        besinIsmi.text = besinListesi.get(position).besinIsmi
        besinKalori.text = besinListesi.get(position).besinKalori



        holder.itemView.setOnClickListener {
            val action =BesinListesiDirections.actionBesinListesiToBesinDetayiFragment(0)
        }
        */
        //holder.itemView.findViewById<ImageView>(R.id.besinListGorseli).gorselIndir(besinListesi.get(position).besinGorsel, placeholderYap(holder.itemView.context))

        holder.view.besin = besinListesi[position]
        holder.view.listener = this


    }

    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }

    override fun besinTikla(view: View) {

        val uuid = view.findViewById<TextView>(R.id.besinUuId).text.toString().toIntOrNull()

        uuid?.let {
            val action = BesinListesiDirections.actionBesinListesiToBesinDetayi(it)
            Navigation.findNavController(view).navigate(action)
            //Toast.makeText(view.context,uuid.toString(),Toast.LENGTH_LONG).show()
        }






    }
}
