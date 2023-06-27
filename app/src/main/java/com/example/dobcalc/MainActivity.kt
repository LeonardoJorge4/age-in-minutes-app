package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

const val TWENTY_FOUR_HOURS_IN_MILLISECONDS = 86400000

class MainActivity : AppCompatActivity() {
  private var tvSelectedDate : TextView? = null
  private var tvAgeInMinutes: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
    tvSelectedDate = findViewById(R.id.tvSelectedDate)
    tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

    btnDatePicker.setOnClickListener {
      clickDatePicker()
    }
  }

  private fun clickDatePicker() {
    val myCalendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val month = myCalendar.get(Calendar.MONTH)
    val day = myCalendar.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(this,
      { _, selectedYear, selectedMonth, selectedDayOfMonth ->
        Toast.makeText(this,
          "Year was $year, Month was ${selectedMonth + 1}, the day is $selectedDayOfMonth", Toast.LENGTH_LONG
        ).show()

        val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

        tvSelectedDate?.text = selectedDate

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        val convertedDate = sdf.parse(selectedDate)

        convertedDate?.let {
          val selectedDateInMinutes = convertedDate.time / 60000

          val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
          currentDate?.let {
            val currentDateInMinutes = currentDate.time / 60000

            val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

            tvAgeInMinutes?.text = differenceInMinutes.toString()
          }
        }
      },
      year,
      month,
      day
    )

    dpd.datePicker.maxDate = System.currentTimeMillis() - TWENTY_FOUR_HOURS_IN_MILLISECONDS //milliseconds
    dpd.show()
  }
}