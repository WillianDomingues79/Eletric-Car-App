package com.example.eletriccarapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccarapp.R
import com.example.eletriccarapp.databinding.ActivityCalcularAutonomiaBinding


class CalcularAutonomiaActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityCalcularAutonomiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalcularAutonomiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculo.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnCalculo){
            calcularAutonomia()
            //startActivity(Intent(this, MainActivity::class.java))
        }
        if (view.id == R.id.img_close){
            //calcularAutonomia()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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