package com.example.smoker_logger

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Calendar() {
    Column {
        DateHeader()
        DatePicker()
        MonthCalendar()
    }
}

@Composable
fun MonthCalendar() {
    TODO("Not yet implemented")
}

@Composable
fun DatePicker() {
    TODO("Not yet implemented")
}

@Composable
fun DateHeader() {
    Text(text = "Mon, Aug 17")
}

@Composable
fun CalendarAndroidView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val calendar = java.util.Calendar.getInstance()

//    var selectedDateText by remember { mutableStateOf("") }
//    val year = calendar[Calendar.YEAR]
//    val month = calendar[Calendar.MONTH]
//    val day = calendar[Calendar.DAY_OF_MONTH]
    AndroidView(
        { CalendarView(ContextThemeWrapper(context, R.style.Widget_CalendarView_Custom)) },
        modifier,
        update = { views ->
            views.date = calendar.timeInMillis
            views.setOnDateChangeListener { calendarView, year, month, day ->
                Log.d("onDateChangeListener", "changed date")
            }
        }
    )
}