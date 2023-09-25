package com.example.eletriccarapp.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.presentation.CalcularAutonomiaActivity
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.example.eletriccarapp.presentation.data.CarFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarrosFragment : Fragment() {
    lateinit var btnCalcular: FloatingActionButton
    lateinit var listaCarros: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carros, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
        setupListeners()
    }

    fun setupView(view: View) {
        view.apply {
            btnCalcular = view.findViewById(R.id.fab_calcular)
            listaCarros = view.findViewById(R.id.rv_informations)
        }

    }

    fun setupList(){
        val adapter = CarAdapter(CarFactory.list)
        listaCarros.adapter = adapter
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

}