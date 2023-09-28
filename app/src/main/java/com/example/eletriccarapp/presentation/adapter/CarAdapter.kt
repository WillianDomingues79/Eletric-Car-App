package com.example.eletriccarapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.presentation.domain.Car

class CarAdapter(private val carros: List<Car>, private val isFavoriteScreen : Boolean = false) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    var carItemLister : (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.potencia.text = carros[position].potencia
        holder.recarga.text = carros[position].recarga
        if (isFavoriteScreen) {
            holder.favorito.setImageResource(R.drawable.ic_star_selected)
        }
        holder.favorito.setOnClickListener{
            val carro = carros[position]
            carItemLister(carro)
            setupFavorite(carro, holder)
        }
    }

    private fun setupFavorite(carro: Car, holder: ViewHolder){
        carro.isFavorite = !carro.isFavorite
        if (carro.isFavorite){
            holder.favorito.setImageResource(R.drawable.ic_star_selected)
        }else{
            holder.favorito.setImageResource(R.drawable.ic_star)
        }
    }

    override fun getItemCount(): Int = carros.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val preco: TextView
        val bateria: TextView
        val potencia: TextView
        val recarga: TextView
        val favorito: ImageView

        init {
            preco = view.findViewById(R.id.text_cost)
            bateria = view.findViewById(R.id.text_kwh)
            potencia = view.findViewById(R.id.text_cv)
            recarga = view.findViewById(R.id.text_min)
            favorito = view.findViewById(R.id.iv_favorite)
        }
    }
}

