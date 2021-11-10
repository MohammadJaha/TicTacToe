package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private var status = 0
    private lateinit var rotateAnimation: Animation
    private lateinit var backgroundImage : ImageView
    private lateinit var welcomeLay : LinearLayout
    private lateinit var loadingImage : ImageView
    private lateinit var handler : Handler
    private var check = true
    private lateinit var mainLay: ConstraintLayout
    private lateinit var oneModeButton: Button
    private lateinit var twoModeButton: Button
    private lateinit var noPlayerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras?.isEmpty == false) {
            status = intent.extras!!.getInt("status")
        }

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animmation)
        backgroundImage = findViewById(R.id.backgroundImage)
        loadingImage = findViewById(R.id.loadingImage)
        welcomeLay = findViewById(R.id.welcomeLay)
        mainLay = findViewById(R.id.mainLay)
        oneModeButton = findViewById(R.id.oneModeButton)
        twoModeButton = findViewById(R.id.twoModeButton)
        noPlayerButton= findViewById(R.id.noPlayerButton)

        handler = Handler()
        if (status == 0){
            handler.postDelayed({
                startingView()
            }, 1000)
            connect()
        }
        else{
            changeView()
        }
        oneModeButton.setOnClickListener{
            redirect(1)
        }
        twoModeButton.setOnClickListener{
            redirect(2)
        }
        noPlayerButton.setOnClickListener{
            redirect(0)
        }

    }

    private fun startingView(){
        loadingImage.startAnimation(rotateAnimation)
        if (check)
            handler.postDelayed({
                startingView()
            }, 3000)
        check = true
    }

    private fun connect(){
        handler.postDelayed({
            check = false
        }, 10000)
        handler.postDelayed({
            changeView()
        }, 12000)
    }

    private fun changeView() {
        welcomeLay.isVisible = false
        mainLay.isVisible = true
    }

    private fun redirect(choice: Int) {
        when (choice) {
            1 -> startActivity(Intent(this,OnePlayerGame::class.java))
            2 -> startActivity(Intent(this,TwoPlayerGame::class.java))
            else -> startActivity(Intent(this,NoPlayer::class.java))
        }
    }

}