package com.example.smoker_logger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smoker_logger.ui.theme.SmokerloggerTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmokerloggerTheme {
                Log.d(MainActivity::class.toString(), "onCreate")
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ScaffoldDemo()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(MainActivity::class.toString(), "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(MainActivity::class.toString(), "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(MainActivity::class.toString(), "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(MainActivity::class.toString(), "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(MainActivity::class.toString(), "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MainActivity::class.toString(), "onDestroy")
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldDemo() {
    Scaffold(
        topBar = { TopAppBar(title = {Text("Smoker Logger")}, backgroundColor = MaterialTheme.colors.primarySurface)  },
        content = { Calendar(Modifier.fillMaxSize()) },
        bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primarySurface) {} }
    )
}


@Composable
fun Calendar(modifier: Modifier = Modifier) {
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
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    SmokerloggerTheme {
        ScaffoldDemo()
    }
}