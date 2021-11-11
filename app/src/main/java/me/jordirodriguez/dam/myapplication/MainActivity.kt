package me.jordirodriguez.dam.myapplication

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val INITIAL_TIME = 6

    private val TAG = MainActivity::class.java.simpleName

    internal var counter = 0
    internal var time =  INITIAL_TIME
    internal lateinit var tapMeButton: Button
    internal lateinit var timeTextView: TextView
    internal lateinit var counterTextView: TextView

    internal var appStarted = false
    internal lateinit var countdownTimer : CountDownTimer
//    internal val initialCountDownTimer : Long = 60000
    internal val initialCountDownTimer : Long = time.toLong()*10000
    internal val intervalCountDownTimer : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCountdown()

        tapMeButton = findViewById(R.id.tapMeButton)
        timeTextView = findViewById(R.id.timeTextView)
        counterTextView = findViewById(R.id.counterTextView)

        tapMeButton.setOnClickListener {view -> incrementCounter()
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            if (!appStarted){
                startGame()
            }
            //incrementCounter()
        }
        timeTextView.text= getString(R.string.timeText, time)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionAbout) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.aboutTitle)
        val dialogMessage = getString(R.string.aboutMessage)
    }

    private fun startGame() {
        countdownTimer.start()
        appStarted=true
    }

    private fun initCountdown () {
        countdownTimer= object : CountDownTimer(initialCountDownTimer, intervalCountDownTimer){
        override fun onTick(millisUntilFinished: Long) {
            val timeLeft = millisUntilFinished/1000
            timeTextView.text = timeLeft.toString()}

            override fun onFinish() {
               endGame()
            }

        }
    }
    private fun incrementCounter() {
        counter += 1
        counterTextView.text = counter.toString()
    }
    private fun endGame() {
        Toast.makeText(this, getString(R.string.endGame), Toast.LENGTH_LONG).show()
        resetGame()
    }
    private fun resetGame(){
        counter = 0
        counterTextView = findViewById(R.id.counterTextView)

        time = INITIAL_TIME
        timeTextView.text = time.toString()
        initCountdown()

        appStarted = false


    }

}
















