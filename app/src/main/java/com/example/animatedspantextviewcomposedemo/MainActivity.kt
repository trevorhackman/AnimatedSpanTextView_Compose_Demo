package com.example.animatedspantextviewcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animatedspantextviewcomposedemo.ui.theme.AnimatedSpanTextViewComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimatedSpanTextViewComposeDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimatedSpanText(
                        text = "Hello World!",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun FourExamples() {
    Column {
        Text(
            "RGB Normal Speed",
            Modifier.padding(top = 12.dp),
            fontSize = 12.sp
        )
        AnimatedSpanText(
            "Hello World!",
            fontSize = 48.sp
        )
        Text(
            "RGB Fast Speed",
            Modifier.padding(top = 12.dp),
            fontSize = 12.sp
        )
        AnimatedSpanText(
            "Hello World!",
            fontSize = 48.sp
        )
        Text(
            "Magenta Red Normal Speed",
            Modifier.padding(top = 12.dp),
            fontSize = 12.sp
        )
        AnimatedSpanText(
            "Hello World!",
            fontSize = 48.sp
        )
        Text(
            "ROYGBIV Shorter Width Fast Speed",
            Modifier.padding(top = 12.dp),
            fontSize = 12.sp
        )
        AnimatedSpanText(
            "Hello World!",
            fontSize = 48.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimatedSpanTextViewComposeDemoTheme {
        FourExamples()
    }
}
