package com.example.smoker_logger

import android.annotation.SuppressLint
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*

@Composable
fun Calendar() {
    val year = remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    val month = remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }

    Column {
        Text(text = "${year.value} ${month.value}")
        DateHeader()
        DatePicker(year, month)
        MonthsList(year, month)
    }
}

@Composable
fun DatePicker(year: MutableState<Int>, month: MutableState<Int>) {
    Row() {
        DatesDropdown(month)
        IconButton(onClick = {
            minusMonth(year, month)
        }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Previous")
        }
        IconButton(onClick = {
            plusMonth(year, month)
        }) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Next")
        }
    }
}

fun minusMonth(year: MutableState<Int>, month: MutableState<Int>) {
    if(month.value <= 1) {
        year.value--
        month.value = 12
    }
    else {
        month.value--
    }
}

fun plusMonth(year: MutableState<Int>, month: MutableState<Int>) {
    if(month.value >= 12) {
        year.value++
        month.value = 1
    }
    else {
        month.value++
    }
}

@SuppressLint("NewApi")
@Composable
fun MonthsList(year: MutableState<Int>, month: MutableState<Int>) {
    val monthLength = YearMonth.of(year.value, month.value).lengthOfMonth()
    val data = 1..monthLength
//    val data by remember { mutableStateOf(1..monthLength) }

    MonthDates(dates = data , columns = 7)
}

@Composable
fun MonthDates(dates: IntRange, columns: Int = 7) {
    Column() {
        for (row in 0 until dates.count() / columns + 1)
            Row() {
                for (index in row * columns until (row + 1) * columns)
                    if(index < dates.count())
                        CalendarItem(date = dates.elementAt(index))
            }
    }
}

@Composable
fun CalendarItem(date: Int) {
    Text(text = date.toString(), modifier = Modifier.padding(5.dp))
}

@Composable
fun DatesDropdown(month: MutableState<Int>) {
    var expanded by remember { mutableStateOf(false) }
    val items = DateFormatSymbols().months
//    var selectedIndex by remember { mutableStateOf(month.value - 1) }

    Text(text = items[month.value - 1], modifier = Modifier
        .fillMaxWidth(.5f)
        .clickable(onClick = { expanded = true }))
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(.5f)) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                expanded = false
                month.value = index + 1
            }) {
                Text(text = item)
            }
        }
    }
}

@Composable
fun DateHeader() {
    val date = Calendar.getInstance().time
    val formattedDate = SimpleDateFormat("EEE, MMM d").format(date)
    Text(text = formattedDate)
}

@Composable
fun CalendarAndroidView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

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