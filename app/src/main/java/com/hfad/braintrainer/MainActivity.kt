package com.hfad.braintrainer

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import android.os.CountDownTimer



class MainActivity : AppCompatActivity() {

    private var randomNumberOne: Int? = null
    private var randomNumberTwo: Int? = null
    private var question: String? = null
    private var locationOfCorrectAnswer: Int? = null
    private var answers = ArrayList<Int>()
    private var correctAnswers = 0
    private var numberOfQuestions = 0
    private val random = Random()
    private var score : String = ""
    private var done = MediaPlayer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        done = MediaPlayer.create(this , R.raw.airhorn)
    }

    fun onGo(view: View) {
        btn_go.visibility = View.INVISIBLE
        tv_timer.visibility = View.VISIBLE
        tv_score.visibility = View.VISIBLE
        gridLayout2.visibility = View.VISIBLE
        tv_question.visibility = View.VISIBLE
        playGame()
        startTimer()
    }

    private fun playGame() {

        randomNumberOne = random.nextInt(20) + 1
        randomNumberTwo = random.nextInt(20) + 1

        question = "${randomNumberOne.toString()} + ${randomNumberTwo.toString()}"
        tv_question.text = question

        locationOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3){
            if (i == locationOfCorrectAnswer){
                answers.add(randomNumberOne!! + randomNumberTwo!!)
            } else {
                var wrongAnswer = random.nextInt(40) + 1
                while (wrongAnswer == randomNumberOne!! + randomNumberTwo!!){
                    wrongAnswer = random.nextInt(40) + 1
                }
                answers.add(wrongAnswer)
            }
        }

        button0.text = answers.get(0).toString()
        button1.text = answers.get(1).toString()
        button2.text = answers.get(2).toString()
        button3.text = answers.get(3).toString()

    }

    fun chooseAnswer(view: View) {
        val tag = view.tag.toString()
        if (locationOfCorrectAnswer!!.toString().equals(tag)){
            tv_result.visibility = View.VISIBLE
            tv_result.setTextColor(ContextCompat.getColor(this, R.color.correct))
            tv_result.text = getString(R.string.correct_text)
            correctAnswers++
        }else {
            tv_result.visibility = View.VISIBLE
            tv_result.setTextColor(ContextCompat.getColor(this, R.color.wrong))
            tv_result.text = getString(R.string.wrong_text)
        }
        numberOfQuestions++
        score = "${correctAnswers.toString()}/${numberOfQuestions.toString()}"
        tv_score.text = score
        playGame()
    }

    private fun startTimer(){
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toString() + "s"
                tv_timer.setText(seconds)
            }

            override fun onFinish() {
                done.start()
                timesUp()
            }
        }.start()

    }

    fun timesUp(){
        tv_timer.text = getString(R.string.zero_seconds_text)
        tv_result.setTextColor(ContextCompat.getColor(this, R.color.timesup))
        tv_result.text = getString(R.string.done_text)
        btn_play_again.visibility = View.VISIBLE
        button0.isClickable = false
        button1.isClickable = false
        button2.isClickable = false
        button3.isClickable = false
    }

    fun playAgain(view : View){
        btn_play_again.visibility = View.INVISIBLE
        tv_result.visibility = View.INVISIBLE
        button0.isClickable = true
        button1.isClickable = true
        button2.isClickable = true
        button3.isClickable = true
        correctAnswers = 0
        numberOfQuestions = 0
        score = "${correctAnswers.toString()}/${numberOfQuestions.toString()}"
        tv_score.text = score
        playGame()
        startTimer()
    }


}
