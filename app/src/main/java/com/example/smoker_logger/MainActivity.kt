package com.example.smoker_logger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smoker_logger.ui.theme.SmokerloggerTheme
import java.io.File
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        set dex files to read only to avoid android 14 bug
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

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
        content = { Calendar() },
        bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primarySurface) {} }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    SmokerloggerTheme {
        ScaffoldDemo()
    }
}