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
    LazyVerticalGrid(columns = GridCells.Fixed(7), content = {items(30) { CalendarItem() } })
}

@Composable
fun CalendarItem() {
    var date by remember { mutableStateOf(1) }
    Text(text = date.toString(), modifier = Modifier.padding(5.dp))
    date += 1
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
    val items = listOf("jan", "feb", "march")
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