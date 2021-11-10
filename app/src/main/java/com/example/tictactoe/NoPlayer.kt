package com.example.tictactoe

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlin.random.Random

class NoPlayer : AppCompatActivity() {

    private var player = 1
    private var playerOneScore = 0
    private var playerTwoScore = 0
    private lateinit var scoreTv: TextView
    private lateinit var playerTV: TextView
    private lateinit var topLeft: Button
    private lateinit var topMiddle: Button
    private lateinit var topRight: Button
    private lateinit var middleLeft: Button
    private lateinit var middleMiddle: Button
    private lateinit var middleRight: Button
    private lateinit var bottomLeft: Button
    private lateinit var bottomMiddle: Button
    private lateinit var bottomRight: Button
    private lateinit var buttons: ArrayList<ArrayList<Button>>
    private lateinit var dialog: Dialog
    private lateinit var nameAnimation: Animation
    private lateinit var viewKonfetti: KonfettiView
    private lateinit var viewKonfetti2: KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_player_game)

        scoreTv = findViewById(R.id.scoreTV)
        playerTV = findViewById(R.id.playerTV)
        topLeft = findViewById(R.id.topLeft)
        topMiddle = findViewById(R.id.topMiddle)
        topRight = findViewById(R.id.topRight)
        middleLeft = findViewById(R.id.middleLeft)
        middleMiddle = findViewById(R.id.middleMiddle)
        middleRight = findViewById(R.id.middleRight)
        bottomLeft = findViewById(R.id.bottomLeft)
        bottomMiddle = findViewById(R.id.bottomMiddle)
        bottomRight = findViewById(R.id.bottomRight)
        dialog = Dialog(this)
        nameAnimation = AnimationUtils.loadAnimation(this, R.anim.winner_animation)
        viewKonfetti2= findViewById(R.id.viewKonfetti)

        if (savedInstanceState != null) {
            playerOneScore = savedInstanceState.getInt("playerOneScore1", 0)
            playerTwoScore = savedInstanceState.getInt("playerTwoScore1", 0)
        }

        scoreTv.text = "Score\n$playerOneScore : $playerTwoScore"
        playerTV.text = "You Play"
        playerTV.setTextColor(Color.RED)

        buttons = arrayListOf(
            arrayListOf(topLeft, topMiddle, topRight),
            arrayListOf(middleLeft, middleMiddle, middleRight),
            arrayListOf(bottomLeft, bottomMiddle, bottomRight)
        )

        for (list in buttons)
            for (button in list)
                onClickListener(button)
    }

    private fun onClickListener(button: Button) {
        button.setOnClickListener {
            button.isClickable = false
            button.isEnabled = false
            if (player == 1) {
                button.text = "X"
                button.setTextColor(Color.RED)
                if (win()) {
                    playerOneScore++
                    endGame("You")
                } else if (playerTV.text != "Draw!!") {
                    playerTV.text = "Computer Play"
                    playerTV.setTextColor(Color.GREEN)
                    player = 2
                    val handler = Handler()
                    handler.postDelayed({
                        computerTurn("O").performClick()
                    }, 1000)

                }
            } else {
                button.text = "O"
                button.setTextColor(Color.GREEN)
                if (win()) {
                    playerTwoScore++
                    endGame("Computer")
                } else if (playerTV.text != "Draw!!") {
                    playerTV.text = "Computer Play"
                    playerTV.setTextColor(Color.RED)
                    player = 1
                    val handler = Handler()
                    handler.postDelayed({
                        computerTurn("X").performClick()
                    }, 1000)
                }
            }
            playerTV.startAnimation(nameAnimation)
        }
    }

    private fun computerTurn(s:String) : Button {
        for (i in 0 until buttons.size) {
            //Looking for win
            if (buttons[i][0].text == buttons[i][1].text && buttons[i][1].text== "$s" &&  buttons[i][2].text.isBlank())
                return buttons[i][2]
            if (buttons[i][1].text == buttons[i][2].text && buttons[i][2].text== "$s" && buttons[i][0].text.isBlank())
                return buttons[i][0]
            if (buttons[i][0].text == buttons[i][2].text && buttons[i][2].text== "$s" && buttons[i][1].text.isBlank())
                return buttons[i][1]
            if (buttons[0][i].text == buttons[1][i].text && buttons[1][i].text== "$s" && buttons[2][i].text.isBlank())
                return buttons[2][i]
            if (buttons[1][i].text == buttons[2][i].text && buttons[2][i].text== "$s" && buttons[0][i].text.isBlank())
                return buttons[0][i]
            if (buttons[0][i].text == buttons[2][i].text && buttons[2][i].text== "$s" && buttons[1][i].text.isBlank())
                return buttons[1][i]
        }
        if (buttons[0][0].text == buttons[1][1].text && buttons[1][1].text== "$s" && buttons[2][2].text.isBlank())
            return buttons[2][2]
        if (buttons[0][0].text == buttons[2][2].text && buttons[2][2].text== "$s" && buttons[1][1].text.isBlank())
            return buttons[1][1]
        if (buttons[2][2].text == buttons[1][1].text && buttons[1][1].text== "$s" && buttons[0][0].text.isBlank())
            return buttons[0][0]
        if (buttons[0][2].text == buttons[1][1].text && buttons[1][1].text== "$s" && buttons[2][0].text.isBlank())
            return buttons[2][0]
        if (buttons[0][2].text == buttons[2][0].text && buttons[2][0].text== "$s" && buttons[1][1].text.isBlank())
            return buttons[1][1]
        if (buttons[2][0].text == buttons[1][1].text && buttons[1][1].text== "$s" && buttons[0][2].text.isBlank())
            return buttons[0][2]
        //Want to Stop Me
        for (i in 0 until buttons.size){
            if (buttons[i][0].text == buttons[i][1].text && buttons[i][1].text.isNotBlank() &&  buttons[i][2].text.isBlank())
                return buttons[i][2]
            if (buttons[i][1].text == buttons[i][2].text && buttons[i][2].text.isNotBlank() && buttons[i][0].text.isBlank())
                return buttons[i][0]
            if (buttons[i][0].text == buttons[i][2].text && buttons[i][2].text.isNotBlank() && buttons[i][1].text.isBlank())
                return buttons[i][1]
            if (buttons[0][i].text == buttons[1][i].text && buttons[1][i].text.isNotBlank() && buttons[2][i].text.isBlank())
                return buttons[2][i]
            if (buttons[1][i].text == buttons[2][i].text && buttons[2][i].text.isNotBlank() && buttons[0][i].text.isBlank())
                return buttons[0][i]
            if (buttons[0][i].text == buttons[2][i].text && buttons[2][i].text.isNotBlank() && buttons[1][i].text.isBlank())
                return buttons[1][i]
        }
        if (buttons[0][0].text == buttons[1][1].text && buttons[1][1].text.isNotBlank() && buttons[2][2].text.isBlank())
            return buttons[2][2]
        if (buttons[0][0].text == buttons[2][2].text && buttons[2][2].text.isNotBlank() && buttons[1][1].text.isBlank())
            return buttons[1][1]
        if (buttons[2][2].text == buttons[1][1].text && buttons[1][1].text.isNotBlank() && buttons[0][0].text.isBlank())
            return buttons[0][0]
        if (buttons[0][2].text == buttons[1][1].text && buttons[1][1].text.isNotBlank() && buttons[2][0].text.isBlank())
            return buttons[2][0]
        if (buttons[0][2].text == buttons[2][0].text && buttons[2][0].text.isNotBlank() && buttons[1][1].text.isBlank())
            return buttons[1][1]
        if (buttons[2][0].text == buttons[1][1].text && buttons[1][1].text.isNotBlank() && buttons[0][2].text.isBlank())
            return buttons[0][2]
        //Play Anything
        return randomChoice()
    }

    private fun randomChoice(): Button {
        val x= Random.nextInt(0,buttons.size)
        val y= Random.nextInt(0,buttons.size)
        if (buttons[x][y].text.isBlank())
            return buttons[x][y]
        return randomChoice()
    }

    private fun endGame(name: String) {
        scoreTv.text = "Score\n$playerOneScore : $playerTwoScore"
        when (name) {
            "Draw" -> {
                playerTV.text = "$name!!"
                playerTV.setTextColor(Color.MAGENTA)
                playerTV.startAnimation(nameAnimation)
                dialog.setContentView(R.layout.draw_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val winnerTV = dialog.findViewById<TextView>(R.id.winnerTV)
                val playAgainButton = dialog.findViewById<Button>(R.id.playAgainButton)
                val backButton = dialog.findViewById<Button>(R.id.backButton)
                winnerTV.text = "$name!!"
                playAgainButton.setOnClickListener {
                    recreate()
                }
                backButton.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("status", 1)
                    startActivity(intent)
                }
                dialog.setCancelable(false)
                dialog.show()
            }
            "You" -> {
                playerTV.text = "$name Win!!"
                playerTV.startAnimation(nameAnimation)
                dialog.setContentView(R.layout.win_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                viewKonfetti= dialog.findViewById(R.id.viewKonfetti)
                party()
                val winnerTV = dialog.findViewById<TextView>(R.id.winnerTV)
                val playAgainButton = dialog.findViewById<Button>(R.id.playAgainButton)
                val backButton = dialog.findViewById<Button>(R.id.backButton)
                winnerTV.text = "$name Win!!"
                playAgainButton.setOnClickListener {
                    recreate()
                }
                backButton.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("status", 1)
                    startActivity(intent)
                }
                dialog.setCancelable(false)
                dialog.show()
            }
            else -> {
                playerTV.text = "$name Win!!"
                playerTV.startAnimation(nameAnimation)
                dialog.setContentView(R.layout.lose_dialog)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                viewKonfetti= dialog.findViewById(R.id.viewKonfetti)
                loseParty()
                val playAgainButton = dialog.findViewById<Button>(R.id.playAgainButton)
                val backButton = dialog.findViewById<Button>(R.id.backButton)
                playAgainButton.setOnClickListener {
                    recreate()
                }
                backButton.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("status", 1)
                    startActivity(intent)
                }
                dialog.setCancelable(false)
                dialog.show()
            }
        }
    }

    private fun win(): Boolean {
        for (i in 0 until buttons.size) {
            if (buttons[i][0].text == buttons[i][1].text && buttons[i][1].text == buttons[i][2].text && buttons[i][0].text.isNotBlank())
                return true
            if (buttons[0][i].text == buttons[1][i].text && buttons[1][i].text == buttons[2][i].text && buttons[0][i].text.isNotBlank())
                return true
        }
        if (buttons[0][0].text == buttons[1][1].text && buttons[1][1].text == buttons[2][2].text && buttons[0][0].text.isNotBlank())
            return true
        if (buttons[0][2].text == buttons[1][1].text && buttons[1][1].text == buttons[2][0].text && buttons[1][1].text.isNotBlank())
            return true
        var draw = true
        for (list in buttons)
            for (bt in list)
                if (bt.isClickable)
                    draw = false
        if (draw)
            endGame("Draw")
        return false
    }

    private fun party(){
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.WHITE)
            .setDirection(0.0, 600.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Circle, Shape.Square)
            .addSizes(Size(10))
            .setPosition(0f, 0f, 0f, 0f)
            .streamFor(600, 10000L)
        viewKonfetti2.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    private fun loseParty(){
        viewKonfetti.build()
            .addColors(Color.BLACK)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("playerOneScore1", playerOneScore)
        outState.putInt("playerTwoScore1", playerTwoScore)
    }
}