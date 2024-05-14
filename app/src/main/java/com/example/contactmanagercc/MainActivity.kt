package com.example.contactmanagercc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.Layout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactManager()
        }
    }

    @Composable
    fun ContactList(
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        val test = false
        var buttonClicked by remember { mutableStateOf(test) }
        MySurface(
            modifier = Modifier
                .clickable {
                    buttonClicked = !buttonClicked
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {


            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start
            ) {
                val backgroundColor=if(buttonClicked) Color.Green else Color.Blue
                Surface(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = "Contact 1",
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .background(color = backgroundColor)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Surface(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = "Number 1",
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .background(color = backgroundColor)
                    )

                }
            }
        }
    }

   // @Composable
    //fun Contacts(contacts: List<String>){
      //  LazyColumn{
       //     items(contacts){ contact->
         //       ContactList()
           // }
        //}
    //}

    @Composable
    fun MySurface(
        modifier: Modifier= Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier=modifier,
            content = content,
            measurePolicy = { measurables, constraints ->

                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints)
                }


                val maxWidth = placeables.maxOfOrNull { it.width } ?: 0
                val maxHeight = placeables.sumOf { it.height }


                layout(maxWidth, maxHeight) {

                    var yPosition = 0
                    placeables.forEach { placeable ->
                        placeable.place(0, yPosition)
                        yPosition += placeable.height
                    }
                }
            }
        )
    }

    @Composable
    @Preview(showBackground = true)
    fun ContactManager() {
        ContactList()
    }
}
