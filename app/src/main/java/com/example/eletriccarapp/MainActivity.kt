package com.example.eletriccarapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.eletriccarapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalc.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnCalc){
            calcularAutonomia()
        }
    }

    fun calcularAutonomia() {
        var preco = binding.pricekwh.text.toString().toFloat()
        var kmPercorrido = binding.totalKM.text.toString().toFloat()
        var resultado = preco / kmPercorrido

        binding.result.text = resultado.toString()
        Toast.makeText(this,"Calculado", Toast.LENGTH_LONG).show()
    }
}