package com.example.smoker_logger

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
    Column {
        DateHeader()
        DatePicker()
        MonthCalendar()
    }
}

@Composable
fun MonthCalendar() {
    val listState = rememberLazyListState()

    val months = 1..12

    LazyColumn(state = listState) {
        items(months.count()) { index ->
            val monthLength = getMonthLength(Calendar.YEAR, months.elementAt(index))
            val dates = 1..monthLength

            Text(text = DateFormatSymbols().months.get(index))
            MonthDates(dates = dates, columns = 7)
        }
    }
}

fun getMonthLength(year: Int, month: Int): Int {
    return YearMonth.of(year, month).lengthOfMonth()
}

@Composable
fun MonthDates(dates: IntRange, columns: Int) {
    Column() {
        for (row in 0 until dates.count() / columns + 1)
            Row() {
                for (index in row * columns until (row + 1) * columns)
                    if(index < dates.count())
                        CalendarItem(date = dates.elementAt(index))
            }
    }
}

fun loadMoreMonths() {
    TODO("Not yet implemented")
}

@Composable
fun CalendarItem(date: Int) {
    Text(text = date.toString(), modifier = Modifier.padding(5.dp))
}

@Composable
fun DatePicker() {
    Row() {
        DatesDropdown()
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Previous")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Next")
        }
    }
}

@Composable
fun DatesDropdown() {
    var expanded by remember { mutableStateOf(false) }
    val items = DateFormatSymbols().months
    var selectedIndex by remember { mutableStateOf(0) }

    Text(text = items[selectedIndex], modifier = Modifier
        .fillMaxWidth(.5f)
        .clickable(onClick = { expanded = true }))
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(.5f)) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                selectedIndex = index
                expanded = false
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