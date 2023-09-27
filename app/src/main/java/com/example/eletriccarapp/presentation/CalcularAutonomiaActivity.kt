package com.example.eletriccarapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccarapp.R
import com.example.eletriccarapp.databinding.ActivityCalcularAutonomiaBinding


class CalcularAutonomiaActivity : AppCompatActivity() {
    //lateinit var binding: ActivityCalcularAutonomiaBinding
    lateinit var resultado: TextView
    lateinit var preco: EditText
    lateinit var kmPercorrido: EditText
    lateinit var btnCalcular: Button
    lateinit var btnClose: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityCalcularAutonomiaBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_calcular_autonomia)

        //binding.btnCalculo.setOnClickListener(this)
        //binding.imgClose.setOnClickListener(this)
        setupView()
        setupListeners()
        setupCachedResult()
    }

    fun setupView(){
        preco = findViewById(R.id.pricekwh)
        kmPercorrido = findViewById(R.id.totalKM)
        resultado = findViewById(R.id.resultado)
        btnCalcular = findViewById(R.id.btnCalculo)
        btnClose = findViewById(R.id.img_close)
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener{
            calcularAutonomia()
        }
        btnClose.setOnClickListener{
            finish()
        }
    }

    private fun setupCachedResult() {
        val valorCalculado = getSharedPreferences()
        resultado.text = valorCalculado.toString()
    }


    /*override fun onClick(view: View) {
        if (view.id == R.id.btnCalculo){
            calcularAutonomia()
            //startActivity(Intent(this, MainActivity::class.java))
        }
        if (view.id == R.id.img_close){
            //calcularAutonomia()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }*/

    fun calcularAutonomia() {
        val preco = preco.text.toString().toFloat()
        val km = kmPercorrido.text.toString().toFloat()
        val result = preco / km

        resultado.text = result.toString()
        Toast.makeText(this,"Calculado", Toast.LENGTH_LONG).show()
        saveSharedPreferences(result)
    }

    //Salvar os calculos no sqLite
    fun saveSharedPreferences(resultado: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), resultado)
            apply()
        }
    }

    fun getSharedPreferences(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }
}