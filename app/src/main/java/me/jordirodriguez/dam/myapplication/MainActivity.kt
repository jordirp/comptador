package me.jordirodriguez.dam.myapplication

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {


    internal var counter = 0
    internal var appStarted = false
    internal lateinit var countdownTimer : CountDownTimer
    internal val initialCountDownTimer : Long = 60000
    internal val intervalCountDownTimer : Long = 1000
    internal lateinit var tapMeButton: Button
    internal lateinit var timeTextView: TextView
    internal lateinit var counterTextView: TextView
    private var timeLeftOnTimer: Long = 60000

    // internal val initialCountDownTimer : Long = time.toLong()*10000

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val KEY_SCORE = "KEY_SCORE"
        private const val KEY_TIME_LEFT = "KEY_TIME_LEFT"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called. Score is $counter")

        tapMeButton = findViewById(R.id.tapMeButton)
        timeTextView = findViewById(R.id.timeTextView)
        counterTextView = findViewById(R.id.counterTextView)

        tapMeButton.setOnClickListener { view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementCounter()
        }
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(KEY_SCORE)
            timeLeftOnTimer = savedInstanceState.getLong(KEY_TIME_LEFT)
            restoreGame()
        } else {
            resetGame()
        }
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
        val dialogTitle = getString(R.string.aboutTitle, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.aboutMessage)

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle).setMessage(dialogMessage).create().show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_SCORE, counter)
        outState.putLong(KEY_TIME_LEFT, timeLeftOnTimer)
        countdownTimer.cancel()

        Log.d(TAG, "Saving Score: $counter & Times Left: $timeLeftOnTimer")
        super.onSaveInstanceState(outState)
    }


    private fun startGame() {
        countdownTimer.start()
        appStarted=true
    }

    private fun incrementCounter() {
        if (!appStarted) {
            startGame()
        }

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)

        counter += 1

        val newScore = getString(R.string.yourScore, counter)
        counterTextView.text = newScore
        counterTextView.startAnimation(blinkAnimation)
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.endGame, counter), Toast.LENGTH_LONG).show()
        resetGame()
    }


    private fun resetGame() {
        counter = 0

        counterTextView.text = getString(R.string.yourScore, counter)

        val initialTimeLeft = initialCountDownTimer / 1000
        timeTextView.text = getString(R.string.timeText, initialTimeLeft)

        countdownTimer = object : CountDownTimer(initialCountDownTimer, intervalCountDownTimer) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished

                val timeLeft = millisUntilFinished / 1000
                timeTextView.text = getString(R.string.timeText, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }

        appStarted = false
    }

    private fun restoreGame() {
        counterTextView.text = getString(R.string.yourScore, counter)
        val restoredTime = timeLeftOnTimer / 1000
        timeTextView.text = getString(R.string.timeText, restoredTime)

        countdownTimer = object : CountDownTimer(timeLeftOnTimer, intervalCountDownTimer) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished

                val timeLeft = millisUntilFinished / 1000
                timeTextView.text = getString(R.string.timeText, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
    }
}
















