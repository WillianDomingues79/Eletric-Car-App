package com.example.eletriccarapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eletriccarapp.R

import com.example.eletriccarapp.databinding.ActivityMainBinding
import com.example.eletriccarapp.presentation.adapter.CarAdapter
import com.example.eletriccarapp.presentation.data.CarFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.example.eletriccarapp.presentation.fragment.viewPageAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(){
    //lateinit var binding: ActivityMainBinding
    //lateinit var lista: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        //binding.btnCalc.setOnClickListener(this)

        //setupListView()
        //myList()
        setupTabs()

    }


    fun setupTabs(){
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter = viewPageAdapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Carros"
                }
                1->{
                    tab.text="Favoritos"
                }
            }
        }.attach()

    }
}




/*fun setupListView(){
        lista = binding.rvInformations
    }*/

/*fun myList(){
    //val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, data)
    //lista.adapter = adapter
    var adapter = CarAdapter(CarFactory.list)
    lista.adapter = adapter
}*/

/*tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager2.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })*/

/*override fun onClick(view: View) {
        if (view.id == R.id.btnCalc){
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }*/