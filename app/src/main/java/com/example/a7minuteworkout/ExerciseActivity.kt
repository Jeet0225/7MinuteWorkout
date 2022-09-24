package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.graphics.Color.green
import android.graphics.drawable.Drawable
import android.media.MediaParser
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import org.intellij.lang.annotations.JdkConstants
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding
    private var exerciseAdapter : ExerciseAdapter? = null
    private var tts : TextToSpeech? = null
    private var mediaPlayer : MediaPlayer? = null
    private var restTimer : CountDownTimer? = null
    private var exerciseTimer : CountDownTimer? = null
    private var restProgress = 0
    private var exerciseProgress = 0
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            createCustomDialog()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()

        setUpExerciseRecyclerView()
    }

    private fun setUpExerciseRecyclerView() {

        binding.rvExerciseRecyclerView.layoutManager = LinearLayoutManager(
                this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter =  ExerciseAdapter(this, exerciseList!!)
        binding.rvExerciseRecyclerView.adapter =exerciseAdapter
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()

            }
        }.start()
    }

    private fun setUpRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        try {
        mediaPlayer = MediaPlayer.create(this,R.raw.press_start)
        mediaPlayer!!.isLooping = false
        mediaPlayer!!.start()
            
        }catch (e : Exception){
            e.printStackTrace()
        }
        binding.llExerciseView.visibility = View.GONE
        binding.llRestView.visibility = View.VISIBLE
        if (currentExercisePosition < exerciseList!!.size) {
            binding.tvUpcomingExerciseName.text =
                exerciseList!![currentExercisePosition + 1].getName()
        }
        setRestProgressBar()
    }
    private fun setUpExerciseView(){
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE
        speak(exerciseList!![currentExercisePosition].getName())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        binding.ivExerciseImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        setExerciseProgressBar()

    }
    private fun setExerciseProgressBar(){
        binding.progressBarForExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarForExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30-exerciseProgress).toString()

            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList!!.size-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }else{
                    finish()
                    val finishIntent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(finishIntent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (mediaPlayer != null){
            mediaPlayer!!.stop()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
          val result = tts!!.setLanguage( Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result ==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The Language specified is not supported")
            }
        }else{
            Log.e("TTS","Error in initialization")
        }
    }
    private fun speak(textToSpeech: String){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(textToSpeech,TextToSpeech.QUEUE_FLUSH,null,"")
        }
    }
    private fun createCustomDialog(){
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.custom_dialog_on_back_pressed)
        customDialog.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}


