package com.example.eletriccarapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.databinding.ActivityMainBinding
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.example.eletriccarapp.presentation.data.CarFactory

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var binding: ActivityMainBinding
    lateinit var lista: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalc.setOnClickListener(this)
        setupListView()
        myList()
    }

    fun setupListView(){
        lista = binding.rvInformations
    }

    fun myList(){
        //val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, data)
        //lista.adapter = adapter
        var adapter = CarAdapter(CarFactory.list)
        lista.adapter = adapter
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnCalc){
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }


}