package com.example.eletriccarapp.presentation.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.presentation.CalcularAutonomiaActivity
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.example.eletriccarapp.presentation.data.CarFactory
import com.example.eletriccarapp.presentation.domain.Car
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.newFixedThreadPoolContext
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
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView

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
        setupView(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(context)){
            callService()
        }else {
            emptyState()
        }
    }

    fun emptyState(){
        progress.visibility = View.GONE
        listaCarros.visibility = View.GONE
        noInternetImage.visibility = View.VISIBLE
        noInternetText.visibility = View.VISIBLE
    }

    fun setupView(view: View) {
        view.apply {
            btnCalcular = view.findViewById(R.id.fab_calcular)
            listaCarros = view.findViewById(R.id.rv_informations)
            progress = findViewById(R.id.progressBar)
            noInternetImage = findViewById(R.id.iv_empty_state)
            noInternetText = findViewById(R.id.tv_no_wifi)
        }

    }

    fun setupList(){
        val carroAdapter = CarAdapter(carrosArray)
        listaCarros.apply {
            visibility = View.VISIBLE
            adapter = carroAdapter
        }
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

    fun checkForInternet(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            val  activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    //Consumo API
    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("MyTask", "Iniciando...")
            progress.visibility = View.VISIBLE
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
                progress.visibility = View.GONE
                noInternetImage.visibility = View.GONE
                noInternetText.visibility = View.GONE
                setupList()
            }catch (ex: Exception){
                Log.e("Erro", "Erro ao recuper API")
            }
        }



    }
}