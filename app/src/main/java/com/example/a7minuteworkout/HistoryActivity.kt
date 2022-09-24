package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityHistroyBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistroyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistroyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.history)
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }
    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDatesList = dbHandler.getAllCompleteDatesList()

        if (allCompletedDatesList.size > 0 ){
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.GONE

            binding.rvHistory.layoutManager = LinearLayoutManager(this)
            val adapter  = HistoryAdapter(this,allCompletedDatesList)
            binding.rvHistory.adapter = adapter
        }else{
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
        }
    }
}