package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var TVSelectedDate : TextView? = null  //Declaring a variable for 31/09/2012 textview
    private var TimeInMinutes : TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up the button

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        TVSelectedDate = findViewById(R.id.SelectedDate)  //Getting 31/09/2012 id
        TimeInMinutes = findViewById(R.id.TimeInMinutes)

        btnDatePicker.setOnClickListener(){
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        val myCalander = Calendar.getInstance()
        val year = myCalander.get(Calendar.YEAR)
        val month = myCalander.get(Calendar.MONTH)
        val day = myCalander.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { _ , SelectedYear, SelectedMonth, SelectedDayOfMonth ->
                Toast.makeText(this, "Year was $SelectedYear, Month was ${SelectedMonth+1}, Day was $SelectedDayOfMonth", Toast.LENGTH_LONG).show()


                val selectedDate = "$SelectedDayOfMonth/${SelectedMonth+1}/$SelectedYear"
                TVSelectedDate?.text = selectedDate //Assigning user selected date (nullable value)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)   //basically converting the string of date into date format

                //.let is the way to make sure the {} code get executed iff theDate is not empty
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000  //time function gives time in milliseconds passed b/w selected date and 1 Jan 1970
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time / 60000  ////gives time in ms b/w currentDate and 1 Jan 1970
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        TimeInMinutes?.text = differenceInMinutes.toString()
                    }

                }
                val selectedDateInMinutes = theDate.time / 60000  //time function gives time in milliseconds passed b/w selected date and 1 Jan 1970

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateInMinutes = currentDate.time / 60000  ////gives time in ms b/w currentDate and 1 Jan 1970

                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                TimeInMinutes?.text = differenceInMinutes.toString()

            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000  //86.4M ms in 24 hours
        dpd.show()


    }
}