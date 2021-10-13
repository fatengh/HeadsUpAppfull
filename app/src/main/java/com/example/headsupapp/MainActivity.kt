package com.example.headsupapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Surface
import android.widget.*
import androidx.core.view.isVisible
import kotlin.collections.ArrayList
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var dbHlpr: DBHlpr
    private lateinit var llTime: LinearLayout
    private lateinit var llStart: LinearLayout
    private lateinit var llCeleb: LinearLayout
    private lateinit var tvTime: TextView
    private lateinit var tvName: TextView
    private lateinit var tvT1: TextView
    private lateinit var tvT2: TextView
    private lateinit var tvT3: TextView
    private lateinit var tvHead: TextView
    private lateinit var btnStart: Button
    private lateinit var btnAdd: Button
    private  var list=ArrayList<Celebrity>()

    private var Activeplay = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llTime = findViewById(R.id.llTime)
        llStart = findViewById(R.id.llStart)
        llCeleb = findViewById(R.id.llCeleb)
        tvTime = findViewById(R.id.tvTime)
        tvName = findViewById(R.id.tvName)
        tvT1 = findViewById(R.id.tvT1)
        tvT2 = findViewById(R.id.tvT2)
        tvT3 = findViewById(R.id.tvT3)
        tvHead = findViewById(R.id.tvHead)
        btnStart = findViewById(R.id.btnStart)
        btnAdd = findViewById(R.id.btnAdd)
        dbHlpr=DBHlpr(applicationContext)
        list = dbHlpr.retrive()
        dbHlpr= DBHlpr(applicationContext)
        btnStart.setOnClickListener {
            newTimer()

        }
        btnAdd.setOnClickListener {
            intent = Intent(applicationContext, NewCelebrity::class.java)
            startActivity(intent)

        }


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val rotation = windowManager.defaultDisplay.rotation
        if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
            if(Activeplay){
                fetchData()
                updateStatus(false)
            }else{
                updateStatus(false)
            }
        }else{
            if(Activeplay){
                updateStatus(true)
            }else{
                updateStatus(false)
            }
        }
    }

    private fun newTimer(){
        if(!Activeplay){
            Activeplay = true
            tvHead.text = "Please Rotate Device"
            btnStart.isVisible = false
            val rotation = this.windowManager.defaultDisplay.rotation
            if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                updateStatus(false)
            }else{
                updateStatus(true)
            }

            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = "Time: ${millisUntilFinished / 1000}"
                }

                override fun onFinish() {
                    Activeplay = false
                    tvTime.text = "Time: --"
                    tvHead.text = "Heads Up!"
                    btnStart.isVisible = true
                    updateStatus(false)
                }
            }.start()
        }
    }


    private fun fetchData() {

        var r = Random.nextInt(list.size)
        tvName.text = list[r].name
        tvT1.text =  list[r].taboo1
        tvT2.text = list[r].taboo2
        tvT3.text = list[r].taboo3
    }


    private fun updateStatus(showCelebrity: Boolean){
        if(showCelebrity){
            llCeleb.isVisible = true
            llStart.isVisible = false
        }else{
            llCeleb.isVisible = false
            llStart.isVisible = true
        }
    }
}