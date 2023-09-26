package com.example.eletriccarapp.presentation.fragment

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.presentation.CalcularAutonomiaActivity
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.example.eletriccarapp.presentation.data.CarFactory
import com.example.eletriccarapp.presentation.domain.Car
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class CarrosFragment : Fragment() {
    lateinit var btnCalcular: FloatingActionButton
    lateinit var listaCarros: RecyclerView

    var carrosArray : ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carros, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callService()
        setupView(view)
        setupListeners()
    }

    fun setupView(view: View) {
        view.apply {
            btnCalcular = view.findViewById(R.id.fab_calcular)
            listaCarros = view.findViewById(R.id.rv_informations)
        }

    }

    fun setupList(){
        val adapter = CarAdapter(carrosArray)
        listaCarros.adapter = adapter
    }

    fun setupListeners(){
        btnCalcular.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    fun callService(){
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        MyTask().execute(urlBase)
    }

    //Consumo API
    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("MyTask", "Iniciando...")
        }
        override fun doInBackground(vararg url: String?): String {
            var urlCollection: HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlCollection = urlBase.openConnection() as HttpURLConnection
                urlCollection.connectTimeout = 60000
                urlCollection.readTimeout = 60000
                urlCollection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlCollection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlCollection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                }else {
                    Log.e("Erro", "Serviço Indisponivel no momento")
                }

                var response = urlCollection.inputStream.bufferedReader().use { it.readText() }
                publishProgress(response)
            } catch (ex: Exception) {
                Log.e("Erro", "Erro ao parcelar Stream")
            }finally {
                urlCollection?.disconnect()
            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for ( i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val preco = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("preco ->", preco)

                    val bateria = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("bateria ->", bateria)

                    val potencia = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("potencia ->", potencia)

                    val recarga = jsonArray.getJSONObject(i).getString("recarga")
                    Log.d("recarga ->", recarga)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("urlPhoto ->", urlPhoto)

                    val model = Car(
                        id = id.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto
                    )
                    carrosArray.add(model)
                    Log.d("Model ->", model.toString())
                }
                setupList()
            }catch (ex: Exception){
                Log.e("Erro", "Erro ao recuper API")
            }
        }



    }
}