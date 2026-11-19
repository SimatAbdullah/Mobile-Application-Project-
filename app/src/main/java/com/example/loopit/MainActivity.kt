
package com.example.loopit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.loopit.navigation.AppNavGraph
import com.example.loopit.ui.theme.MyLoopitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyLoopitTheme {
                AppNavGraph()
            }
        }
    }
}
