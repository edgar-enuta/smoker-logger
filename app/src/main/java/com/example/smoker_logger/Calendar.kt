package com.example.smoker_logger

import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
    val calendar = Calendar.getInstance()
    val monthLength = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)).lengthOfMonth()
    val dates = 1..monthLength

    LazyVerticalGrid(columns = GridCells.Fixed(7), content = {items(dates.count()) { index ->
        CalendarItem(dates.elementAt(index))
        }
    })
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