package com.example.a7minuteworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleview: String = METRIC_UNITS_VIEW
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.calculate_bmi_actionBar_heading)
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnCalculateUnits.setOnClickListener {
            if (currentVisibleview.equals(METRIC_UNITS_VIEW)) {
                if (validateUnitsMetric()) {
                    val heightValue: Float =
                        binding.etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBmiResult(bmi)
                } else {
                    Toast.makeText(
                        this@BmiActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (validateUsUnits()) {
                    val usUnitHeightValueFeet: String = binding.etUsUnitHeightInFeet.text.toString()
                    val usUnitHeightValueInch: String = binding.etUsUnitHeightInInch.text.toString()
                    val usUnitWeightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()

                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))
                    displayBmiResult(bmi)

                } else {
                    Toast.makeText(
                        this@BmiActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        makeVisibleMetricUnitsView()
        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_metricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more! "
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more! "
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more! "
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in good shape! "
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout! "
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of yourself! Workout! "
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class | (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now! "
        } else {
            bmiLabel = "Obese Class | (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now! "
        }

        binding.llDisplayBmiResult.visibility = View.VISIBLE
//        binding.tvYourBmi.visibility = View.VISIBLE
//        binding.tvBmiValue.visibility = View.VISIBLE
//        binding.tvBmiType.visibility = View.VISIBLE
//        binding.tvBmiDescription.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBmiValue.text = bmiValue
        binding.tvBmiType.text = bmiLabel
        binding.tvBmiDescription.text = bmiDescription


    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleview = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.GONE
        binding.llUsUnitHeight.visibility = View.GONE

        binding.llDisplayBmiResult.visibility = View.GONE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleview = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUnitHeight.visibility = View.GONE

        binding.etUsUnitWeight.text!!.clear()
        binding.etUsUnitHeightInInch.text!!.clear()
        binding.etUsUnitHeightInFeet.text!!.clear()

        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llUsUnitHeight.visibility = View.VISIBLE

        binding.llDisplayBmiResult.visibility = View.GONE
    }

    private fun validateUnitsMetric(): Boolean {
        var isValid = true
        if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        when {
            binding.etUsUnitHeightInFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsUnitHeightInInch.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }
}